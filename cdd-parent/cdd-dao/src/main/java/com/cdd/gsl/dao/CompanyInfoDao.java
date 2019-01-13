package com.cdd.gsl.dao;

import com.cdd.gsl.vo.UserCompanyInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CompanyInfoDao {
    @Select("select u.id as userId,u.phone as phone,u.user_type as userType," +
            "u.portrait as portrait, cum.agree as agree " +
            "from t_user_info u left join t_company_user_mapping cum " +
            "on u.id=cum.user_id and u.phone=#{phone} and cum.company_id=#{companyId} " +
            "and u.status=1 and u.userType=2")
    public UserCompanyInfoVo selectUserByCompanyAndPhone(@Param("companyId") Long conpanyId, @Param("phone") String phone);
}
