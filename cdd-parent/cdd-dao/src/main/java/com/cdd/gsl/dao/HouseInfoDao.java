package com.cdd.gsl.dao;

import com.cdd.gsl.domain.HouseInfoDomain;
import org.apache.ibatis.annotations.Select;

public interface HouseInfoDao {
    @Select("select id as id, username as username, phone as phone, province as province, city as city, " +
            "country as country, town as town, street as street, area as area, rent as rent, rent_type as rentType, " +
            "    house_type_id as houseTypeId, status, create_ts as createTs, update_ts as updateTs " +
            "from t_house_info where id=#{houseId} and status=1")
    public HouseInfoDomain selectHouseInfoById(Long houseId);
}
