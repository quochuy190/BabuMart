package com.vn.babumart.models;

public class MessageEvent {
    public final String message;
    public final float point;
    public final long time;


    public MessageEvent(String message, float point, long time) {
        this.message = message;
        this.point = point;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public float getPoint() {
        return point;
    }

    public long getTime() {
        return time;
    }
}
