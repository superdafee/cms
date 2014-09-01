package com.zjs.cms.weixin.service;


import com.zjs.cms.weixin.entity.*;
import org.springframework.stereotype.Service;

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
    /**
     * 处理文本消息发送文本消息
     * @param textMessage
     * @return
     * @throws Exception
     */
    public  String sendTextMessage(TextMessage textMessage) throws Exception {
        String xml="<xml>" +
                "<ToUserName><![CDATA["+textMessage.getFromUserName()+"]]></ToUserName>" +
                "<FromUserName><![CDATA["+textMessage.getToUserName()+"]]></FromUserName>" +
                "<CreateTime>"+textMessage.getCreateTime()+"</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA["+textMessage.getContent()+"]]></Content>" +
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
        String content="欢迎关注作业辅导平台！";
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
                    xml.append("<xml>");
                    xml.append("<ToUserName><![CDATA["+msg.getFromUserName()+"]]></ToUserName>");
                    xml.append("<FromUserName><![CDATA["+msg.getToUserName()+"]]></FromUserName>");
                    xml.append("<CreateTime>"+msg.getCreateTime()+"</CreateTime>");
                    xml.append("<MsgType><![CDATA[news]]></MsgType>");
                    xml.append("<ArticleCount>"+3+"</ArticleCount>");
                    xml.append("<Articles>");

                    xml.append(appendResponseNewsXml(newsList));

                    xml.append("</Articles>");
                    xml.append("</xml>");
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
