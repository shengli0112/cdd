package com.cdd.gsl.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SearchCityUserDao {
    @Select("select city_name from t_search_city_user_info where 1=1 and user_id=#{userId} and status=1 order by create_ts desc limit 6")
    List<String> selectCityByUserId(Long userId);
}
