package com.cdd.gsl.dao;

import com.cdd.gsl.domain.SlideInfoDomain;
import com.cdd.gsl.vo.SlideConditionVo;
import com.cdd.gsl.vo.SlideParamVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SlideInfoDao {
    @Select("<script>" +
            "select id as id,title as title,slide_url as slideUrl,sequence as sequence,position as position,is_look as isLook," +
            "status as status,city as city,redirect_url as redirectUrl,create_ts as createTs,update_ts as updateTs" +
            " from t_slide_info where 1=1 " +
            "<if test='keyword != null'>" +
            " and  (city like concat('%','${keyword}','%') or title like concat('%','${keyword}','%'))" +
            "</if> order by update_ts desc" +
            " limit #{from},#{limit}"+
            "</script>")
    List<SlideInfoDomain> slideList(SlideConditionVo slideConditionVo);

    @Select("<script>" +
            "select id as id,title as title,slide_url as slideUrl,sequence as sequence," +
            "status as status,city as city,redirect_url as redirectUrl,create_ts as createTs," +
            "update_ts as updateTs,is_look as isLook,position as position" +
            " from t_slide_info where status=1 and position=#{position} " +
            "<if test='isLook == 0'>" +
            " and is_look=0 " +
            "</if> " +
            " and " +
            "<if test='city != null'>" +
            " ( " +
            "</if> " +
            "city='' " +
            "<if test='city != null'>" +
            " or city=#{city} )" +
            "</if> " +
            "order by sequence" +
            "</script>")
    List<SlideInfoDomain> findSlideListByCity(SlideParamVo slideParamVo);

    @Select("<script>" +
            "select id as id,title as title,slide_url as slideUrl,sequence as sequence" +
            " from t_slide_info where 1=1 and status=1  and city='全国' " +
            "<if test='city != null'>" +
            " or city=#{city}" +
            "</if>" +
            "</script>")
    List<SlideInfoDomain> slideInfoList(String city);

    @Select("<script>" +
            "select count(*)" +
            " from t_slide_info where 1=1 " +
            "</script>")
    int slideCount(SlideConditionVo slideConditionVo);
}
