package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RegionCityInfoDomain;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegionCityInfoDao {
    @Select("select city_name as cityName from t_region_city_info")
    List<String> selectCityName();
}
