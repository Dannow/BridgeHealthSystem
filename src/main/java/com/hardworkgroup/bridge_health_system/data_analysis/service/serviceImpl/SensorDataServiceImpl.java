package com.hardworkgroup.bridge_health_system.data_analysis.service.serviceImpl;

import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorCorrelationDataResult;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorMonitorDataResult;
import com.hardworkgroup.bridge_health_system.data_analysis.service.SensorDataService;
import com.hardworkgroup.bridge_health_system.system_common.utils.ReadExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SensorDataServiceImpl implements SensorDataService {

    /*
    根据ID获取传感器数据
     */
    @Override
    public List<SensorCorrelationDataResult> getSensorDataBySensorID(String fileName, String sensorName, int dataNumber) throws Exception{
        // 读取excel数据
        List<SensorCorrelationDataResult> sensorCorrelationDataResultList = readExcelDataByNumber(fileName, sensorName, dataNumber);
        return sensorCorrelationDataResultList;

    }

    /*
    根据日期获得某个传感器数据
     */
    public List<SensorCorrelationDataResult> getSensorDataBySensorTime(String fileName, String sensorName, Date targetTime) throws Exception {
        // 读取excel数据
        List<SensorCorrelationDataResult> sensorCorrelationDataResultList = readExcelDataByTime(fileName, sensorName, targetTime);
        return sensorCorrelationDataResultList;
    }

    /*
    通过传感器类型获得传感器数据
     */
    public List<SensorMonitorDataResult> getSensorDataInSensorType(String fileName, List<String> sensorName, int dataNumber) throws Exception {
        List<SensorMonitorDataResult> sensorMonitorDataResultList = readExcelDataInSensorType(fileName, sensorName, dataNumber);
        return sensorMonitorDataResultList;
    }

    // 通过传感器类型和日期获得传感器数据
    public List<SensorMonitorDataResult> getSensorDataInSensorTypeByTime(String fileName, List<String> sensorName, Date targetTime) throws Exception{
        List<SensorMonitorDataResult> sensorMonitorDataResultList = readExcelDataInSensorTypeInTime(fileName, sensorName, targetTime);
        return sensorMonitorDataResultList;
    }

    /*
    通过数量来读取数据
     */
    public List<SensorCorrelationDataResult> readExcelDataByNumber(String fileName, String sensorName, int dataNumber) throws Exception {
        // 获得子表
        Sheet xssfSheet = ReadExcelUtil.getExcelSheet(fileName);

        List<SensorCorrelationDataResult> sensorCorrelationDataResultList = new ArrayList<>();
        // 获得表的总行数
        int sheetRowNum = xssfSheet.getLastRowNum();
        // 获得传感器所在的列索引
        int sensorColumnIndex = ReadExcelUtil.getSensorColumnIndex(xssfSheet, sensorName);
        // 遍历每行读取数据
        for(int rowNum = sheetRowNum - dataNumber+1; rowNum <= sheetRowNum; rowNum++){
            Row row = xssfSheet.getRow(rowNum);
            // 读取时间
            String time = row.getCell(0).getStringCellValue();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date sensorDataTime = formatter.parse(time);
            // 读取传感器数据
            float sensorData = (float) row.getCell(sensorColumnIndex).getNumericCellValue();
            // 封装到Result添加到列表中
            sensorCorrelationDataResultList.add(new SensorCorrelationDataResult(sensorDataTime,sensorData));
        }
        return sensorCorrelationDataResultList;
    }

    /*
    通过时间来读取时数据
     */
    public List<SensorCorrelationDataResult> readExcelDataByTime(String fileName, String sensorName, Date targetTime) throws Exception {
        // 获得子表
        Sheet xssfSheet = ReadExcelUtil.getExcelSheet(fileName);

        List<SensorCorrelationDataResult> sensorCorrelationDataResultList = new ArrayList<>();
        // 获得表的总行数
        int sheetRowNum = xssfSheet.getLastRowNum();
        // 获得传感器所在的列索引
        int sensorColumnIndex = ReadExcelUtil.getSensorColumnIndex(xssfSheet, sensorName);
        // 遍历每行读取数据
        for(int rowNum = 1; rowNum <= sheetRowNum; rowNum++){
            Row row = xssfSheet.getRow(rowNum);
            // 读取时间
            String time = row.getCell(0).getStringCellValue();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date sensorDataTime = formatter.parse(time);
            // 查找大于目标时间的传感器数据
            if (targetTime.before(sensorDataTime) || targetTime.equals(sensorDataTime)){
                // 读取传感器数据
                float sensorData = (float) row.getCell(sensorColumnIndex).getNumericCellValue();
                // 封装到Result添加到列表中
                sensorCorrelationDataResultList.add(new SensorCorrelationDataResult(sensorDataTime,sensorData));
            }
        }
        return sensorCorrelationDataResultList;
    }


    /*
    通过传感器类型获得数据
     */
    public List<SensorMonitorDataResult> readExcelDataInSensorType(String fileName, List<String> sensorName, int dataNumber) throws Exception {
        // 获得子表
        Sheet xssfSheet = ReadExcelUtil.getExcelSheet(fileName);

        List<SensorMonitorDataResult> sensorMonitorDataResult = new ArrayList<>();
        // 获得表的总行数
        int sheetRowNum = xssfSheet.getLastRowNum();

        // 获得传感器所在的列索引
        List<Integer> sensorColumnIndexList = new ArrayList<>();
        for (int i = 0; i < sensorName.size(); i++){
            int sensorColumnIndex = ReadExcelUtil.getSensorColumnIndex(xssfSheet, sensorName.get(i));
            sensorColumnIndexList.add(sensorColumnIndex);
        }

        for(int rowNum = sheetRowNum - dataNumber+1; rowNum <= sheetRowNum; rowNum++){
            Row row = xssfSheet.getRow(rowNum);
            // 读取时间
            String time = row.getCell(0).getStringCellValue();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date sensorDataTime = formatter.parse(time);

            // 同一类型传感器数据
//            Map<String, Float> sensorDataMap = new LinkedHashMap<>();
            List<Float> sensorDataList = new ArrayList<>();
            for (int j = 0; j < sensorColumnIndexList.size(); j++){
                // 读取传感器数据
                float sensorData = (float) row.getCell(sensorColumnIndexList.get(j)).getNumericCellValue();
                sensorDataList.add(sensorData);
//                sensorDataMap.put(sensorName.get(j), sensorData);
            }
//            List<Map<String, Float>> sensorDataList = new ArrayList<>();
//            sensorDataList.add(sensorDataMap);
            // 封装到SensorMonitorDataResult
            sensorMonitorDataResult.add(new SensorMonitorDataResult(sensorDataTime,sensorDataList));
        }

        return sensorMonitorDataResult;
    }

    /*
    通过传感器类型和时间获得数据
     */
    public List<SensorMonitorDataResult> readExcelDataInSensorTypeInTime(String fileName, List<String> sensorName, Date targetTime) throws Exception {
        // 获得子表
        Sheet xssfSheet = ReadExcelUtil.getExcelSheet(fileName);

        List<SensorMonitorDataResult> sensorMonitorDataResult = new ArrayList<>();
        // 获得表的总行数
        int sheetRowNum = xssfSheet.getLastRowNum();

        // 获得传感器所在的列索引
        List<Integer> sensorColumnIndexList = new ArrayList<>();
        for (int i = 0; i < sensorName.size(); i++) {
            int sensorColumnIndex = ReadExcelUtil.getSensorColumnIndex(xssfSheet, sensorName.get(i));
            sensorColumnIndexList.add(sensorColumnIndex);
        }

        for (int rowNum = 1; rowNum <= sheetRowNum; rowNum++) {
            Row row = xssfSheet.getRow(rowNum);
            // 读取时间
            String time = row.getCell(0).getStringCellValue();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date sensorDataTime = formatter.parse(time);
            // 查找大于目标时间的传感器数据
            if (targetTime.before(sensorDataTime) || targetTime.equals(sensorDataTime)){
                // 同一类型传感器数据
//                Map<String, Float> sensorDataMap = new HashMap<>();
                List<Float> sensorDataList = new ArrayList<>();
                // 遍历所有同一类型的传感器
                for (int j = 0; j < sensorColumnIndexList.size(); j++){
                    // 读取传感器数据
                    float sensorData = (float) row.getCell(sensorColumnIndexList.get(j)).getNumericCellValue();
                    sensorDataList.add(sensorData);
//                    sensorDataMap.put(sensorName.get(j), sensorData);
                }
//                List<Map<String, Float>> sensorDataList = new ArrayList<>();
//                sensorDataList.add(sensorDataMap);
                // 封装到SensorMonitorDataResult
                sensorMonitorDataResult.add(new SensorMonitorDataResult(sensorDataTime,sensorDataList));
            }
        }
        return sensorMonitorDataResult;
    }
}
