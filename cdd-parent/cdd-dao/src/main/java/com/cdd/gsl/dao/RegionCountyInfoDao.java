package com.cdd.gsl.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RegionCountyInfoDao {

    @Select("select county_name as countyName from t_region_city_info city " +
            "left join t_region_county_info county on city.city_id=county.city_id " +
            "where city.city_name=#{cityName}")
    public List<String> findCountyNameByCityName(@Param("cityName") String cityName);
}
