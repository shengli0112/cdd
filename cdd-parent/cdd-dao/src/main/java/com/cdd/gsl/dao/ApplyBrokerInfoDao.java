package com.cdd.gsl.dao;

import com.cdd.gsl.vo.ApplyBrokerConditionVo;
import com.cdd.gsl.vo.ApplyBrokerInfoVo;
import com.cdd.gsl.vo.UserBrokerVo;
import com.cdd.gsl.vo.UserInfoDemainVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ApplyBrokerInfoDao {
    @Select("<script>select a.user_id as userId,u.username as username,u.phone as phone,u.portrait as portrait," +
            "a.company_name as companyName,u.service_area as serviceArea,u.city as city,u.county as county,u.town as town" +
            " from t_apply_broker_info a left join t_user_info u on a.user_id=u.id " +
            "where a.apply_type=2 and u.status=1 " +
            "<if test='param != null'>" +
            " and u.service_area like concat('%','${param}','%')" +
            "</if>"+
            "limit #{from},#{pageSize}" +
            "</script>")
    public List<UserBrokerVo> userBrokerList(@Param("from") int from,@Param("pageSize") int pageSize,@Param("param")String param );

    @Select("select u.id as id,u.username as username,u.phone as phone," +
            "u.portrait as portrait,u.service_area as serviceArea,u.user_type as userType,u.integral as integral," +
            "u.status as status,u.create_ts as createTs,u.update_ts as updateTs" +
            " from t_apply_broker_info a left join t_user_info u on a.user_id=u.id where a.company_name=#{companyName} order by u.create_ts desc")
    public List<UserInfoDemainVo> companyUserList(String companyName);

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

    //公司员工
    @Select("select u.id as id,u.username as username,u.portrait as portrait" +
            " from t_apply_broker_info a left join t_user_info u on a.user_id=u.id " +
            "where a.company_name=(select company_name from t_apply_broker_info where user_id=#{loginUserId}) " +
            "and a.apply_type=2 and u.status=1 and a.status=1")
    public List<UserInfoDemainVo> companyHaveUserList(Long loginUserId);

    @Update("update t_apply_broker_info set status=0 where user_id=#{userId}")
    void deleteApplyBroker(Long userId);
}
