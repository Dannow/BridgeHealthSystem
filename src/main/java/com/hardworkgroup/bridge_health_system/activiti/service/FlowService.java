package com.hardworkgroup.bridge_health_system.activiti.service;

import org.activiti.engine.delegate.DelegateTask;

public interface FlowService {
    void createTaskEvent(DelegateTask delegateTask);
}
