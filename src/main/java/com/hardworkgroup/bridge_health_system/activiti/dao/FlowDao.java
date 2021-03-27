package com.hardworkgroup.bridge_health_system.activiti.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity.FlowInfo;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FlowDao extends Mapper<FlowInfo> {

    @Select("select * from tb_flow order by createtime desc")
    List<FlowInfo> selectFlowList();

    @Update("update tb_flow set state = 0 where id=#{id}")
    int updateFlowDeployState(String id);

    @Select("select * from tb_flow where id=#{id}")
    FlowInfo selectOneFlow(String id);
}
