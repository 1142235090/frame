package com.hz.task.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @explain
 * @Classname EventListener
 * @Date 2022/9/20 19:41
 * @Created by chrise warnner
 */
@Component
@Slf4j
@EnableAsync
public class EventDtoListener {

    @Async("asyncServiceExecutor")
    @EventListener
    public void listener(EventDto eventDto) {
        System.out.println(eventDto.getMessage());
    }
}
