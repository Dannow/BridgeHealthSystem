package com.hardworkgroup.bridge_health_system.data_analysis.controller;

import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.entity.OriginalData;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorCorrelationDataResult;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataFire;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataSmoke;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataTemperature;
import com.hardworkgroup.bridge_health_system.data_analysis.service.SensorDataService;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataFireService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataSmokeService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataTemperatureService;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/dataAnalysis")
public class SensorCorrelationAnalysisController {
    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorDataService sensorDataService;
    @Autowired
    private RawDataTemperatureService rawDataTemperatureService;
    @Autowired
    private RawDataSmokeService rawDataSmokeService;
    @Autowired
    private RawDataFireService rawDataFireService;

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
        List<SensorCorrelationDataResult> sensorOriginalData = getRawData(sensorId,"number",originalData,sensor);

        return new  Result(ResultCode.SUCCESS,sensorOriginalData);
    }

    /*
     * 通过日期获得传感器的数据
     * */
    @RequestMapping(value = "/sensorDataByTime/{sensorId}" , method = RequestMethod.POST)
    public Result findSensorDataByTime(@PathVariable String sensorId, @RequestBody Map<String,String > map) throws Exception {
        // 获得时间
        String time = map.get("targetTime");
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
        List<SensorCorrelationDataResult> sensorOriginalData = getRawData(sensorId,"time",originalData,sensor,time);
        return new  Result(ResultCode.SUCCESS,sensorOriginalData);
    }


    // 获取原始数据
    public List<SensorCorrelationDataResult> getRawData(String sensorId, String type, OriginalData originalData, Sensor sensor, String... time) throws Exception {
        List<SensorCorrelationDataResult> sensorOriginalData = new ArrayList<>();
        // 温度传感器
        if (sensorId.equals("1")){
            List<RawDataTemperature> rawDataTemperatures = null;
            if (type.equals("time")){
                rawDataTemperatures = rawDataTemperatureService.selectRawDataTemperatureBySensorIDAndTime(Integer.parseInt(sensorId), time[0]);
            }else {
                rawDataTemperatures = rawDataTemperatureService.selectBatchRawDataTemperatureBySensorID(Integer.parseInt(sensorId), 18);
            }
            for (int i = rawDataTemperatures.size()-1; i >=0; i--){
                RawDataTemperature rawDataTemperature = rawDataTemperatures.get(i);
                sensorOriginalData.add(new SensorCorrelationDataResult(rawDataTemperature.getAcquisitionTime(), rawDataTemperature.getTemperatureValue()));
            }
            // 火焰传感器
        }else if (sensorId.equals("19")){
            List<RawDataFire> rawDataFires = null;
            if (type.equals("time")){
                rawDataFires = rawDataFireService.selectRawDataFireBySensorIDAndTime(Integer.parseInt(sensorId), time[0]);
            }else {
                rawDataFires = rawDataFireService.selectBatchRawDataFireBySensorID(Integer.parseInt(sensorId), 18);
            }

            for (int i = rawDataFires.size()-1; i >=0; i--){
                RawDataFire rawDataFire = rawDataFires.get(i);
                sensorOriginalData.add(new SensorCorrelationDataResult(rawDataFire.getAcquisitionTime(), rawDataFire.getFireValue()));
            }
            // 烟雾传感器
        }else if (sensorId.equals("18")){
            List<RawDataSmoke> rawDataSmokes = null;
            if (type.equals("time")){
                rawDataSmokes = rawDataSmokeService.selectRawDataSmokeBySensorIDAndTime(Integer.parseInt(sensorId), time[0]);
            }else {
                rawDataSmokes = rawDataSmokeService.selectBatchRawDataSmokeBySensorID(Integer.parseInt(sensorId), 18);
            }

            for (int i = rawDataSmokes.size()-1; i >=0; i--){
                RawDataSmoke rawDataSmoke = rawDataSmokes.get(i);
                sensorOriginalData.add(new SensorCorrelationDataResult(rawDataSmoke.getAcquisitionTime(), rawDataSmoke.getSmokeValue()));
            }
            // 没有实时数据的传感器依旧按照读Excel方式读取
        }else {
            if (type.equals("time")){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date targetTime = formatter.parse(time[0]);
                sensorOriginalData = sensorDataService.getSensorDataBySensorTime(originalData.getOriginalDataPath(), sensor.getSensorName(),targetTime);
            }else {
                sensorOriginalData = sensorDataService.getSensorDataBySensorID(originalData.getOriginalDataPath(), sensor.getSensorName(), 18);
            }
        }
        return sensorOriginalData;
    }
}
