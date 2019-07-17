package com.cdd.gsl.dao;

import com.cdd.gsl.domain.EnterpriseInfoDomain;
import com.cdd.gsl.vo.EnterpriseAdminConditionVo;
import com.cdd.gsl.vo.EnterpriseConditionVo;
import com.cdd.gsl.vo.EnterpriseInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EnterpriseInfoDao {

    @Select("<script> " +
            "select id as id, main_business as mainBusiness, enterprise_name as enterpriseName, " +
            "address as address, register_date as registerDate,description as description, " +
            " contacts as contacts,phone as phone,image as image,user_id as userId,title as title,trade as trade,price as price" +
            " from t_enterprise_info where status=1 " +
            "<if test='keyword != null'>" +
            " and (title like concat('%','${keyword}','%') or main_business like concat('%','${keyword}','%') or enterprise_name like concat('%','${keyword}','%') or address like concat('%','${keyword}','%') " +
            " or description like concat('%','${keyword}','%') or trade like concat('%','${keyword}','%'))"+
            "</if>"+
            "<if test='userId != null'>"+
            " and user_id = #{userId}"+
            "</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<EnterpriseInfoDomain> selectEnterpriseInfoListByCondition(EnterpriseConditionVo enterpriseConditionVo);

    @Select("<script> " +
            "select id as id, main_business as mainBusiness, enterprise_name as enterpriseName, " +
            "address as address, register_date as registerDate,description as description, " +
            " contacts as contacts,phone as phone,image as image,user_id as userId,title as title,trade as trade,price as price," +
            "create_ts as createTs,update_ts as updateTs,status as status"+
            " from t_enterprise_info where 1=1 " +
            "<if test='keyword != null'>" +
            " and (title like concat('%','${keyword}','%') or main_business like concat('%','${keyword}','%') or enterprise_name like concat('%','${keyword}','%') or address like concat('%','${keyword}','%') " +
            " or description like concat('%','${keyword}','%') or trade like concat('%','${keyword}','%'))"+
            "</if>"+
            " order by status desc "+
            " limit #{from},#{limit}"+
            "</script>")
    public List<EnterpriseInfoVo> selectAdminEnterpriseInfoListByCondition(EnterpriseAdminConditionVo enterpriseConditionVo);

    @Select("<script> " +
            "select count(*)  " +
            " from t_enterprise_info where 1=1 " +
            "<if test='keyword != null'>" +
            " and (title like concat('%','${keyword}','%') or main_business like concat('%','${keyword}','%') or enterprise_name like concat('%','${keyword}','%') or address like concat('%','${keyword}','%') " +
            " or description like concat('%','${keyword}','%') or trade like concat('%','${keyword}','%'))"+
            "</if>"+
            "</script>")
    public int enterpriseCount(EnterpriseAdminConditionVo enterpriseConditionVo);

    @Select("select id as id, main_business as mainBusiness, enterprise_name as enterpriseName, " +
            "address as address, register_date as registerDate, trade as trade,description as description, " +
            " contacts as contacts,phone as phone,image as image,user_id as userId,title as title,price as price" +
            " from t_enterprise_info where status=1 " +
            " order by rand() limit 3")
    public List<EnterpriseInfoVo> selectEnterpriseInfoListRand();

    @Update("update t_enterprise_info set status=0 where id=#{enterpriseId} and user_id=#{userId}")
    void deleteEnterpriseInfoByIdAndUserId(@Param("enterpriseId") Long enterpriseId,@Param("userId")Long userId);
}
