package com.hardworkgroup.bridge_health_system.data_analysis.controller;

import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.entity.OriginalData;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorCorrelationDataResult;
import com.hardworkgroup.bridge_health_system.data_analysis.util.ConnectionPython;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import com.hardworkgroup.bridge_health_system.system_common.utils.ReadExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

//解决跨域
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/dataAnalysis")
public class PredictController {
    @Autowired
    private SensorService sensorService;

    /*
    获得短期预测数据
     */
    @RequestMapping(value = "/getShortTimePredict/{sensorId}" , method = RequestMethod.POST)
    public Result getShortTimePredict(@PathVariable String sensorId, @RequestBody Map<String,String > map) throws Exception {
        // 获得预测时长
        int time = Integer.parseInt(map.get("predictTime"));
        int predictTime = time / 5;

        // 获得传感器
        Sensor sensor = sensorService.getSensorOriginalData(sensorId);
        String sensorName = sensor.getSensorName();

        // 获得传感器所在字表名
        String str = sensorName.substring(0,2);
        String sheetName = str+"监测";

        // 获得传感器所在列对应的索引
        // 获得传感器对应的原始数据
        Set<OriginalData> originalDataSet = sensor.getOriginalDatas();
        Iterator<OriginalData> iterator = originalDataSet.iterator();
        // 获得最新的一份原始数据
        OriginalData originalData = null;
        while (iterator.hasNext()){
            originalData = iterator.next();
        }
        // 获得传感器所在列号
        Sheet excelSheet = ReadExcelUtil.getExcelSheet(originalData.getOriginalDataPath());
        int columnIndex = ReadExcelUtil.getSensorColumnIndex(excelSheet, sensorName);

        // 因为到了预处理后的数据都会把两列合并成一列
        if (columnIndex % 2 == 1){
            columnIndex = columnIndex - 1;
        }

        // 获取最后一个时间
        Row row = excelSheet.getRow(excelSheet.getLastRowNum());
        String time1 = row.getCell(0).getStringCellValue();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sensorDataTime1 = formatter.parse(time1);

        // 将数据进行编码转换
        String sheetName2 = java.net.URLEncoder.encode(sheetName,"utf-8");
        String sensorName2 = java.net.URLEncoder.encode(sensorName,"utf-8");

        // 从Python获得短期预测数据
        String shortTimePrediction = ConnectionPython.connectionPython("ShortTermForcastMain/?sheetName="+sheetName2+"&columnIndex="+columnIndex+"&predictTime="+predictTime+"&sensorName="+sensorName2);
        String[] predictionSplit = shortTimePrediction.split(",");
        List<SensorCorrelationDataResult> sensorDataResultList = new ArrayList<>();
        for (int i = 0; i < predictionSplit.length; i++){
            // 时间加5分钟
            Long tmp = sensorDataTime1.getTime()+300000;
            String format = formatter.format(tmp);
            Date sensorDataTime = formatter.parse(format);
            // 获得预测数据
            float sensorData = Float.parseFloat(predictionSplit[i]);
            // 保存入list中
            sensorDataResultList.add(new SensorCorrelationDataResult(sensorDataTime, sensorData));

            sensorDataTime1 = sensorDataTime;
        }

        return new  Result(ResultCode.SUCCESS,sensorDataResultList);
    }
}
