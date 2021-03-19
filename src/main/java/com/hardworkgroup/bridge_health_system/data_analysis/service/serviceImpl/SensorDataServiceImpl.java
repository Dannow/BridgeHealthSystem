package com.hardworkgroup.bridge_health_system.data_analysis.service.serviceImpl;

import com.hardworkgroup.bridge_health_system.data_analysis.service.SensorDataService;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorDataResult;
import com.hardworkgroup.bridge_health_system.system_common.utils.ReadExcelUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SensorDataServiceImpl implements SensorDataService {

    /*
    根据ID获取传感器数据
     */
    @Override
    public List<SensorDataResult> getSensorDataBySensorID(String fileName, String sensorName, int dataNumber) throws Exception{
        // 读取excel数据
        List<SensorDataResult> sensorDataResultList = ReadExcelUtil.readExcelDataByNumber(fileName, sensorName, dataNumber);
        return sensorDataResultList;

    }

    /*
    根据日期获得某个传感器数据
     */
    public List<SensorDataResult> getSensorDataBySensorTime(String fileName, String sensorName, Date targetTime) throws Exception {
        // 读取excel数据
        List<SensorDataResult> sensorDataResultList = ReadExcelUtil.readExcelDataByTime(fileName, sensorName, targetTime);
        return sensorDataResultList;
    }
}
