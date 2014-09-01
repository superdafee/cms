package com.zjs.cms.weixin.service;

import com.zjs.cms.weixin.entity.EventMessage;
import com.zjs.cms.weixin.entity.PicMessage;
import com.zjs.cms.weixin.entity.TextMessage;
import com.zjs.cms.weixin.entity.VoiceMessage;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mayufeng
 * Date: 14-1-2
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class WeiXinReceiveService {

    /**
     * 接受text文本
     * @param map
     * @return
     */
    public TextMessage receiveTextMessage( Map<String, String> map){
        TextMessage textMessage=new TextMessage();
        for(Map.Entry<String,String> m:map.entrySet()){
            System.out.println(m.getKey()+"------"+m.getValue());
            if(m.getKey().equals(WeixinConstant.ToUserName)){  //接受人现在变发送人
                textMessage.setToUserName(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.FromUserName)){  //接受人现在变发送人
                textMessage.setFromUserName(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.CreateTime)){
                textMessage.setCreateTime(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.MsgType)){
                textMessage.setMsgType(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.MsgId)){
                textMessage.setMsgId(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.Content)){
                if(m.getValue().equals("1")){
                   textMessage.setContent("<a href=\"http://wenwen.soso.com/p/20111103/20111103174125-425187779.jpg\">请关注我们的教育!</a>");
                }else if(m.getValue().equals("2")){
                    textMessage.setContent("请关注我们的教育！");
                }else{
                    textMessage.setContent("请输入1或2");
                }
            }else{

            }
        }
        return  textMessage;
    }

    /**
     * 接受图片消息
     * @param map
     * @return
     */
    public PicMessage receivePicMessage( Map<String, String> map){
        PicMessage picMessage=new PicMessage();
        for(Map.Entry<String,String> m:map.entrySet()){
            System.out.println(m.getKey()+"------"+m.getValue());
            if(m.getKey().equals(WeixinConstant.ToUserName)){  //发送时接受人现在变发送人
                picMessage.setToUserName(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.FromUserName)){  //发送时接受人现在变发送人
                picMessage.setFromUserName(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.CreateTime)){
                picMessage.setCreateTime(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.MsgType)){
                picMessage.setMsgType(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.PicUrl)){
                picMessage.setPicUrl(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.MediaId)){
                picMessage.setMediaId(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.MsgId)){
                picMessage.setMsgId(m.getValue());
            }
        }
        return  picMessage;
    }
    /**
     * 接受声音消息
     * @param map
     * @return
     */
    public VoiceMessage receiveVoiceMessage( Map<String, String> map){
        VoiceMessage message=new VoiceMessage();
        for(Map.Entry<String,String> m:map.entrySet()){
            System.out.println(m.getKey()+"------"+m.getValue());
            if(m.getKey().equals(WeixinConstant.ToUserName)){  //发送时接受人现在变发送人
                message.setToUserName(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.FromUserName)){  //发送时接受人现在变发送人
                message.setFromUserName(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.CreateTime)){
                message.setCreateTime(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.MsgType)){
                message.setMsgType(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.MediaId)){
                message.setMediaId(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.MsgId)){
                message.setMsgId(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.Format)){
                message.setFomat(m.getValue());
            }
        }
        return  message;
    }

    /**
     * 接受事件消息
     * @param map
     * @return
     */
    public EventMessage receiveEventMessage( Map<String, String> map){
        EventMessage message=new EventMessage();
        for(Map.Entry<String,String> m:map.entrySet()){
            System.out.println(m.getKey()+"------"+m.getValue());
            if(m.getKey().equals(WeixinConstant.ToUserName)){  //接受人现在变发送人
                message.setToUserName(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.FromUserName)){  //接受人现在变发送人
                message.setFromUserName(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.CreateTime)){
                message.setCreateTime(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.MsgType)){
                message.setMsgType(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.Event)){
                message.setEvent(m.getValue());
            }else if(m.getKey().equals(WeixinConstant.EventKey)){
                message.setEventKey(m.getValue());
            }
        }
        return  message;
    }

}
