package com.hardworkgroup.bridge_health_system.activiti.controller;

import com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl.ActFlowCommServiceImpl;
import com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl.FlowServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity.FlowInfo;
import com.hardworkgroup.bridge_health_system.system_common.controller.BaseController;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 流程管理
 */
//解决跨域
@CrossOrigin
@RestController
public class FlowController extends BaseController {

    @Autowired
    private FlowServiceImpl flowService;

    @Autowired
    private ActFlowCommServiceImpl actFlowCommService;

    /**
     * 查询所有流程
     * @return
     */
    @GetMapping("/flow/findAll")
    public List<FlowInfo> findAllFlow(){
        return flowService.findAllFlow();
    }
    /**
     * 部署流程
     * @param request
     * @return 0-部署失败  1- 部署成功  2- 已经部署过
     */
    @PutMapping("/flow/deployment/{id}")
    public Result deployment(HttpServletRequest request, @PathVariable(name = "id")String id){
        FlowInfo flowInfo = flowService.findOneFlow(id);
        if(flowInfo.getState() == 0){
            return new Result(ResultCode.FAIL,"2:已经部署过");
        }
        actFlowCommService.saveNewDeploy(flowInfo);
        return new Result(ResultCode.SUCCESS,flowService.updateDeployState(id));
    }

    /**
     * 查询用户任务
     * @param request
     * @return
     */
    @GetMapping("/flow/findUserTask")
    public Result findUserTask(HttpServletRequest request){
        List<Map<String, Object>> userTasks = flowService.findUserTask(this.userId);
        return new Result(ResultCode.SUCCESS,userTasks);
    }

    /**
     * 查询任务详细信息
     * @param request
     * @return
     */
    @GetMapping("/flow/findTaskInfo")
    public List<Map<String,Object>> findTaskInfo(HttpServletRequest request){
        //Long userId = (Long)request.getSession().getAttribute("userid");
        return flowService.findTaskInfo(this.userId);
    }

    /**
     * 完成任务
     * @param taskId
     */
    @PutMapping("/flow/completeTask/{taskId}")
    public Result completeTask(@PathVariable("taskId")String taskId){
        flowService.completeTask(taskId,this.userId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询
     * @return
     */
    @GetMapping("/flow/findFlowTask/{id}")
    public Map<String,Object> findFlowTask(@PathVariable(name = "id")Long id){
        String businessKey = "evection:"+id;
        actFlowCommService.searchHistory(businessKey);
        return null;
    }
}
