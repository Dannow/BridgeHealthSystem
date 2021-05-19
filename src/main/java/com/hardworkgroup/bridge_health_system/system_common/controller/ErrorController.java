package com.hardworkgroup.bridge_health_system.system_common.controller;


import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hyl
 * @date: 2020/02/08
 **/
@RestController
@CrossOrigin
@Api(tags = "错误页面显示接口")
public class ErrorController {

    //公共错误跳转
    @RequestMapping(value = "/autherror")
    public Result autherror(int code){
        return code == 1 ? new Result(ResultCode.UNAUTHENTICATED) : new Result(ResultCode.UNAUTHORISE);
    }
}
