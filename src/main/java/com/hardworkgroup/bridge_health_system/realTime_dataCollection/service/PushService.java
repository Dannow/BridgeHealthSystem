package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service;

public interface PushService {
    /**
     * 推送给指定用户
     */
    void pushMsgToOne(String userId,Object msg);

    /**
     * 推送给所有用户
     */
    void pushMsgToAll(String msg);
}
