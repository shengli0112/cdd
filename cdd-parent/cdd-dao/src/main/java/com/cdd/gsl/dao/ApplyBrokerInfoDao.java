package com.cdd.gsl.dao;

import com.cdd.gsl.vo.UserBrokerVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ApplyBrokerInfoDao {
    @Select("select a.user_id as userId,u.username as username,u.phone as phone,u.portrait as portrait,a.company_name as companyName" +
            " from t_apply_broker_info a left join t_user_info u on a.user_id=u.id where a.apply_type=2 limit #{from},#{pageSize}")
    public List<UserBrokerVo> userBrokerList(@Param("from") int from,@Param("pageSize") int pageSize );

    //搜索同公司的人
    @Select("select user_id from t_apply_broker_info where apply_type=2 and company_name = (select company_name from t_apply_broker_info where user_id=#{userId})")
    List<Long> selectBrokerByUserId(Long userId);
}
