package com.cdd.gsl.dao;

import com.cdd.gsl.admin.UserBrokerConditionVo;
import com.cdd.gsl.admin.UserBrokerVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserBrokerInfoDao {
    @Select("<script>" +
            "SELECT id id,name name,phone phone,create_ts createTs,update_ts updateTs,city city,county county,town town " +
            "FROM  t_user_broker_info WHERE status=1 " +
            "<if test='keyword != null '>" +
            "AND (name like concat('%','${keyword}','%') OR phone like concat('%','${keyword}','%') OR city like concat('%','${keyword}','%')" +
            "OR county like concat('%','${keyword}','%') OR town like concat('%','${keyword}','%'))" +
            "</if>" +
            "ORDER BY create_ts DESC " +
            "LIMIT #{from},#{limit}" +
            "</script>")
    List<UserBrokerVo> getUserBrokerList(UserBrokerConditionVo userBrokerConditionVo);

    @Select("<script>" +
            "SELECT count(*) " +
            "FROM  t_user_broker_info WHERE status=1 " +
            "<if test='keyword != null '>" +
            "AND (name like concat('%','${keyword}','%') OR phone like concat('%','${keyword}','%') OR city like concat('%','${keyword}','%')" +
            "OR county like concat('%','${keyword}','%') OR town like concat('%','${keyword}','%'))" +
            "</if>" +
            "</script>")
    int countUserBroker(UserBrokerConditionVo userBrokerConditionVo);
}
