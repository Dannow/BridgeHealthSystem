package com.hardworkgroup.bridge_health_system;

import com.hardworkgroup.bridge_health_system.alarm_management.service.serviceImpl.AlarmDataServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.BridgeService;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import com.hardworkgroup.bridge_health_system.data_analysis.service.SensorDataService;
import com.hardworkgroup.bridge_health_system.permission_management.service.UserService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao.RawDataTemperatureDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BridgeHealthSystemApplicationTests {
    @Autowired
    public SensorService sensorService;
    @Autowired
    public UserService userService;
    @Autowired
    public SensorDataService sensorDataService;
    @Autowired
    public BridgeService bridgeService;
    @Autowired
    public RawDataTemperatureDao rawDataTemperatureDao;
    @Autowired
    public AlarmDataServiceImpl alarmDataService;

    @Test
    public void test() throws Exception {
//        AlarmInformation alarmInformation = new AlarmInformation();
//        alarmInformation.setAlarmDetail("aa");
//        alarmDataService.save(alarmInformation);
        System.out.println(userService.getUserByID("1").getBridge().getBridgeName());
//        System.out.println(rawDataTemperatureDao.selectRawDataTemperatureBySensorIDAndTime(1,"2020-04-27"));
//        // 获得当前时间
//        Date nowTime = new Date();
//
//        // 得到指定的日期
//        Calendar c = Calendar.getInstance();
//        c.setTime(nowTime);
//        c.add(Calendar.DAY_OF_MONTH, -2);
//        Date targetTime = c.getTime();
//        System.out.println(targetTime);
//        System.out.println(bridgeService.getSensorByBridgeID("1"));
//        System.out.println(sensorDataService.getSensorDataBySensorID("长期温度监测表.xlsx","温度传感器A1",18));
//        String sensorName = "温度a传感器A1";
//        String str = sensorName.substring(sensorName.length()-2,sensorName.length());
//        System.out.println(str);
//        System.out.println(sensorService.getAlarmInformationBySensorID(1));
//        OriginalData data = new OriginalData();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date sensorDataTime = formatter.parse("2020-04-28");
//////        data.setOriginalDataCollectionTime(sensorDataTime);
////////        Date date = new Date(System.currentTimeMillis());
//////        System.out.println(formatter.format(data.getOriginalDataCollectionTime()));
////        System.out.println(sensorDataService.getSensorDataBySensorTime("长期温度监测表.xlsx","温度传感器A1", sensorDataTime));
//        System.out.println(sensorDataService.getSensorDataBySensorID("长期温度监测表.xlsx","温度传感器A1",18));
//        String fileName = "长期温度监测表.xlsx";
//        // 获得输入流
//        InputStream is = new FileInputStream(new File("E://学习//服务外包大赛//桥梁管理系统//拆分Excel表//"+fileName));
//
//        // 不同的后缀名获得不同的Workbook对象
//        Workbook xssfWorkbook = null;
//        if (fileName.endsWith("xlsx")){
//            xssfWorkbook = new XSSFWorkbook(is);
//        }else if (fileName.endsWith("xls")){
//            xssfWorkbook = new HSSFWorkbook(is);
//        }
//
//        // 获得传感器所在列（截取传感器名的倒数两位）
//        String sensorColumnName = "A1";
//        // 获得子表
//        Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
//        // 传感器所在列的索引号
//        int sensorColumnIndex = 0;
//        // 获得总列数
//        int rowCells = xssfSheet.getRow(0).getPhysicalNumberOfCells();
//        // 找出传感器名所在列的索引号
//        for (int i = 0; i < rowCells; i++){
//            Row hssfRow = xssfSheet.getRow(0);
//            String columnName = hssfRow.getCell(i).getStringCellValue();
//            if (columnName.equals(sensorColumnName)){
//                sensorColumnIndex = i;
//                break;
//            }
//        }
//
//        List<SensorCorrelationDataResult> sensorDataResultList = new ArrayList<>();
//        // 遍历每行读取数据
//        for(int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++){
//            Row row = xssfSheet.getRow(rowNum);
//            // 读取时间
//            String sensorDataTime1 = row.getCell(0).getStringCellValue();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date sensorDataTime = formatter.parse(sensorDataTime1);
//            // 读取传感器数据
//            float sensorData = (float) row.getCell(sensorColumnIndex).getNumericCellValue();
//            // 封装到Result添加到列表中
//            sensorDataResultList.add(new SensorCorrelationDataResult(sensorDataTime,sensorData));
//        }
//        System.out.println(sensorDataResultList);


    }
}
