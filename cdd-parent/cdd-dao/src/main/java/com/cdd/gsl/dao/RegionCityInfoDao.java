package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RegionCityInfoDomain;
import com.cdd.gsl.vo.SearchCityVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegionCityInfoDao {
    @Select("select city_name as cityName from t_region_city_info")
    List<String> selectCityName();

    @Select("select * from " +
            "((select city.city_name as cityName,'' as countyName from t_region_city_info city where city.city_name like concat('%','${cityName}','%'))" +
            " union all " +
            " (select city.city_name as cityName,county.county_name as countyName from t_region_city_info city left join t_region_county_info county on city.city_id=county.city_id where county.county_name like concat('%','${cityName}','%')))" +
            " tmp")
    List<SearchCityVo> selectCityByCityName(String cityName);
}
