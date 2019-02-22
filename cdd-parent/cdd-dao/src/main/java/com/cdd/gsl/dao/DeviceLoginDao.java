package com.cdd.gsl.dao;

import com.cdd.gsl.domain.DeviceLoginDomain;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeviceLoginDao {
    @Select("select phone as phone,device_id as deviceId from t_device_login " +
            "where phone=#{phone} order by create_ts desc")
    public List<DeviceLoginDomain> selectDeviceLoginByPhone(String phone);
}
