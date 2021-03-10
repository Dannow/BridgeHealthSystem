package com.hardworkgroup.bridge_health_system.permission_management.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.RoleAndUserRelations;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.response.RoleResult;
import com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl.RoleServiceImpl;
import com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl.RoleAndUserRelationsServiceImpl;
import com.hardworkgroup.bridge_health_system.system_common.controller.BaseController;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: hyl
 * @date: 2020/01/15
 **/

//解决跨域
@CrossOrigin
@RestController
@RequestMapping(value = "/permissionManagement")
public class RoleController extends BaseController {

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private RoleAndUserRelationsServiceImpl roleAndUserRelationsService;

    /**
     * 分配权限
     */
    @RequestMapping(value = "/role/assignPrem" , method = RequestMethod.PUT)
    public Result save(@RequestBody Map<String,Object> map){

        //获取被分配的角色id
        String roleId = (String) map.get("roleID");
        //获取到权限的id列表
        List<String> permIds = (List<String>) map.get("permIds");
        //调用service完成权限分配
        roleService.assignPerms(roleId , permIds);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 添加角色
     */
    @RequestMapping(value = "/role/import", method = RequestMethod.POST)
    public Result add(@RequestBody Role role) throws Exception {
        //role.setCompanyId(companyId);
        roleService.save(role);
        return Result.SUCCESS();
    }

    /**
     * 更新角色
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id") String id, @RequestBody Role role) throws Exception {
        role.setRoleID(id);
        roleService.update(role);
        return Result.SUCCESS();
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name = "id") String id) throws Exception {
        roleService.delete(id);
        return Result.SUCCESS();
    }

    /**
     * 根据ID查询角色信息(包括权限信息）
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        Role role = roleService.findById(id);
        //分装角色的返回结果
        RoleResult roleResult = new RoleResult(role);
        return new Result(ResultCode.SUCCESS,roleResult);
    }

    /**
     * 分页查询所有角色
     */
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public Result findByPage(@RequestBody Map<String,Object> map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<Role> pageInfo = roleService.findAll(pageNum, pageSize);
        PageResult<Role> pageResult = new PageResult<Role>(pageInfo.getTotal(),pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 根据用户id获取全部角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/role/userId/{id}", method = RequestMethod.GET)
    public Result findRolesByUserId(@PathVariable(name = "id") String id) {
        Set<RoleAndUserRelations> roleByUserId = roleAndUserRelationsService.findRoleByUserId(id);
        if (!ObjectUtils.isEmpty(roleByUserId)){
            Set<Role> roles = roleAndUserRelationsService.getRoleDetailByRoleId(roleByUserId);
            if (!ObjectUtils.isEmpty(roles)){
                return new Result(ResultCode.SUCCESS , roles);
            }
        }
        return new Result(ResultCode.SUCCESS);
    }

}
