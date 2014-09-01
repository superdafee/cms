package com.zjs.cms.weixin.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: mayufeng
 * Date: 14-2-11
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
public class AccessToken {
    public  String accessToken;
    public  int expiresIn;
    public  String startTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
