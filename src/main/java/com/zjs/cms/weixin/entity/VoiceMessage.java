package com.zjs.cms.weixin.entity;

/**
 * Created with IntelliJ IDEA.
 * User: mayufeng
 * Date: 14-2-13
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
public class VoiceMessage extends TextMessage {

    private String fomat;

    private String mediaId;

    public String getFomat() {
        return fomat;
    }

    public void setFomat(String fomat) {
        this.fomat = fomat;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
