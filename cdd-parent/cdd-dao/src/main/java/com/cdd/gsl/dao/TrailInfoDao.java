package com.cdd.gsl.dao;

import com.cdd.gsl.vo.CountCoustomerTrail;
import com.cdd.gsl.vo.CountHouseTrail;
import com.cdd.gsl.vo.TrailInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TrailInfoDao {
    @Select("select t.id as trailId,u.username as username,t.trail_info as trailInfo," +
            "t.create_ts as createTs,t.house_id as houseId " +
            " from t_user_info u left join t_trail_info t on u.id=t.user_id " +
             "where t.house_id=#{houseId} order by t.create_ts desc")
    List<TrailInfoVo> findTrailList(Long houseId);

    @Select("<script>" +
            "select t.user_id as userId,count(t.id) countHouseTrail " +
            "t.create_ts as createTs,t.house_id as houseId " +
            " from t_trail_info t left join t_house_info h on t.house_id=h.id " +
            "where h.house_use_type in (3,4) and h.`status`=1 "+
            "<if test='from != null and from != \"\"'>"+
            " and t.create_ts <![CDATA[>= ]]> #{from} "+
            "</if>"+
            "<if test='to != null and to != \"\"'>"+
            " and t.create_ts <![CDATA[<= ]]> #{to} "+
            "</if>"+
            "<if test='userIdList != null'>" +
            " and user_id in " +
            "<foreach collection='userIdList' item='userId' open='(' close=')' separator=',' >" +
            "#{userId}" +
            " </foreach>" +
            "</if> group by t.user_id" +
            "<if test='orderParam != null and orderParam != \"\"'>"+
            " order by countHouseTrail desc"+
            "</if>"+
            "</script>")
    List<CountHouseTrail> countHouseTrailByUserId(@Param("userIdList") List<Long> userIdList, @Param("orderParam") String orderParam, @Param("from")String from, @Param("to")String to);

    @Select("<script>" +
            "select t.user_id as userId,count(t.id) countHouseTrail " +
            " from t_trail_info t left join t_house_info h on t.house_id=h.id " +
            "where h.house_use_type in (1,2) and h.`status`=1 "+
            "<if test='from != null and from != \"\"'>"+
            " and t.create_ts <![CDATA[>= ]]> #{from} "+
            "</if>"+
            "<if test='to != null and to != \"\"'>"+
            " and t.create_ts <![CDATA[<= ]]> #{to} "+
            "</if>"+
            "<if test='userIdList != null'>" +
            " and t.user_id in " +
            "<foreach collection='userIdList' item='userId' open='(' close=')' separator=',' >" +
            "#{userId}" +
            " </foreach>" +
            "</if> group by t.user_id" +
            "<if test='orderParam != null and orderParam != \"\"'>"+
            " order by countHouseTrail desc"+
            "</if>"+
            "</script>")
    List<CountCoustomerTrail> countCoustomerTrailByUserId(@Param("userIdList") List<Long> userIdList, @Param("orderParam") String orderParam, @Param("from")String from, @Param("to")String to);

}
