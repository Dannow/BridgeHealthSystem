package com.hardworkgroup.bridge_health_system.bridge_inspection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEventPicture;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (TPatrolRecord)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
@Repository
public interface ProblemEventPictureDao extends Mapper<ProblemEvent> {


    List<ProblemEventPicture> selectAllProblemEventPicture();

    List<ProblemEventPicture> selectAllByEventID(Integer problemEventID);

    void insertByKey(ProblemEventPicture problemEventPicture);

    ProblemEventPicture getProblemEventPictureByID(String id);

    void deleteByKey(String problemEventPictureID);

    void updateByKey(ProblemEventPicture problemEventPicture);
}