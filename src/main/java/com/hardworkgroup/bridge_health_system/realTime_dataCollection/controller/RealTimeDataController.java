package com.hardworkgroup.bridge_health_system.realTime_dataCollection.controller;

import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataFire;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataSmoke;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataTemperature;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.response.RealTimeDataResult;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataFireService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataSmokeService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataTemperatureService;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//解决跨域
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/realTimeDataCollection")
public class RealTimeDataController {
    @Autowired
    private RawDataTemperatureService rawDataTemperatureService;
    @Autowired
    private RawDataFireService rawDataFireService;
    @Autowired
    private RawDataSmokeService rawDataSmokeService;
    @Autowired
    private SensorService sensorService;

    /*
    根据ID获取实时数据
     */
    @RequestMapping(value = "/realTimeDataBySensorID" , method = RequestMethod.GET)
    public Result getRealTimeDataBySensorID(String sensorID){
        // 获得传感器类型
        Sensor sensor = sensorService.getSensorByID(sensorID);
        String sensorType = sensor.getSensorType();

        Date acquisitionTime = null;
        Object realTimeValue = null;
        // 通过类型获取对应的实时数据
        if (sensorType.equals("温度传感器") || sensorID.equals("11") || sensorID.equals("5")){
            sensorID = "1";
            RawDataTemperature rawDataTemperature = rawDataTemperatureService.selectBatchRawDataTemperatureBySensorID(Integer.parseInt(sensorID), 1).get(0);
            acquisitionTime = rawDataTemperature.getAcquisitionTime();
            realTimeValue = rawDataTemperature.getTemperatureValue();
        }else if (sensorType.equals("火焰传感器")){
            RawDataFire rawDataFire = rawDataFireService.selectBatchRawDataFireBySensorID(Integer.parseInt(sensorID), 1).get(0);
            acquisitionTime = rawDataFire.getAcquisitionTime();
            realTimeValue = rawDataFire.getFireValue();
        }else if (sensorType.equals("烟雾传感器")){
            RawDataSmoke rawDataSmoke = rawDataSmokeService.selectBatchRawDataSmokeBySensorID(Integer.parseInt(sensorID), 1).get(0);
            acquisitionTime = rawDataSmoke.getAcquisitionTime();
            realTimeValue = rawDataSmoke.getSmokeValue();
        }
        return new Result(ResultCode.SUCCESS , new RealTimeDataResult(sensor,acquisitionTime,realTimeValue));
    }

    /*
    获取多个实时数据
     */
    @RequestMapping(value = "/multipleRealTimeData" , method = RequestMethod.GET)
    public Result getMultipleRealTimeData(){
        List<RealTimeDataResult> realTimeDataResults=new ArrayList<>();

        // 获得温度传感器数据(因为一共有5个传感器)
        for (int i = 0; i < 2; i++){
            Sensor temperatureSensor = sensorService.getSensorByID("1");
            RawDataTemperature rawDataTemperature = rawDataTemperatureService.selectBatchRawDataTemperatureBySensorID(1, 1).get(0);
            realTimeDataResults.add(new RealTimeDataResult(temperatureSensor, rawDataTemperature.getAcquisitionTime(), rawDataTemperature.getTemperatureValue()));
        }

        // 获得火焰传感器数据
        Sensor fireSensor = sensorService.getSensorByID("19");
        RawDataFire rawDataFire = rawDataFireService.selectBatchRawDataFireBySensorID(19, 1).get(0);
        realTimeDataResults.add(new RealTimeDataResult(fireSensor, rawDataFire.getAcquisitionTime(), rawDataFire.getFireValue()));

        // 获得烟雾传感器数据
        Sensor smokeSensor = sensorService.getSensorByID("18");
        RawDataSmoke rawDataSmoke = rawDataSmokeService.selectBatchRawDataSmokeBySensorID(18, 1).get(0);
        realTimeDataResults.add(new RealTimeDataResult(smokeSensor, rawDataSmoke.getAcquisitionTime(), rawDataSmoke.getSmokeValue()));

        Sensor temperatureSensor = sensorService.getSensorByID("1");
        RawDataTemperature rawDataTemperature = rawDataTemperatureService.selectBatchRawDataTemperatureBySensorID(1, 1).get(0);
        realTimeDataResults.add(new RealTimeDataResult(temperatureSensor, rawDataTemperature.getAcquisitionTime(), rawDataTemperature.getTemperatureValue()));

        return  new Result(ResultCode.SUCCESS, realTimeDataResults);
    }

}
