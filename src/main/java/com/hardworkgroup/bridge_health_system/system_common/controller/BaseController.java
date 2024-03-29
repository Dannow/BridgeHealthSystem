package com.hardworkgroup.bridge_health_system.system_common.controller;


import com.hardworkgroup.bridge_health_system.common_model.domain.system.response.ProfileResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: hyl
 * @date: 2020/01/04
 **/
public class BaseController {

    public HttpServletRequest request;
    public HttpServletResponse response;
    //protected String companyId;
    //protected String companyName;
    protected Integer userId;
    protected String userPicture;

    //使用shiro获取
    //进入控制器之前执行的方法
    @ModelAttribute
    public void serResAndReq(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;

        //获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //subject获取所有的安全集合
        PrincipalCollection principals = subject.getPrincipals();
        if (principals != null && !principals.isEmpty()){
            //获取安全数据
            ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();
            //this.companyId = result.getCompanyId();
            //this.companyName = result.getCompany();
            this.userId = result.getUserId();
            this.userPicture = result.getUserPicture();
        }
    }


}
