package com.zjs.cms.weixin.pojo;

import java.io.Serializable;


public class WeixinInfo implements Serializable {
	
	private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
