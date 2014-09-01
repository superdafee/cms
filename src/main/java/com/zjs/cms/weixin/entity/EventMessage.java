package com.zjs.cms.weixin.entity;

/**
 * Created with IntelliJ IDEA.
 * User: mayufeng
 * Date: 14-1-2
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public class EventMessage extends TextMessage {

    private String event;
    private String eventKey;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
