package com.zjs.cms.weixin.entity;

/**
 * 图文消息类
 * Created by dafee on 2014/8/22.
 */
public class NewsMessage {
    //图文消息标题
    private String title;
    //图文消息简介
    private String description;
    //图文消息图片地址
    private String picurl;
    //图文消息页url
    private String url;

    public NewsMessage(String title, String description, String picurl, String url) {
        this.title = title;
        this.description = description;
        this.picurl = picurl;
        this.url = url;
    }

    public NewsMessage(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
