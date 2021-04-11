package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.jaxrs.FastJsonAutoDiscoverable;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.InspectionDataDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.ProblemEventDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.ProblemEventPictureDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.InspectionRecordService;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.InspectionRecordDao;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.response.AlarmInformationWithBridge;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.response.BridgeSimpleResult;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionData;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEventPicture;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleRecord;
import com.hardworkgroup.bridge_health_system.system_common.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * (TPatrolRecord)表服务实现类
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
@Slf4j
@Service
public class InspectionRecordServiceImpl implements InspectionRecordService {

    @Resource
    private InspectionRecordDao inspectionRecordDao;

    @Resource
    private InspectionDataDao inspectionDataDao;

    @Resource
    private ProblemEventDao problemEventDao;

    @Resource
    private ProblemEventPictureDao problemEventPictureDao;

    @Override
    public InspectionRecord selectInspectionRecord(int inspectionID) {
        return inspectionRecordDao.selectByPrimaryKey(inspectionID);
    }

    /**
     * 保存用户
     */
    @Override
    public void save(InspectionRecord inspectionRecord) {
        //调用dao保存用户
        inspectionRecordDao.insertByKey(inspectionRecord);
    }

    /**
     * 获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<InspectionRecord> findAll(int pageNum, int pageSize) {
        Page<InspectionRecord> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionRecord> inspectionRecords =  inspectionRecordDao.selectAllInspectionRecord();
        return new PageInfo<>(inspectionRecords,5);
    }

    /**
     * 根据planID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<InspectionRecord> findAllByPlanID(Integer inspectionPlanID, int pageNum, int pageSize) {
        Page<InspectionRecord> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionRecord> inspectionRecords =  inspectionRecordDao.selectAllByPlanID(inspectionPlanID);
        return new PageInfo<>(inspectionRecords,5);
    }

    @Override
    public PageInfo<InspectionRecord> findAllByBridgeID(Integer bridgeID, int pageNum, int pageSize) {
        Page<InspectionRecord> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionRecord> inspectionRecords =  inspectionRecordDao.selectAllByBridgeID(bridgeID);
        return new PageInfo<>(inspectionRecords,5);
    }

    @Override
    public List<SimpleRecord> findAllByBridgeID(Integer bridgeID) {
        List<InspectionRecord> inspectionRecords =  inspectionRecordDao.selectAllByBridgeID(bridgeID);
        List<SimpleRecord> simpleRecords = new ArrayList<>();
        for (InspectionRecord inspectionRecord : inspectionRecords) {
            simpleRecords.add(new SimpleRecord(inspectionRecord));
        }
        return simpleRecords;
    }

    @Override
    public List<SimpleRecord> findAllByUserID(Integer userId) {
        List<InspectionRecord> inspectionRecords = inspectionRecordDao.selectAllByUserID(userId);
        List<SimpleRecord> simpleRecords = new ArrayList<>();
        for (InspectionRecord inspectionRecord : inspectionRecords) {
            simpleRecords.add(new SimpleRecord(inspectionRecord));
        }
        return simpleRecords;
    }

    /**
     * 手机端插入巡检数据
     * @param inspectionRecord
     */
    @Override
    @Transactional
    public void insert(InspectionRecord inspectionRecord) {
        inspectionRecordDao.insertByKey(inspectionRecord);
        //解决@RequestBodyJSON转化时将List转化为LinkHashMap；
        String s = JSON.toJSONString(inspectionRecord.getInspectionData());
        List<InspectionData> inspectionData = JSONObject.parseArray(s, InspectionData.class);
        //添加巡检数据
        for (InspectionData inspectionDatum : inspectionData) {
            inspectionDatum.setInspectionRecordID(inspectionRecord.getInspectionRecordID());
            inspectionDataDao.insertByKey(inspectionDatum);
        }
        //解决@RequestBodyJSON转化时将List转化为LinkHashMap；
        s = JSON.toJSONString(inspectionRecord.getProblemEvents());
        List<ProblemEvent> problemEvents = JSONObject.parseArray(s, ProblemEvent.class);
        //添加问题事件
        for (ProblemEvent problemEvent : problemEvents) {
            problemEvent.setInspectionRecordID(inspectionRecord.getInspectionRecordID());
            problemEventDao.insertByKey(problemEvent);
            //添加问题事件图片
            for (ProblemEventPicture problemEventPicture : problemEvent.getProblemEventPictures()) {
                problemEventPicture.setProblemEventID(problemEvent.getProblemEventID());
                if(upload(problemEventPicture)){
                    log.info(problemEventPicture.getProblemPicture());
                    problemEventPictureDao.insertByKey(problemEventPicture);
                }
            }
        }
    }

    private Boolean upload(ProblemEventPicture problemEventPicture) {
        log.info("==上传图片==");
        log.info("==接收到的数据=="+ problemEventPicture.getProblemPicture());


        String dataPrix = ""; //base64格式前头
        String data = "";//实体部分数据
        if(problemEventPicture.getProblemPicture() ==null||"".equals(problemEventPicture.getProblemPicture())){
            //return AppResultBuilder.buildFailedResult("上传失败，上传图片数据为空","401");
            log.info("1");
            return false;
        }else {
            String [] d = problemEventPicture.getProblemPicture().split("base64,");//将字符串分成数组
            if(d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else {
                //return AppResultBuilder.buildFailedResult("上传失败，数据不合法","401");
                log.info("11");
                return false;
            }
        }
        String suffix = "";//图片后缀，用以识别哪种格式数据
        //data:image/jpeg;base64,base64编码的jpeg图片数据
        if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){
            suffix = ".jpg";
        }else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){
            //data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        }else if("data:image/gif;".equalsIgnoreCase(dataPrix)){
            //data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        }else if("data:image/png;".equalsIgnoreCase(dataPrix)){
            //data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else {
            //return AppResultBuilder.buildFailedResult("上传图片格式不合法","401");
            log.info("111");
            return false;
        }
        String uuid = UUIDUtils.getUUID();
        String tempFileName=uuid+suffix;
        String imgFilePath = "D:\\fwwb\\xxzl\\test\\"+tempFileName;//新生成的图片
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(data);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {
                    //调整异常数据
                    b[i]+=256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            //String imgurl="http://xxxxxxxx/"+tempFileName;
            problemEventPicture.setProblemPicture(tempFileName);
            log.info(problemEventPicture.getProblemPicture());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            //return AppResultBuilder.buildFailedResult("上传图片失败","401");
            log.info("1111");
            return false;
        }
    }

    @Override
    public InspectionRecord getRecordByID(String id) {
        return  inspectionRecordDao.getRecordByID(id);
    }

    @Override
    public void delete(String inspectionRecordID) {
        inspectionRecordDao.deleteByKey(inspectionRecordID);
    }

    @Override
    public void update(InspectionRecord inspectionRecord) {
        inspectionRecordDao.updateByKey(inspectionRecord);
    }
}