package com.cdd.gsl.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegionTownInfoDao {
    @Select("select town.town_name as townName from t_region_county_info county " +
            "left join t_region_town_info town on county.county_id=town.county_id " +
            "where county.county_name=#{county}")
    public List<String> findTownNameByCountyName(String county);
}
