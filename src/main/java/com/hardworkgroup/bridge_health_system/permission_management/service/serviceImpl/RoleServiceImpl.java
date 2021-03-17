package com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.*;
import com.hardworkgroup.bridge_health_system.permission_management.dao.PermissionAndRoleRelationsDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.PermissionDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.RoleDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.UserDao;
import com.hardworkgroup.bridge_health_system.permission_management.service.RoleService;
import com.hardworkgroup.bridge_health_system.system_common.utils.IdWorker;
import com.hardworkgroup.bridge_health_system.system_common.utils.PermissionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl  implements RoleService {

    @Resource
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionAndRoleRelationsDao permissionAndRoleRelationsDao;

    /**
     * 分配权限
     * @param roleId    角色id
     * @param permIds   权限id列表
     */
    public void assignPerms(String roleId, List<String> permIds) {
        /*//获取分配的角色对象
        Role role = roleDao.getRoleByID(roleId);
        //构造角色的权限集合
        Set<Permission> perms = role.getPermissions();*/
        //维护角色-权限表
        PermissionAndRoleRelations target = new PermissionAndRoleRelations();
        target.setRoleID(roleId);
        for (String permId : permIds) {
            target.setPermissionID(permId);
            /*Permission perm = permissionDao.getPermissionByID(permId);
            //往角色权限集合中添加新的权限
            perms.add(perm);*/
            permissionAndRoleRelationsDao.insertByRoleID(target);
        }
        //role.setPermissions(perms);
        /*//获取分配的角色对象
        Role role = roleDao.selectByPrimaryKey(roleId);
        //构造角色的权限集合
        Set<Permission> perms = new HashSet<>();
        for (String permId : permIds) {
            Permission perm = permissionDao.selectByPrimaryKey(permId);
            //需要根据父id和类型查询API权限列表
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, perm.getPermissionID());
            perms.addAll(apiList);//自动赋予API权限
            perms.add(perm);//当前菜单或按钮的权限
        }
        //设置角色和权限的关系
        role.setPermissions(perms);
        //更新角色
        roleDao.updateByPrimaryKey(role);*/
    }

    /**
     * 添加角色
     */
    public void save(Role role){
        //role.setRoleID(idWorker.nextId() + "");
        roleDao.insertByKey(role);
    }

    /**
     * 更新角色
     */
    public void update(Role role){
        //多表联查对性能有影响
        Role target = roleDao.getRoleByID(role.getRoleID());
        target.setRoleDescription(role.getRoleDescription());
        target.setRoleName(role.getRoleName());
        roleDao.updateByKey(target);
    }

    /**
     * 根据ID查询角色
     */
    public Role findById(String id){
        return roleDao.getRoleByID(id);
    }

    /**
     * 根据id删除角色
     */
    public void delete(String id){
        roleDao.deleteByKey(id);
    }

    /**
     * 查询所有角色:
     *      根据内部维护的公司id进行查询该公司的所有角色
     */
    public PageInfo<Role> findAll(int pageNum, int pageSize){
        Page<Role> page = PageHelper.startPage(pageNum,pageSize);
        List<Role> users =  roleDao.selectAllRoles();
        PageInfo<Role> pageInfo = new PageInfo<>(users,5);
        for (Role role : pageInfo.getList()) {
            Set<Permission> permissions = permissionDao.getPermissionByRoleID(role.getRoleID());
            role.setPermissions(permissions);
        }
        return pageInfo;
    }
    public List<Role> findAll(String companyId){
        return roleDao.selectAll();
        //return roleDao.findAll(getSpec(companyId));
    }
}
