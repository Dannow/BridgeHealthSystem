package com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.RoleAndUserRelations;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.permission_management.dao.RoleAndUserRelationsDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.RoleDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.UserDao;
import com.hardworkgroup.bridge_health_system.permission_management.service.UserService;
import com.hardworkgroup.bridge_health_system.system_common.utils.IdWorker;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return userDao.selectByPrimaryKey(userID);
        //return userDao.getUserByID(userID);
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
    public void save(User user) {
        //设置主键
        String id = idWorker.nextId() + "";
        //md5加密密码
        String password = new Md5Hash("123456", user.getUserPhone(), 3).toString();
        //String password = new Md5Hash(user.getUserPassword(), user.getUserPhone(), 3).toString();
        user.setUserLevel("user");
        user.setUserPassword(password);//设置初始密码
        user.setUserStatus(1);
        user.setUserID(id);
        //调用dao保存用户
        userDao.insertByKey(user);
    }

    /**
     * 更新用户
     */
    public void update(String id , User user){
        User tempUser = userDao.getUserByID(id);
        if (!ObjectUtils.isEmpty(tempUser) && !ObjectUtils.isEmpty(user)){
            tempUser.setUserName(user.getUserName());
            tempUser.setUserPassword(user.getUserPassword());
            //String password = new Md5Hash("123456" , "13800000812" , 3).toString();
            //tempUser.setUserPassword(password);
            //tempUser.setWorkNumber(user.getWorkNumber());
            //tempUser.setTimeOfEntry(user.getTimeOfEntry());
        }
        //更新用户
        userDao.updateByKey(tempUser);
        //userDao.updateByPrimaryKey(tempUser);
    }

    /**
     * 查询全部用户列表
     */
    public List<User> findAll(){
        return userDao.selectAllUsers();
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
        target.setUserID(userId);
        for (String roleId : roleIds) {
            target.setRoleID(roleId);
            roleAndUserRelationsDao.insertByUserID(target);
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
