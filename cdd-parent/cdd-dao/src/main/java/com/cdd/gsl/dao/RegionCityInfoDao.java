package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RegionCityInfoDomain;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegionCityInfoDao {
    @Select("select city_name as cityName from t_region_city_info")
    List<String> selectCityName();

    @Select("select * from " +
            "((select city_name as name from t_region_city_info where city_name like concat('%','${cityName}','%'))" +
            " union all " +
            " (select county_name as name from t_region_county_info where county_name like concat('%','${cityName}','%')))" +
            " tmp")
    List<String> selectCityByCityName(String cityName);
}
