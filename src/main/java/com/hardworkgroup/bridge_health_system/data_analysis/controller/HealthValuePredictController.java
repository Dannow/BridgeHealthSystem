package com.hardworkgroup.bridge_health_system.data_analysis.controller;

import com.hardworkgroup.bridge_health_system.data_analysis.util.ConnectionPython;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//解决跨域
@CrossOrigin
@RestController
@Slf4j
@Api(tags = "桥梁健康判断接口")
@RequestMapping("/dataAnalysis")
public class HealthValuePredictController {

    // 桥梁健康的判断值
    @RequestMapping(value = "/healthValuePredict" , method = RequestMethod.GET)
    public Result getHealthValuePredict() throws IOException {
        String LongTimePrediction = ConnectionPython.connectionPython("HealthValuePredictMain/");
        return new  Result(ResultCode.SUCCESS, LongTimePrediction);
    }
}
