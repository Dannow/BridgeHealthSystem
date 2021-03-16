package com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.RoleAndUserRelations;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.permission_management.dao.RoleAndUserRelationsDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.RoleDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.UserDao;
import com.hardworkgroup.bridge_health_system.permission_management.service.UserService;
import com.hardworkgroup.bridge_health_system.system_common.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RoleAndUserRelationsDao roleAndUserRelationsDao;

    /**
     * 根据id查询用户
     */
    public User getUserByID(String userID){
        //return userDao.selectByPrimaryKey(userID);
        return userDao.getUserByID(userID);
    }

    /**
     * 根据mobile查询用户
     */
    public User findByPhone(String mobile){
        return userDao.findByPhone(mobile);
    }

    /**
     * 保存用户
     */
    @Transactional
    public void save(User user,String roleID) {
        //设置主键
        //        //String id = idWorker.nextId() + "";
        //        //md5加密密码
        //        //String password = new Md5Hash("123456", user.getUserPhone(), 3).toString();
        String password = new Md5Hash(user.getUserPassword(), user.getUserPhone(), 3).toString();
        user.setUserPassword(password);//设置初始密码
        RoleAndUserRelations rAus = new RoleAndUserRelations();
        rAus.setRoleID(roleID);
        userDao.insertByKey(user);
        //调用dao保存用户
        String tempID = String.valueOf(user.getUserID());
        rAus.setUserID(tempID);
        roleAndUserRelationsDao.insertByUserID(rAus);
    }

    /**
     * 更新用户
     */
    public void update(String id , User user){
        User tempUser = userDao.getUserByID(id);
        if (!ObjectUtils.isEmpty(tempUser) && !ObjectUtils.isEmpty(user)){
            tempUser.setUserName(user.getUserName());
            String password = new Md5Hash(user.getUserPassword() , user.getUserPhone() , 3).toString();
            tempUser.setUserPassword(password);
            tempUser.setUserEmail(user.getUserEmail());
            tempUser.setUserLevel(user.getUserLevel());
            tempUser.setUserStatus(user.getUserStatus());
        }
        //更新用户
        userDao.updateByKey(tempUser);
        //userDao.updateByPrimaryKey(tempUser);
    }

    /**
     * 查询全部用户列表
     */
    public PageInfo<User> findAll(int pageNum, int pageSize){
        Page<User> page = PageHelper.startPage(pageNum,pageSize);
        List<User> users =  userDao.selectAllUsers();
        PageInfo<User> pageInfo = new PageInfo<>(users,5);
        for (User user : pageInfo.getList()) {
            Set<Role> roles = roleDao.getRoleByUserId(user.getUserID());
            user.setRoles(roles);
        }
        /*System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.getPageSize());
        System.out.println(pageInfo.getPages());
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.isHasPreviousPage());
        System.out.println(pageInfo.isHasNextPage());
        System.out.println(Arrays.toString(pageInfo.getNavigatepageNums()) );*/

        return pageInfo;
    }


    /**
     * 根据id删除用户
     */
    public void deleteById(String id){
        userDao.deleteByKey(id);
    }

    /**
     * 分配角色
     * @param userId    用户id
     * @param roleIds   要分配的角色id
     */
    public void assignRoles(String userId, List<String> roleIds) {
        //根据id查询用户
        RoleAndUserRelations target = new RoleAndUserRelations();
        Set<RoleAndUserRelations> rAndUs = roleAndUserRelationsDao.findByUserId(userId);
        target.setUserID(userId);
        for (String roleId : roleIds) {
            target.setRoleID(roleId);
            if(!rAndUs.contains(target)){
                roleAndUserRelationsDao.insertByUserID(target);
            }

        }

        /*//根据id查询用户
        User user = userDao.getUserByID(userId);
        //设置用户的角色集合
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.getRoleByID(roleId);
            roleAndUserRelationsDao.updateByUserID(userId);
            roles.add(role);
        }
        //设置用户和角色集合的关系
        user.setRoles(roles);
        //更新用户
        userDao.updateByPrimaryKey(user);*/
    }

    /**
     *  批量用户保存
     * @param list  用户list
     * @param companyId 用户所属公司id
     * @param companyName   用户所属公司名称
     */
    @Transactional
    public void saveAll(List<User> list, String companyId, String companyName) {
        for (User user : list) {
            //默认密码
            user.setUserPassword(new Md5Hash("123456" , user.getUserPhone() , 3).toString());
            //id
            user.setUserID(idWorker.nextId() + "");
            //基本属性
            //user.setCompanyId(companyId);
            //user.setCompanyName(companyName);
            //user.setInServiceStatus(1);
            user.setUserStatus(1);
            user.setUserLevel("user");

            //填充部门的属性
            /*Department department = companyFeignClient.findByCode(user.getDepartmentId(), companyId);
            if (department != null){
                user.setDepartmentId(department.getId());
                user.setDepartmentName(department.getName());
            }*/

            //userDao.save(user);
        }
    }


}
