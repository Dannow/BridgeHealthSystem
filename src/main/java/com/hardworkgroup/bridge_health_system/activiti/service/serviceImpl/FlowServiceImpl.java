package com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl;

import com.hardworkgroup.bridge_health_system.activiti.dao.FlowDao;
import com.hardworkgroup.bridge_health_system.activiti.service.FlowService;
import com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity.FlowInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.response.ProfileResult;
import com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FlowServiceImpl implements FlowService {
    @Autowired
    private ActFlowCommServiceImpl actFlowCommService;

    @Autowired
    private FlowDao flowDao;
    /**
     * 查询用户任务
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findUserTask(Integer userId) {
        List<Map<String, Object>> list = actFlowCommService.myTaskList(userId);
        return list;
    }


    /**
     * 完成任务
     * @param userId
     */
    public void completeTask(String taskId,Integer userId) {
        actFlowCommService.completeProcess("确认",taskId,userId);
    }

    /**
     * 查询所有流程
     * @return
     */
    public List<FlowInfo> findAllFlow() {
        return flowDao.selectFlowList();
    }

    /**
     * 查询单个流程
     * @param id
     * @return
     */
    public FlowInfo findOneFlow(String id) {
        return flowDao.selectOneFlow(id);
    }

    /**
     * 更新部署状态
     * @param id
     * @return
     */
    public int updateDeployState(String id){
        return flowDao.updateFlowDeployState(id);
    }

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SiteMessageServiceImpl siteMessageService;
    /**
     * 任务创建事件
     * @param delegateTask
     */
    @Override
    public void createTaskEvent(DelegateTask delegateTask) {
        log.info("delegateTask=={}",delegateTask);
//        负责人
        String assignee = delegateTask.getAssignee();
        log.info("assignee=={}",assignee);
//        获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();
        Integer userId = result.getUserId();
//        任务id
        String taskId = delegateTask.getId();
        if(!assignee.equals(userId.toString())){
            int type =1;
            siteMessageService.sendMsg(Integer.parseInt(assignee),taskId,type);
        }

    }

    /**
     * 查询任务详细信息
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findTaskInfo(Integer userId) {
        List<Map<String, Object>> list = actFlowCommService.myTaskInfoList(userId);
        return list;
    }
}
