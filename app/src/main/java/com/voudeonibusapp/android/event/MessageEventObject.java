package com.voudeonibusapp.android.event;

public class MessageEventObject {

    private MessageEvent messageEvent;
    private Object object;

    public MessageEventObject(MessageEvent messageEvent, Object object) {
        this.messageEvent = messageEvent;
        this.object = object;
    }

    public MessageEvent getMessageEvent() {
        return messageEvent;
    }

    public void setMessageEvent(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
