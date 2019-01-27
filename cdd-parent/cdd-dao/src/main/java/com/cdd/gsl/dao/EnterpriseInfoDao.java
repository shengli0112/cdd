package com.cdd.gsl.dao;

import com.cdd.gsl.domain.EnterpriseInfoDomain;
import com.cdd.gsl.vo.EnterpriseConditionVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EnterpriseInfoDao {

    @Select("<script> " +
            "select id as id, main_business as mainBusiness, enterprise_name as enterpriseName, " +
            "address as address, register_date as registerDate, product_detail as productDetail,description as description, " +
            " contacts as contacts,phone as phone,image as image" +
            "from t_enterprise_info where status=1 " +
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<EnterpriseInfoDomain> selectEnterpriseInfoListByCondition(EnterpriseConditionVo enterpriseConditionVo);
}
