package com.hardworkgroup.bridge_health_system.bridge_inspection.service;


import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEventPicture;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleEventPicture;

import java.util.List;

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

    List<SimpleEventPicture> findAllByEventID(Integer problemEventID);

    void save(ProblemEventPicture problemEventPicture);

    void update(String id, ProblemEventPicture problemEventPicture);

    void delete(String id);
}