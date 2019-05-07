package com.cdd.gsl.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegionCountyInfoDao {

    @Select("select county_name as countyName from t_region_city_info city " +
            "left join t_region_county_info county on city.city_id=county.city_id " +
            "where city.city_name=#{city}")
    public List<String> findCountyNameByCityName(String city);
}
