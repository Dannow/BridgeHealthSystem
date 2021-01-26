package com.hardworkgroup.bridge_health_system.system_common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/*
 * 数据响应对象
 * */
@Component
@Data
@NoArgsConstructor
public class Result {
    private boolean success;    //是否成功
    private Integer code;   //返回码
    private String message;     //返回信息
    private Object data;    //返回数据


    /**
     * 构造方法
     * @param code：ResultCode中的枚举类型
     *            当只有一个参数时把ResultCode中的属性赋值到Result中
     */
    public Result(ResultCode code){
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
    }

    /**
     * 构造方法
     * @param code：ResultCode中的枚举类型
     * @param data：响应的数据
     */
    public Result(ResultCode code,Object data) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
        this.data = data;
    }

    /**
     *
     * @param code
     * @param message
     * @param success
     */
    public Result(Integer code,String message,boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }


    /**
     * 调用函数，第一个构造函数
     * @return
     */
    public static Result SUCCESS(){
        return new Result(ResultCode.SUCCESS);
    }


    public static Result ERROR(){
        return new Result(ResultCode.SERVER_ERROR);
    }


    public static Result FAIL(){
        return new Result(ResultCode.FAIL);
    }
}

