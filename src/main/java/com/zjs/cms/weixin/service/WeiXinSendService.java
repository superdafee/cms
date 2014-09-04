package com.zjs.cms.weixin.service;


import com.zjs.cms.entity.Parent;
import com.zjs.cms.entity.ParentStudentCon;
import com.zjs.cms.repository.ParentDao;
import com.zjs.cms.repository.ParentStudentDao;
import com.zjs.cms.repository.StudentDao;
import com.zjs.cms.utils.PropertiesUtil;
import com.zjs.cms.weixin.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mayufeng
 * Date: 14-2-11
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
@Service
public class WeiXinSendService {
    public static  int NUMBER=3;
    private static Logger logger = LoggerFactory.getLogger(WeiXinSendService.class);

    @Resource
    private StudentDao studentDao;
    @Resource
    private ParentDao parentDao;
    @Resource
    private ParentStudentDao parentStudentDao;

    /**
     * 处理文本消息发送文本消息
     * @param textMessage
     * @return
     * @throws Exception
     */
    public  String sendTextMessage(TextMessage textMessage, String content) throws Exception {
        String xml="<xml>" +
                "<ToUserName><![CDATA["+textMessage.getFromUserName()+"]]></ToUserName>" +
                "<FromUserName><![CDATA["+textMessage.getToUserName()+"]]></FromUserName>" +
                "<CreateTime>"+textMessage.getCreateTime()+"</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
//                "<Content><![CDATA["+textMessage.getContent()+"]]></Content>" +
                "<Content><![CDATA["+content+"]]></Content>" +
                "</xml>" ;
        return  xml;
    }
    /**
     * 处理图片消息
     * @param picMessage
     * @return
     * @throws Exception
     */
    public  String sendPicMessage(PicMessage picMessage) throws Exception {
        String xml="<xml>" +
                "<ToUserName><![CDATA["+picMessage.getFromUserName()+"]]></ToUserName>" +
                "<FromUserName><![CDATA["+picMessage.getToUserName()+"]]></FromUserName>" +
                "<CreateTime>"+picMessage.getCreateTime()+"</CreateTime>" +
                "<MsgType><![CDATA[image]]></MsgType>" +
                "<Image><MediaId><![CDATA["+picMessage.getMediaId()+"]]></MediaId></Image>" +
                "</xml>" ;
        return  xml;
    }

    /**
     * 发送音频文件
     * @param message
     * @return
     * @throws Exception
     */
    public  String sendVoiceMessage(VoiceMessage message) throws Exception {
        String xml="<xml>" +
                "<ToUserName><![CDATA["+message.getFromUserName()+"]]></ToUserName>" +
                "<FromUserName><![CDATA["+message.getToUserName()+"]]></FromUserName>" +
                "<CreateTime>"+message.getCreateTime()+"</CreateTime>" +
                "<MsgType><![CDATA[voice]]></MsgType>" +
                "<Voice><MediaId><![CDATA["+message.getMediaId()+"]]></MediaId></Voice>" +
                "</xml>" ;
        return  xml;
    }
    /**
     * 反馈事件消息
     * @param message
     * @return
     * @throws Exception
     */
    public  String sendEventMessage(EventMessage message) throws Exception {
        String content="欢迎关注中小学教育支撑平台！";
        System.out.println("#######:"+message.getEvent());
        if(message.getEvent().equals("unsubscribe")){
            content="谢谢退出";
        }else if(message.getEvent().equalsIgnoreCase("click")){
            return replyEventAction(message);
        }else{
            content="功能正在开发中，请持续关注...";
        }
        String xml="<xml>" +
                "<ToUserName><![CDATA["+message.getFromUserName()+"]]></ToUserName>" +
                "<FromUserName><![CDATA["+message.getToUserName()+"]]></FromUserName>" +
                "<CreateTime>"+message.getCreateTime()+"</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA["+content+"]]></Content>" +
                "</xml>" ;
        return  xml;
    }

