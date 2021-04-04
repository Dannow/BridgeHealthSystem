package com.hardworkgroup.bridge_health_system.bridge_inspection.service;


import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEventPicture;

/**
 * (TPatrolRecord)表服务接口
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
public interface ProblemEventPictureService {

    PageInfo<ProblemEventPicture> findAll(int pageNum, int pageSize);

    PageInfo<ProblemEventPicture> findAllByEventID(Integer problemEventID, int pageNum, int pageSize);

    ProblemEventPicture getProblemEventPictureByID(String id);

    void save(ProblemEventPicture problemEventPicture);

    void update(String id, ProblemEventPicture problemEventPicture);

    void delete(String id);
}