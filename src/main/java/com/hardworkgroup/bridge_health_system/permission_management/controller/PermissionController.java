package com.hardworkgroup.bridge_health_system.permission_management.controller;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Permission;
import com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl.PermissionServiceImpl;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import com.hardworkgroup.bridge_health_system.system_common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: hyl
 * @date: 2020/01/15
 **/

//解决跨域
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/permissionManagement")
public class PermissionController {

    @Autowired
    private PermissionServiceImpl permissionService;

    /**
     * 添加权限
     * @return
     */
    @RequestMapping(value = "/permission" , method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> map) throws Exception {
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }
    
    /**
     * 修改
     */
    @RequestMapping(value = "/permission/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody Map<String,Object> map) throws Exception {
        //构造id
        map.put("permissionID" , id);
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询列表权限
     * @return
     */
    @RequestMapping(value = "/permission" , method = RequestMethod.GET)
    public Result findAll(@RequestParam() Map<String,Object> map){
        List<Permission> list = permissionService.findAll();
        return new Result(ResultCode.SUCCESS , list);
    }


    /**
     * 根据Id查询
     */
    @RequestMapping(value = "/permission/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) throws CommonException {
        Map<String,Object> map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS , map);
    }



    /**
     * 根据Id删除
     */
    @RequestMapping(value = "/permission/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) throws CommonException {
        permissionService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询所有企业可以看到的menu
     */
    @RequestMapping(value = "/permission/getMenus" , method = RequestMethod.GET)
    public Result getMenus() throws CommonException {
        List<Permission> menus = permissionService.getMenus();
        return new Result(ResultCode.SUCCESS , menus);
    }


}
