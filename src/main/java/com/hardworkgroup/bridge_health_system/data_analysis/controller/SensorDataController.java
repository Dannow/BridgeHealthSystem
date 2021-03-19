package com.hardworkgroup.bridge_health_system.data_analysis.controller;

import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.entity.OriginalData;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorDataResult;
import com.hardworkgroup.bridge_health_system.data_analysis.service.SensorDataService;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (SensorController)表控制层
 *
 * @author makejava
 * @since 2020-12-27 10:35:05
 */
//解决跨域
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/bridgeConfiguration")
public class SensorDataController {
    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorDataService sensorDataService;

    /*
    * 通过ID查询传感器的数据
    * */
    @RequestMapping(value = "/sensorDataByID/{sensorId}" , method = RequestMethod.GET)
    public Result findSensorDataById(@PathVariable String sensorId) throws Exception {
        // 获取传感器
        Sensor sensor = sensorService.getSensorOriginalData(sensorId);
        // 获得传感器对应的原始数据
        Set<OriginalData> originalDataSet = sensor.getOriginalDatas();
        Iterator<OriginalData> iterator = originalDataSet.iterator();
        // 获得最新的一份原始数据
        OriginalData originalData = null;
        while (iterator.hasNext()){
           originalData = iterator.next();
        }
        // 或得传感器的数据
        List<SensorDataResult> sensorOriginalData = sensorDataService.getSensorDataBySensorID(originalData.getOriginalDataPath(), sensor.getSensorName(), 18);

        return new  Result(ResultCode.SUCCESS,sensorOriginalData);
    }

    /*
     * 通过日期获得传感器的数据
     * */
    @RequestMapping(value = "/sensorDataByTime/{sensorId}" , method = RequestMethod.GET)
    public Result findSensorDataByTime(@PathVariable String sensorId, @RequestBody Map<String,String > map) throws Exception {
        // 获得时间
        String time = map.get("targetTime");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date targetTime = formatter.parse(time);
        // 获取传感器
        Sensor sensor = sensorService.getSensorOriginalData(sensorId);
        // 获得传感器对应的原始数据
        Set<OriginalData> originalDataSet = sensor.getOriginalDatas();
        Iterator<OriginalData> iterator = originalDataSet.iterator();
        // 获得最新的一份原始数据
        OriginalData originalData = null;
        while (iterator.hasNext()){
            originalData = iterator.next();
        }
        // 或得传感器的数据
        List<SensorDataResult> sensorOriginalData = sensorDataService.getSensorDataBySensorTime(originalData.getOriginalDataPath(), sensor.getSensorName(),targetTime);
        return new  Result(ResultCode.SUCCESS,sensorOriginalData);
    }
}
