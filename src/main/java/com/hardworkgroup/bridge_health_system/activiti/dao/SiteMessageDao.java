package com.hardworkgroup.bridge_health_system.activiti.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity.SiteMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteMessageDao {

    @Select("select * from siteMessage where userID=#{userID}")
    List<SiteMessage> selectMsgList(Integer userID);

    @Select("select * from siteMessage where id=#{id} and userID=#{userID}")
    SiteMessage selectOneMsg(@Param("id") Integer id, @Param("userID") Integer userID);

    @Select("select * from siteMessage where isRead= 0 and userID=#{userID}")
    SiteMessage selectOne( @Param("userID") Integer userID);

    @Update("update siteMessage set isRead=1 where id=#{id} and userID=#{userID}")
    int updateMsgRead(@Param("id") Integer id, @Param("userID") Integer userID);

    @Insert("insert into siteMessage (userID,taskID,type,isRead) values (#{userID},#{taskID},#{type},0)")
    int insertMsg(@Param("userID") Integer userID, @Param("taskID") String taskID, @Param("type") Integer type);
}
