package com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl;

import com.hardworkgroup.bridge_health_system.activiti.service.ActFlowCommService;
import com.hardworkgroup.bridge_health_system.activiti.service.IActFlowCustomService;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionPlanServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity.FlowInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.PlanFlowInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl.UserServiceImpl;
import com.hardworkgroup.bridge_health_system.system_common.utils.SpringContextUtil;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ActFlowCommServiceImpl implements ActFlowCommService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserServiceImpl userService;

    @Resource
    private InspectionPlanServiceImpl inspectionPlanService;

    /**
     * 部署流程定义
     */
    public void saveNewDeploy(FlowInfo flowInfo) {
        Deployment deployment = repositoryService.createDeployment()
                .disableSchemaValidation()
                .addClasspathResource(flowInfo.getFilePath()) // 添加bpmn资源
                .name(flowInfo.getFlowKey())
                .deploy();
//        4、输出部署信息
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    /**
     * 启动流程实例
     */
    public ProcessInstance startProcess(String formKey, String beanName, String bussinessKey, Integer id,Integer userID) {
        IActFlowCustomService customService = (IActFlowCustomService) SpringContextUtil.getBean(beanName);
//		修改业务的状态
        customService.startRunTask(id);
        Map<String, Object> variables = customService.setvariables(id,userID);
        variables.put("bussinessKey", bussinessKey);
//		启动流程
        log.info("【启动流程】，formKey ：{},bussinessKey:{}", formKey, bussinessKey);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(formKey, bussinessKey, variables);
//		流程实例ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        log.info("【启动流程】- 成功，processDefinitionId：{}", processDefinitionId);
        return processInstance;
    }

    public ProcessInstance startPlanProcess(String formKey, InspectionPlan inspectionPlan, String bussinessKey, Integer userID) {
        Map<String,Object> variables = new HashMap<>();
        variables.put("assignee0",userID.toString());
        variables.put("assignee1","1");
        variables.put("assignee2",inspectionPlan.getUserID());
        variables.put("bussinessKey", bussinessKey);
//		启动流程
        log.info("【启动流程】，formKey ：{},bussinessKey:{}", formKey, bussinessKey);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(formKey, bussinessKey, variables);
//		流程实例ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        log.info("【启动流程】- 成功，processDefinitionId：{}", processDefinitionId);
        return processInstance;
    }
    /**
     * 查看个人任务列表
     */
    public List<Map<String, Object>> myTaskList(Integer userID) {

        /**
         * 根据负责人id  查询任务
         */
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(userID.toString());

        List<Task> list = taskQuery.orderByTaskCreateTime().desc().list();

        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Task task : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("taskid", task.getId());
            map.put("taskname", task.getName());
            map.put("description", task.getDescription());
            map.put("priority", task.getPriority());
            map.put("owner", task.getOwner());
            map.put("assignee", task.getAssignee());
            map.put("delegationState", task.getDelegationState());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("createTime", task.getCreateTime());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            map.put("dueDate", task.getDueDate());
            map.put("category", task.getCategory());
            map.put("parentTaskId", task.getParentTaskId());
            map.put("tenantId", task.getTenantId());

            User userInfo = userService.getUserByID(task.getAssignee());
            //User userInfo = userService.findOneUserById(Long.valueOf(task.getAssignee()));
            map.put("assigneeUser", userInfo.getUserName());
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 根据BusinessKey查看单个个人任务列表
     */
    public Task singleTask(String formKey, Integer id) {
        //根据BusinessKey查询任务
        String businessKey = formKey+":"+id;
        return taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
    }

    /**
     * 查看个人任务信息
     */
    public List<Map<String, Object>> myTaskInfoList(Integer userid) {

        /**
         * 根据负责人id  查询任务
         */
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(userid.toString());

        List<Task> list = taskQuery.orderByTaskCreateTime().desc().list();

        List<Map<String, Object>> listmap = new ArrayList<>();
        for (Task task : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("taskid", task.getId());
            map.put("assignee", task.getAssignee());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("createTime", task.getCreateTime());
            ProcessInstance processInstance = runtimeService
                    .createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            if (processInstance != null) {
                String businessKey = processInstance.getBusinessKey();
                if (!StringUtils.isBlank(businessKey)) {
                    String type = businessKey.split(":")[0];
                    String id = businessKey.split(":")[1];
                    if ("inspectionPlan1".equals(type)) {
                        InspectionPlan inspectionPlan = inspectionPlanService.getPlanByID(id);
                        map.put("巡检信息",inspectionPlan);
                        /*User userInfo = userService.findOneUserById(evection.getUserid());
                        map.put("flowUserName", userInfo.getUsername());
                        map.put("flowType", "新增巡检计划");
                        map.put("flowcontent", "巡检计划" + evection.getNum() + "天");*/
                    }
                }
            }
            listmap.add(map);
        }

        return listmap;
    }


    /**
     * 完成提交任务
     */
    public void completeProcess(String remark, String taskId, Integer userId) {


        //任务Id 查询任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            log.error("completeProcess - task is null!!");
            return;
        }

        //任务对象  获取流程实例Id
        String processInstanceId = task.getProcessInstanceId();

        //设置审批人的userId
        Authentication.setAuthenticatedUserId(userId.toString());

        //添加记录
        taskService.addComment(taskId, processInstanceId, remark);
        System.out.println("-----------完成任务操作 开始----------");
        System.out.println("任务Id=" + taskId);
        System.out.println("负责人id=" + userId);
        System.out.println("流程实例id=" + processInstanceId);

        //完成办理
        taskService.complete(taskId);
        System.out.println("-----------完成任务操作 结束----------");
    }

    /**
     * 查询历史记录
     *
     * @param businessKey
     */
    public List<PlanFlowInfo> searchHistory(String businessKey) {
        List<HistoricProcessInstance> list1 = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
        if (CollectionUtils.isEmpty(list1)) {
            return null;
        }
        String processDefinitionId = list1.get(0).getProcessDefinitionId();
        // 历史相关Service
        List<HistoricActivityInstance> list = historyService
                .createHistoricActivityInstanceQuery()
                .processDefinitionId(processDefinitionId)
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        List<PlanFlowInfo> mapList = new ArrayList<>();
        for (HistoricActivityInstance hiact : list) {
            if (StringUtils.isBlank(hiact.getAssignee())) {
                continue;
            }
            PlanFlowInfo planFlowInfo = new PlanFlowInfo();
            planFlowInfo.setActID(hiact.getId());
            planFlowInfo.setProcessInstanceID(hiact.getProcessInstanceId());
            planFlowInfo.setUserID(hiact.getAssignee());
            User user = userService.getUserByID(hiact.getAssignee());
            planFlowInfo.setUserName(user.getUserName());
            planFlowInfo.setStartTime(hiact.getStartTime());
            planFlowInfo.setEndTime(hiact.getEndTime());
            mapList.add(planFlowInfo);
            System.out.println(planFlowInfo);
            /*System.out.println("活动ID:" + hiact.getId());
            System.out.println("流程实例ID:" + hiact.getProcessInstanceId());
            System.out.println("办理人ID：" + hiact.getAssignee());
            System.out.println("办理人名字：" + user.getUserName());
            System.out.println("开始时间：" + hiact.getStartTime());
            System.out.println("结束时间：" + hiact.getEndTime());
            System.out.println("==================================================================");*/
        }
        return mapList;
    }

}