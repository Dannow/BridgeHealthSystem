package com.hardworkgroup.bridge_health_system.system_common.utils;

import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorCorrelationDataResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadExcelUtil {

    /*
    获得传感器所在的列索引
     */
    public static int getSensorColumnIndex(Sheet xssfSheet, String sensorName){
        // 传感器所在列的索引号
        int sensorColumnIndex = 0;
        // 获得传感器所在列（截取传感器名的倒数两位）
        String sensorColumnName = sensorName.substring(sensorName.length() -2,sensorName.length());
        // 获得总列数
        int rowCells = xssfSheet.getRow(0).getPhysicalNumberOfCells();
        // 找出传感器名所在列的索引号
        for (int i = 0; i < rowCells; i++){
            Row hssfRow = xssfSheet.getRow(0);
            String columnName = hssfRow.getCell(i).getStringCellValue();
            if (columnName.equals(sensorColumnName)){
                sensorColumnIndex = i;
                break;
            }
        }
        return sensorColumnIndex;
    }

    /*
    读取Excel数据
     */
    public static Sheet getExcelSheet(String fileName) throws Exception {
        // 获得输入流
//        InputStream is = new FileInputStream(new File("E://学习//服务外包大赛//桥梁管理系统//拆分Excel表//"+fileName));
        InputStream is = new FileInputStream(new File("/home/dong/doucments/BridgeHealthSystem/bridge_health_system_ExcelData/splitOriginalExcelData/"+fileName));
        // 不同的后缀名获得不同的Workbook对象
        Workbook xssfWorkbook = null;
        if (fileName.endsWith("xlsx")){
            xssfWorkbook = new XSSFWorkbook(is);
        }else if (fileName.endsWith("xls")){
            xssfWorkbook = new HSSFWorkbook(is);
        }

        // 获得子表
        Sheet xssfSheet = xssfWorkbook.getSheetAt(0);

        return xssfSheet;
    }

//    /*
//    通过数量来读取数据
//     */
//    public static List<SensorCorrelationDataResult> readExcelDataByNumber(String fileName, String sensorName, int dataNumber) throws Exception {
//        // 获得子表
//        Sheet xssfSheet = getExcelSheet(fileName);
//
//        List<SensorCorrelationDataResult> sensorDataResultList = new ArrayList<>();
//        // 获得表的总行数
//        int sheetRowNum = xssfSheet.getLastRowNum();
//        // 获得传感器所在的列索引
//        int sensorColumnIndex = getSensorColumnIndex(xssfSheet, sensorName);
//        // 遍历每行读取数据
//        for(int rowNum = sheetRowNum - dataNumber+1; rowNum <= sheetRowNum; rowNum++){
//            Row row = xssfSheet.getRow(rowNum);
//            // 读取时间
//            String time = row.getCell(0).getStringCellValue();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date sensorDataTime = formatter.parse(time);
//            // 读取传感器数据
//            float sensorData = (float) row.getCell(sensorColumnIndex).getNumericCellValue();
//            // 封装到Result添加到列表中
//            sensorDataResultList.add(new SensorCorrelationDataResult(sensorDataTime,sensorData));
//        }
//        return sensorDataResultList;
//    }
//
//    /*
//    通过时间来读取时数据
//     */
//    public static List<SensorCorrelationDataResult> readExcelDataByTime(String fileName, String sensorName, Date targetTime) throws Exception {
//        // 获得子表
//        Sheet xssfSheet = getExcelSheet(fileName);
//
//        List<SensorCorrelationDataResult> sensorDataResultList = new ArrayList<>();
//        // 获得表的总行数
//        int sheetRowNum = xssfSheet.getLastRowNum();
//        // 获得传感器所在的列索引
//        int sensorColumnIndex = getSensorColumnIndex(xssfSheet, sensorName);
//        // 遍历每行读取数据
//        for(int rowNum = 1; rowNum <= sheetRowNum; rowNum++){
//            Row row = xssfSheet.getRow(rowNum);
//            // 读取时间
//            String time = row.getCell(0).getStringCellValue();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date sensorDataTime = formatter.parse(time);
//            // 查找大于目标时间的传感器数据
//            if (targetTime.before(sensorDataTime) || targetTime.equals(sensorDataTime)){
//                // 读取传感器数据
//                float sensorData = (float) row.getCell(sensorColumnIndex).getNumericCellValue();
//                // 封装到Result添加到列表中
//                sensorDataResultList.add(new SensorCorrelationDataResult(sensorDataTime,sensorData));
//            }
//        }
//        return sensorDataResultList;
//    }

}
