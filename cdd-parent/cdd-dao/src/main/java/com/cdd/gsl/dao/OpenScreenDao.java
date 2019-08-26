package com.cdd.gsl.dao;

import com.cdd.gsl.admin.ScreenAdminConditionVo;
import com.cdd.gsl.domain.OpenScreenInfoDomain;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OpenScreenDao {
    @Select("<script>" +
            "select id as id,title as title,screen_url as screenUrl," +
            "status as status,redirect_url as redirectUrl,create_ts as createTs,update_ts as updateTs" +
            " from t_slide_info where 1=1 " +
            "<if test='keyword != null'>" +
            " and  (city like concat('%','${keyword}','%') or title like concat('%','${keyword}','%'))" +
            "</if> order by update_ts desc" +
            " limit #{from},#{limit}"+
            "</script>")
    List<OpenScreenInfoDomain> openScreenList(ScreenAdminConditionVo screenAdminConditionVo);

    @Select("<script>" +
            "select count(*) " +
            " from t_slide_info where 1=1 " +
            "<if test='keyword != null'>" +
            " and  (city like concat('%','${keyword}','%') or title like concat('%','${keyword}','%'))" +
            "</if>" +
            "</script>")
    int countOpenScreen(ScreenAdminConditionVo screenAdminConditionVo);
}
