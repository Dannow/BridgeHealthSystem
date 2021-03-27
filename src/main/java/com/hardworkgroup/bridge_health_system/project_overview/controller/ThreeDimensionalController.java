package com.hardworkgroup.bridge_health_system.project_overview.controller;

import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.project_overview.response.SingleSensorDataResult;
import com.hardworkgroup.bridge_health_system.data_analysis.service.SensorDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/projectOverview")
public class ThreeDimensionalController {
    @Autowired
    private SensorService sensorService;

    /*
    获得单个传感器ID
     */
    @RequestMapping(value = "/getSensorData/{sensorId}")
    public SingleSensorDataResult getSensorData(@PathVariable String sensorId,int dataIndex){
        // 获得传感器
        Sensor sensor = sensorService.getSensorOriginalData(sensorId);
        String sensorName = sensor.getSensorName();
        String sensorLocation = sensor.getSensorLocation();
        String sensorType = sensor.getSensorType();
        int upperThreshold = sensor.getUpperThreshold();
        int lowerThreshold = sensor.getLowerThreshold();
        

        return null;
    }
}
