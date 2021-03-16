package com.hardworkgroup.bridge_health_system.permission_management.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.response.ProfileResult;
import com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl.RoleAndUserRelationsServiceImpl;
import com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl.UserServiceImpl;
import com.hardworkgroup.bridge_health_system.system_common.controller.BaseController;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import com.hardworkgroup.bridge_health_system.system_common.utils.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
//解决跨域
@CrossOrigin
@RestController
@RequestMapping("/permissionManagement")
public class UserController extends BaseController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    RoleAndUserRelationsServiceImpl roleAndUserRelationsService;
    /**
     * 获取所有用户列表
     * @return 用户结果
     */
    @RequestMapping(value = "/user" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<User> pageInfo = userService.findAll(pageNum, pageSize);
        PageResult<User> pageResult = new PageResult<User>(pageInfo.getTotal(),pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,Object> loginMap){
        String phone = (String) loginMap.get("phone");
        String password = (String) loginMap.get("password");
        try {
            //构造登录令牌
            password = new Md5Hash(password , phone , 3).toString();
            UsernamePasswordToken upToken = new UsernamePasswordToken(phone , password);
            //获取subject
            Subject subject = SecurityUtils.getSubject();
            //调用login方法,进入realm完成认证
            subject.login(upToken);
            //获取sessionId
            String sessionId = (String) subject.getSession().getId();
            //构造返回结果
            return new Result(ResultCode.SUCCESS , sessionId);
        }catch (Exception e){
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }
    }
    /**
     * 用户登录成功之后,获取用户信息
     */
    @RequestMapping(value = "/profile" , method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws Exception {

        //获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //subject获取所有的安全集合
        PrincipalCollection principals = subject.getPrincipals();
        //获取安全数据
        ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();

        return new Result(ResultCode.SUCCESS,result);
    }

    /**
     * 分配角色
     */
    //@RequiresPermissions(value = "API-USER-ASSIGN-ROLE")
    @RequestMapping(value = "/user/assignRoles" , method = RequestMethod.PUT)
    public Result save(@RequestBody Map<String,Object> map){
        //获取被分配的用户id
        String userId = (String) map.get("userID");
        //获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //调用service完成角色分配
        userService.assignRoles(userId , roleIds);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 保存用户
     */
    @RequestMapping(value = "/user/import" , method = RequestMethod.POST)
    public Result importUser(@RequestBody Map<String,Object> map) throws Exception{
        User user = BeanMapUtils.mapToBean(map,User.class);
        String roleID = (String) map.get("roleID");
        //添加用户
        userService.save(user,roleID);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询
     */
    @RequestMapping(value = "/user/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加roleIds(用户已经具有的角色id数组)
        User user = userService.getUserByID(id);
        return new Result(ResultCode.SUCCESS , user);
    }

    /**
     * 根据Id修改User
     */
    @RequestMapping(value = "/user/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody User user){
        //调用Service更新
        userService.update(id , user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除
     */
    @RequiresPermissions(value = "POINT-USER-DELETE")
    @RequestMapping(value = "/user/{id}" , method = RequestMethod.DELETE , name = "POINT-USER-DELETE")
    public Result delete(@PathVariable(value = "id") String id){
        //调用userService进行删除
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据userID删除roleAndUserRelation.roleID
     */
    @RequestMapping(value = "/user/{userID}/role/{roleID}" , method = RequestMethod.DELETE )
    public Result deleteRole(@PathVariable(value = "userID") String userID,@PathVariable(value = "roleID") String roleID){
        //调用roleAndUserRelationsService进行删除
        roleAndUserRelationsService.deleteRoleAndUserByID(userID,roleID);
        return new Result(ResultCode.SUCCESS);
    }
}
