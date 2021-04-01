package com.hardworkgroup.bridge_health_system.bridge_inspection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.CheckIn;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (TPatrolRecord)表数据库访问层
 *
 * @author hui
 * @since 2021-03-26 18:08:33
 */
@Repository
public interface CheckInDao extends Mapper<CheckIn> {

    List<CheckIn> selectAllByUserID(Integer userID);

    List<CheckIn> selectAllCheckIn();

    void insertByKey(CheckIn checkIn);

    CheckIn getCheckInByID(String checkInID);

    void deleteByKey(String checkInID);

    void updateByKey(CheckIn checkIn);
}