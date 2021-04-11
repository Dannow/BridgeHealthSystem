package com.hardworkgroup.bridge_health_system.system_common.entity;

/*
 * 公共返回码
 *
 * 枚举型：
 * 通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
 * 赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值；如果不赋值则不能写构造器，赋值编译也出错
 * */
public enum ResultCode {

    //枚举类型
    SUCCESS(true,10000,"操作成功"),
    //系统错误返回码
    FAIL(false,10001,"操作失败"),
    UNAUTHENTICATED(false,10002,"您还未登录"),
    UNAUTHORISE(false,10003,"权限不足"),
    SERVER_ERROR(false,10004,"抱歉，系统繁忙，请稍后重试！"),

    //传感器长期与否错误
    SensorIsShort_ERROR(false,20000,"你需要预测的传感器与数据不符"),

    //用户操作返回码
    MOBILEORPASSWORDERROR(false,20001,"手机号码或密码错误"),

    //用户操作返回码
    CHECKERROR(false,30001,"无巡检计划需要打卡");

    //类型中的参数定义
    boolean success;    //操作是否成功
    int code;    //操作代码
    String message;     //提示信息

    //构造函数
    ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    //方法
    public boolean success(){
        return success;
    }
    public int code(){
        return code;
    }
    public String message(){
        return message;
    }

}

