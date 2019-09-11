package com.cdd.gsl.dao;

import com.cdd.gsl.vo.ServiceTypeVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ServiceTypeDao {

    @Select("select id id,service_type_name serviceTypeName from t_service_type where status=1")
    public List<ServiceTypeVo> getServiceTypeList();
}
