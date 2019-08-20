package com.cdd.gsl.dao;

import com.cdd.gsl.admin.LeagueAdminConditionVo;
import com.cdd.gsl.domain.LeagueInfoDomain;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LeagueInfoDao {
    @Select("<script>" +
            "select id as id,city as city,name as name,phone as phone," +
            "create_ts as createTs,status as status" +
            " from t_league_info where 1=1 " +
            "<if test='keyword != null'>"+
            " and (city like concat('%','${keyword}','%') or name like concat('%','${keyword}','%') or phone like concat('%','${keyword}','%'))" +
            "</if>"+
            " limit #{from},#{limit}"+
            "</script>")
    List<LeagueInfoDomain> leagueInfoList(LeagueAdminConditionVo leagueAdminConditionVo);
}
