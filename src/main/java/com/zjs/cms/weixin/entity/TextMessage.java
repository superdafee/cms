package com.zjs.cms.weixin.entity;

/**
 * Created with IntelliJ IDEA.
 * User: mayufeng
 * Date: 13-12-31
 * Time: 上午10:41
 * To change this template use File | Settings | File Templates.
 ToUserName	开发者微信号
 FromUserName	 发送方帐号（一个OpenID）
 CreateTime	 消息创建时间 （整型）
 MsgType	 text
 Content	 文本消息内容
 MsgId	 消息id，64位整型
 */
public class TextMessage {
    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String msgType;
    private String content;
    private String msgId;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