    /**
     * 反馈事件消息
     * @param message
     * @return
     * @throws Exception
     */
    public  String zSendEventMessage(EventMessage message) throws Exception {
        String content;
        System.out.println("#######:"+message.getEvent());
        if(message.getEvent().equals("unsubscribe")){
            content="感谢您对本平台的支持";
        }else if(message.getEvent().equals("subscribe")){

            content="感谢您关注作业辅导平台，获取最及时、高质量的同步作业辅导微课程，参与精彩的线下活动，绑定您的信息后可以获得丰富的定制化功能。\n" +
                    "<a href='"+ PropertiesUtil.getWeixinAppValue("weixin.host")+"/weixin/user/bindAccount/"+message.getFromUserName()+"'>绑定账号</a>";
        }else if(message.getEvent().equalsIgnoreCase("click")){
            return replyEventAction(message);
        }else{
            content="功能正在开发中，请持续关注...";
        }
        String xml="<xml>" +
                "<ToUserName><![CDATA["+message.getFromUserName()+"]]></ToUserName>" +
                "<FromUserName><![CDATA["+message.getToUserName()+"]]></FromUserName>" +
                "<CreateTime>"+message.getCreateTime()+"</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA["+content+"]]></Content>" +
                "</xml>" ;
        return  xml;
    }

    /**
     * 反馈事件消息
     * @param message
     * @return
     * @throws Exception
     */
    public  String sendEventMessage2(EventMessage message) throws Exception {
        String content="欢迎关注河北ISChool教育平台--我爱学习";
        if(!message.getEvent().equals("subscribe")){
            content="谢谢退出";
        }
        StringBuilder xml=new StringBuilder();
        xml.append("[{").append("\"touser\":\""+message.getFromUserName()+"\",");
        xml.append("\"msgtype\":\"text\",");
        xml.append("\"text\":");
        xml.append("{").append(" \"content\":\""+content+"\"").append(" }");
        xml.append("}]");
        return  xml.toString();
    }

