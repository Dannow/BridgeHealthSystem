package com.hardworkgroup.bridge_health_system.activiti.controller;


import com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl.SiteMessageServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity.SiteMessage;
import com.hardworkgroup.bridge_health_system.system_common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
public class SiteMessageController extends BaseController {

    @Autowired
    private SiteMessageServiceImpl siteMessageService;

    /**
     * 查询所有站内消息
     * @return
     */
    @GetMapping("/msg/findAll")
    public List<SiteMessage> findAll(HttpServletRequest request){
        return siteMessageService.findAll(this.userId);
    }

    @GetMapping("/msg/findOne/{id}")
    public SiteMessage findOne(@PathVariable(name = "id")Integer id, HttpServletRequest request){
        return siteMessageService.findOne(id,this.userId);
    }

    /**
     * 修改消息
     * @param id
     */
    @PutMapping("/msg/{id}")
    public void readMsg(@PathVariable(name = "id")Integer id, HttpServletRequest request){
        siteMessageService.readMsg(id,this.userId);
    }
}
