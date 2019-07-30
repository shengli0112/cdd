package com.cdd.gsl.dao;

import com.cdd.gsl.domain.TopInfoDomain;
import com.cdd.gsl.vo.TopInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TopInfoDao {

    @Select("select id as id,integral as integral,day as day from t_top_info order by integral asc")
    List<TopInfoVo> selectTopInfoList();
}