    /**
     * 针对业务事件返回消息
     * @param msg
     * @return
     * @throws Exception
     */
    public String replyEventAction(EventMessage msg) throws Exception {

        List<NewsMessage> newsList = new ArrayList<NewsMessage>();

        newsList.add(new NewsMessage("数学", "今日数学作业",
                "http://www.izhufu.com/pics/allimg/101130/1730010.jpg",
                "http://dafee.ngrok.com/testme/list2"));
        newsList.add(new NewsMessage("语文", "今日语文作业",
                "http://www.izhufu.com/pics/allimg/101130/1730010.jpg",
                "http://dafee.ngrok.com/testme/list2"));
        newsList.add(new NewsMessage("英语", "今日英语作业",
                "http://www.izhufu.com/pics/allimg/101130/1730010.jpg",
                "http://dafee.ngrok.com/testme/list2"));
        StringBuilder xml = new StringBuilder();
        if(msg!=null){
            String msgType ;
            if(msg.getEvent().equalsIgnoreCase("click")){
                if(msg.getEventKey().equals("C1001_MY_HOMEWORK")){
//                    xml.append("<xml>");
//                    xml.append("<ToUserName><![CDATA["+msg.getFromUserName()+"]]></ToUserName>");
//                    xml.append("<FromUserName><![CDATA["+msg.getToUserName()+"]]></FromUserName>");
//                    xml.append("<CreateTime>"+msg.getCreateTime()+"</CreateTime>");
//                    xml.append("<MsgType><![CDATA[news]]></MsgType>");
//                    xml.append("<ArticleCount>"+3+"</ArticleCount>");
//                    xml.append("<Articles>");
//
//                    xml.append(appendResponseNewsXml(newsList));
//
//                    xml.append("</Articles>");
//                    xml.append("</xml>");

                    //演示用

                    xml.append("<xml>");
                    xml.append("<ToUserName><![CDATA["+msg.getFromUserName()+"]]></ToUserName>");
                    xml.append("<FromUserName><![CDATA["+msg.getToUserName()+"]]></FromUserName>");
                    xml.append("<CreateTime>"+msg.getCreateTime()+"</CreateTime>");
                    xml.append("<MsgType><![CDATA[text]]></MsgType>");
                    xml.append("<Content><![CDATA[");

                    xml.append("选择学科查看作业辅导\n\n");
                    xml.append("<a href='"+ PropertiesUtil.getWeixinAppValue("weixin.host")+"/weixin/hw/list/1'>数学</a>\n");
                    xml.append("<a href='"+ PropertiesUtil.getWeixinAppValue("weixin.host")+"/weixin/hw/list/2'>语文</a>\n");
                    xml.append("<a href='"+ PropertiesUtil.getWeixinAppValue("weixin.host")+"/weixin/hw/list/3'>英语</a>");

                    xml.append("]]></Content>" );

                    xml.append("</xml>") ;


                }else if(msg.getEventKey().equals("A1001_MY_ACCOUNT")){
                    ParentStudentCon pscon = parentStudentDao.queryByOpenid(msg.getFromUserName());
                    if(pscon!=null&&pscon.getParent()!=null){
                        xml.append("<xml>");
                        xml.append("<ToUserName><![CDATA["+msg.getFromUserName()+"]]></ToUserName>");
                        xml.append("<FromUserName><![CDATA["+msg.getToUserName()+"]]></FromUserName>");
                        xml.append("<CreateTime>"+msg.getCreateTime()+"</CreateTime>");
                        xml.append("<MsgType><![CDATA[text]]></MsgType>");
                        xml.append("<Content><![CDATA[");


                        xml.append("您的账号信息如下：\n");
                        xml.append("学生姓名：\n" );
                        xml.append(pscon.getStudent().getRealname());
                        xml.append("\n学校：\n");
                        xml.append(pscon.getStudent().getSchool().getName());
                        xml.append("\n年级：\n");
                        xml.append(pscon.getStudent().getGrade()+"年级");
                        xml.append("\n班级：\n");
                        xml.append(pscon.getStudent().getClassname());
                        xml.append("\n家长姓名：\n");
                        xml.append(pscon.getParent().getRealname());
                        xml.append("\n联系方式：\n");
                        xml.append(pscon.getParent().getMobile());


                        xml.append("]]></Content>" );

                        xml.append("</xml>") ;
                    }else{
                        xml.append("<xml>");
                        xml.append("<ToUserName><![CDATA["+msg.getFromUserName()+"]]></ToUserName>" );
                        xml.append("<FromUserName><![CDATA["+msg.getToUserName()+"]]></FromUserName>" );
                        xml.append("<CreateTime>"+msg.getCreateTime()+"</CreateTime>" );
                        xml.append("<MsgType><![CDATA[text]]></MsgType>" );
                        xml.append("<Content><![CDATA[" );
                        xml.append("您尚未绑定您的账号信息，绑定您的信息后可以获得丰富的定制化功能，请点击以下链接进行账号绑定\n");
                        xml.append("<a href='"+ PropertiesUtil.getWeixinAppValue("weixin.host")+"/weixin/user/bindAccount/"+msg.getFromUserName()+"'>绑定账号</a>");
                        xml.append("]]></Content>");
                        xml.append("</xml>") ;
                    }

                }else{
                    xml.append("<xml>" +
                            "<ToUserName><![CDATA["+msg.getFromUserName()+"]]></ToUserName>" +
                            "<FromUserName><![CDATA["+msg.getToUserName()+"]]></FromUserName>" +
                            "<CreateTime>"+msg.getCreateTime()+"</CreateTime>" +
                            "<MsgType><![CDATA[text]]></MsgType>" +
                            "<Content><![CDATA[功能正在开发中，请持续关注...]]></Content>" +
                            "</xml>") ;
                }
            }
        }

        System.out.println(xml.toString());
        return xml.toString();
    }

    /**
     * 抽奖
     * @return
     */
    public boolean getAward(){
        int n=(int)(Math.random()*99);
        if(n>0 && n<=NUMBER){
            NUMBER--;
            return true;
        }
        System.out.println(n);
        return  false;
    }

    /**
     * 拼接被动响应图文消息内容
     * @param newsList
     * @return
     * @throws Exception
     */
    private String appendResponseNewsXml(List<NewsMessage> newsList) throws Exception{
        StringBuilder xml = null;
        if(newsList!=null && newsList.size()>0){
            xml = new StringBuilder();
            for(NewsMessage msg : newsList){
                xml.append("<item>");
                xml.append("<Title><![CDATA["+msg.getTitle()+"]]></Title>");
                xml.append("<Description><![CDATA["+msg.getDescription()+"]]></Description>");
                xml.append("<PicUrl><![CDATA["+msg.getPicurl()+"]]></PicUrl>");
                xml.append("<Url><![CDATA["+msg.getUrl()+"]]></Url>");
                xml.append("</item>");
            }
        }
        return xml!=null?xml.toString():null;
    }
}
