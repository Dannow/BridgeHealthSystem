package com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl;


import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Permission;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.PermissionApi;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.PermissionMenu;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.PermissionPoint;
import com.hardworkgroup.bridge_health_system.permission_management.dao.PermissionApiDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.PermissionDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.PermissionMenuDao;
import com.hardworkgroup.bridge_health_system.permission_management.dao.PermissionPointDao;
import com.hardworkgroup.bridge_health_system.permission_management.service.PermissionService;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import com.hardworkgroup.bridge_health_system.system_common.exception.CommonException;
import com.hardworkgroup.bridge_health_system.system_common.utils.BeanMapUtils;
import com.hardworkgroup.bridge_health_system.system_common.utils.IdWorker;
import com.hardworkgroup.bridge_health_system.system_common.utils.PermissionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: hyl
 * @date: 2020/01/09
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionPointDao permissionPointDao;
    
    @Autowired
    private PermissionApiDao permissionApiDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存新增权限
     */
    public void save(Map<String,Object> map) throws Exception {
        //设置主键
        String id = idWorker.nextId() + "";
        //通过map构造权限对象
        Permission perm = BeanMapUtils.mapToBean(map , Permission.class);
        //perm.setPermissionID(id);
        //保存权限
        permissionDao.insertPermission(perm);
        //根据类型构造不同的资源对象(菜单,按钮,api)
        int type = perm.getPermissionType();
        switch (type){
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setMenuID(perm.getPermissionID());
                permissionMenuDao.insertPermissionMenu(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setPointID(perm.getPermissionID());
                permissionPointDao.insertPermissionPoint(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setApiID(perm.getPermissionID());
                permissionApiDao.insertPermissionApi(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
    }

    /**
     * 更新
     */
    public void update(Map<String,Object> map) throws Exception {
        Permission perm = BeanMapUtils.mapToBean(map , Permission.class);
        //通过传递的权限id查询权限
        Permission permission = permissionDao.getPermissionByID(perm.getPermissionID());
        permission.setPermissionName(perm.getPermissionName());
        //未解决改变权限类型
        //permission.setPermissionType(perm.getPermissionType());
        permission.setPermissionCode(perm.getPermissionCode());
        permission.setPermissionDescription(perm.getPermissionDescription());
        permission.setPid(perm.getPid());
        permission.setEnVisible(perm.getEnVisible());
        //根据类型构造不同的资源
        int type = perm.getPermissionType();
        switch (type){
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setMenuID(perm.getPermissionID());
                permissionMenuDao.updatePermissionMenuByKey(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setPointID(perm.getPermissionID());
                permissionPointDao.updatePermissionPointByKey(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setApiID(perm.getPermissionID());
                permissionApiDao.updatePermissionApiByKey(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //更新权限,更新资源
        permissionDao.updatePermissionByKey(permission);
    }


    /**
     * 根据id查询权限
     */
    public Map<String,Object> findById(String id) throws CommonException {
        //查询权限
        Permission perm = permissionDao.getPermissionByID(id);
        //根据权限的类型查询资源
        int type = perm.getPermissionType();
        //构造map集合
        Object object = null;

        if (type == PermissionConstants.PY_MENU){
            object = permissionMenuDao.getPermissionMenuByID(id);
        }else if (type == PermissionConstants.PY_POINT){
            object = permissionPointDao.getPermissionPointByID(id);
        }else if (type == PermissionConstants.PY_API){
            object = permissionApiDao.getPermissionApiByID(id);
        }else{
            throw new CommonException(ResultCode.FAIL);
        }

        Map<String, Object> map = BeanMapUtils.beanToMap(object);
        map.put("permissionName" , perm.getPermissionName());
        map.put("permissionType" , perm.getPermissionType());
        map.put("permissionCode" , perm.getPermissionCode());
        map.put("permissionDescription" , perm.getPermissionDescription());
        map.put("pid" , perm.getPid());
        map.put("enVisible" , perm.getEnVisible());

        return map;
    }


    /**
     * 查询全部用户列表
     * type     :   查询全部权限列表
     *          0 : 菜单 + 按钮(权限点)    1 ： 菜单  2 : 按钮(权限点) 3 ： API接口
     * enVisible :
     *          0 ： 查询SaaS平台的最高权限   1 ： 查询企业的权限
     */
    public List<Permission> findAll(){
        return permissionDao.selectAllPermissions();
    }

    /**
     * 根据id删除权限
     */
    public void deleteById(String id) throws CommonException {
        //通过传递的权限id查询权限
        Permission permission = permissionDao.getPermissionByID(id);
        permissionDao.deletePermissionByKey(permission);
        //根据类型构造不同的资源
        int type = permission.getPermissionType();
        switch (type){
            case PermissionConstants.PY_MENU:
                permissionMenuDao.deletePermissionMenuByKey(id);
                break;
            case PermissionConstants.PY_POINT:
                permissionPointDao.deletePermissionPointByKey(id);
                break;
            case PermissionConstants.PY_API:
                permissionApiDao.deletePermissionApiByKey(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }

    }

    /**
     * 查询所有企业可以看到的menu
     */
    public List<Permission> getMenus() throws CommonException {
        /*List<Permission> pers = permissionDao.findByTypeAndEnVisible(1, "1");
        return pers;*/
        return null;
    }
}

