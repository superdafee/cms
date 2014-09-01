package com.zjs.cms.weixin.entity;

/**
 * Created with IntelliJ IDEA.
 * User: mayufeng
 * Date: 14-2-11
 * Time: 上午9:32
 * To change this template use File | Settings | File Templates.
 ToUserName	开发者微信号
 FromUserName	 发送方帐号（一个OpenID）
 CreateTime	 消息创建时间 （整型）
 MsgType	 image
 PicUrl	 图片链接
 MediaId	 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
 MsgId	 消息id，64位整型
 */
public class PicMessage extends TextMessage {
    private String picUrl;
    private String mediaId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }



    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
