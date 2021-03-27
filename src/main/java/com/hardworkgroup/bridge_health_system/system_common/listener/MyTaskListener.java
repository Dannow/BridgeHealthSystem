package com.hardworkgroup.bridge_health_system.system_common.listener;

import com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl.FlowServiceImpl;
import com.hardworkgroup.bridge_health_system.system_common.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("eventName=={}",delegateTask.getEventName());
        log.info("Name=={}",delegateTask.getName());
        if("assignment".equals(delegateTask.getEventName())){
            FlowServiceImpl flowService = (FlowServiceImpl) SpringContextUtil.getBean("flowServiceImpl");
            log.info("flowService={}",flowService);
            flowService.createTaskEvent(delegateTask);
        }
    }
}
