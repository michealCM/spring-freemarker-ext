package org.spring.freemarker.web.event.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 *
 * @date 2018-11-29 17:03:21
 */
public class ApplicationContextRefreshedListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ContextRefreshedEvent){
            ContextRefreshedEvent contextRefreshedEvent = (ContextRefreshedEvent)applicationEvent;
            ApplicationContextHelper.initApplicationContext(contextRefreshedEvent.getApplicationContext());
        }
    }
}
