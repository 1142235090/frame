package com.hz.task.event;

import org.springframework.context.ApplicationEvent;


/**
 * @explain
 * @Classname RadarGetListener
 * @Date 2022/7/20 10:27
 * @Created by chrise warnner
 */
public class EventDto extends ApplicationEvent {
    private String message;

    public EventDto( String message) {
        // ApplicationEvent有一个spurce变量用于存储数据
        super("");
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
