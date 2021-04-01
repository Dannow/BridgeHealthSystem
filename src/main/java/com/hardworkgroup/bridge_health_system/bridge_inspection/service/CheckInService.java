package com.hardworkgroup.bridge_health_system.bridge_inspection.service;


import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.CheckIn;

/**
 * (CheckInService)表服务接口
 *
 * @author hui
 * @since 2021-03-26 18:11:58
 */
public interface CheckInService {

    CheckIn getCheckInByID(String id);

    PageInfo<CheckIn> findAll(int pageNum, int pageSize);

    PageInfo<CheckIn> findAllByUserID(Integer userID, int pageNum, int pageSize);

    void save(CheckIn checkIn);

    void delete(String checkInID);

    void update(CheckIn checkIn);
}