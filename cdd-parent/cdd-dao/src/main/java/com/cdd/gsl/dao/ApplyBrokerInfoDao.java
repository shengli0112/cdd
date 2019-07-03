package com.cdd.gsl.dao;

import com.cdd.gsl.vo.ApplyBrokerConditionVo;
import com.cdd.gsl.vo.ApplyBrokerInfoVo;
import com.cdd.gsl.vo.UserBrokerVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ApplyBrokerInfoDao {
    @Select("select a.user_id as userId,u.username as username,u.phone as phone,u.portrait as portrait,a.company_name as companyName,u.service_area as serviceArea" +
            " from t_apply_broker_info a left join t_user_info u on a.user_id=u.id where a.apply_type=2 limit #{from},#{pageSize}")
    public List<UserBrokerVo> userBrokerList(@Param("from") int from,@Param("pageSize") int pageSize );

    //搜索同公司的人
    @Select("select user_id from t_apply_broker_info where apply_type=2 and company_name = (select company_name from t_apply_broker_info where user_id=#{userId})")
    List<Long> selectBrokerByUserId(Long userId);

    @Select("select id as id,company_name as companyName,user_id as userId,license as license,address as address," +
            "line_business as lineBusiness,register_date as registerDate,description as description," +
            "contacts as contacts,phone as phone,apply_type as applyType" +
            " from t_apply_broker_info where broker_type=2 limit #{from},#{limit}")
    List<ApplyBrokerInfoVo> managerBrokerList(ApplyBrokerConditionVo applyBrokerConditionVo);

    @Select("select id as id,company_name as companyName,user_id as userId,license as license,address as address," +
            "line_business as lineBusiness,register_date as registerDate,description as description," +
            "contacts as contacts,phone as phone,apply_type as applyType" +
            " from t_apply_broker_info where broker_type != 2 and company_name=#{companyName}")
    List<ApplyBrokerInfoVo> companyBrokerList(String companyName);


    @Select("select count(*)" +
            " from t_apply_broker_info where broker_type=2")
    Integer managerBrokerCount();
}
