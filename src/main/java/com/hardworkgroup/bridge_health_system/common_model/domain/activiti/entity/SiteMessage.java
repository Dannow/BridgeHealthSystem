package com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 站内消息
 */
@Data
@Entity
@Table(name = "siteMessage")
@NoArgsConstructor
public class SiteMessage {
    private Integer id;
    private Integer userID;
    private String taskID;
    /**
     * 消息类型  1-代办任务
     */
    private Integer type;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否已读  0- 已读  1-未读
     */
    private Integer isRead;
    private Date createTime;
    private Date updateTime;
}
