package com.hz.task.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @explain
 * @Classname RadarGetPushEvent
 * @Date 2022/7/20 10:28
 * @Created by chrise warnner
 */
@Service
public class EventDtoPush {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void push(String message) {
        applicationEventPublisher.publishEvent(new EventDto(message));
    }
}
