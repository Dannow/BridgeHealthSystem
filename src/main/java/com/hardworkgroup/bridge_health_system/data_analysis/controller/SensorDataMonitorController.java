package com.hardworkgroup.bridge_health_system.data_analysis.controller;

import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.entity.OriginalData;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorCorrelationDataResult;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorMonitorDataResult;
import com.hardworkgroup.bridge_health_system.data_analysis.service.SensorDataService;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//解决跨域
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/dataAnalysis")
public class SensorDataMonitorController {
    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorDataService sensorDataService;

    /*
     * 通过传感器类型获得传感器原始数据
     * */
    @RequestMapping(value = "/sensorDataBySensorType" , method = RequestMethod.POST)
    public Result findSensorDataBySensorType(@RequestBody Map<String,String > map) throws Exception {
        // 获得类型
        String sensorType = map.get("sensorType");
        // 根据类型获得传感器
        List<Sensor> sensorList = sensorService.getSensorOriginalDataBySensorType(sensorType);
        // 获得最新的一份原始数据
        OriginalData originalData = null;
        List<String> sensorNameList = new ArrayList<>();
        // 遍历每个传感器，获得传感器名
        for (int i = 0; i < sensorList.size(); i++){
            // 获得传感器
            Sensor sensor = sensorList.get(i);
            String sensorName = sensor.getSensorName();
            String columnIndex = sensorName.substring(sensorName.length()-1, sensorName.length());
            // 只取同一位置的第一个传感器
            if (Integer.parseInt(columnIndex) % 2 == 1){
                sensorNameList.add(sensorName);
                // 获得传感器对应的原始数据
                Set<OriginalData> originalDataSet = sensor.getOriginalDatas();
                Iterator<OriginalData> iterator = originalDataSet.iterator();
                while (iterator.hasNext()){
                    originalData = iterator.next();
                }
            }
        }
        List<SensorMonitorDataResult> sensorOriginalData = sensorDataService.getSensorDataInSensorType(originalData.getOriginalDataPath(), sensorNameList, 18);
        return new Result(ResultCode.SUCCESS, sensorOriginalData);
    }

    /*
     * 通过传感器类型和时间获得传感器原始数据
     * */
    @RequestMapping(value = "/sensorDataBySensorTypeInTime" , method = RequestMethod.POST)
    public Result findSensorDataBySensorTypeInTime(@RequestBody Map<String,String > map) throws Exception {
        // 获得类型
        String sensorType = map.get("sensorType");
        // 获得时间
        String time = map.get("targetTime");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date targetTime = formatter.parse(time);

        // 根据类型获得传感器
        List<Sensor> sensorList = sensorService.getSensorOriginalDataBySensorType(sensorType);
        // 获得最新的一份原始数据
        OriginalData originalData = null;
        List<String> sensorNameList = new ArrayList<>();
        // 遍历每个传感器，获得传感器名
        for (int i = 0; i < sensorList.size(); i++){
            // 获得传感器
            Sensor sensor = sensorList.get(i);
            String sensorName = sensor.getSensorName();
            String columnIndex = sensorName.substring(sensorName.length()-1, sensorName.length());
            // 只取同一位置的第一个传感器
            if (Integer.parseInt(columnIndex) % 2 == 1){
                sensorNameList.add(sensorName);
                // 获得传感器对应的原始数据
                Set<OriginalData> originalDataSet = sensor.getOriginalDatas();
                Iterator<OriginalData> iterator = originalDataSet.iterator();
                while (iterator.hasNext()){
                    originalData = iterator.next();
                }
            }
        }
        List<SensorMonitorDataResult> sensorOriginalData = sensorDataService.getSensorDataInSensorTypeByTime(originalData.getOriginalDataPath(), sensorNameList, targetTime);
        return new Result(ResultCode.SUCCESS, sensorOriginalData);
    }
}
