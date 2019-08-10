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
            "select * from ((select e.id as id, e.main_business as mainBusiness, e.enterprise_name as enterpriseName, " +
            "e.address as address, e.register_date as registerDate,e.description as description, " +
            " e.contacts as contacts,e.phone as phone,e.image as image,e.user_id as userId,e.title as title,e.trade as trade,e.price as price,e.top as top" +
            " from t_house_top t left join t_enterprise_info e on t.obj_id=e.id where e.status=1 and t.type='enterprise' "+
            "<if test='keyword != null'>" +
            " and (e.title like concat('%','${keyword}','%') or e.main_business like concat('%','${keyword}','%') or e.enterprise_name like concat('%','${keyword}','%') or e.address like concat('%','${keyword}','%') " +
            " or e.description like concat('%','${keyword}','%') or e.trade like concat('%','${keyword}','%'))"+
            "</if>"+
            "<if test='userId != null'>"+
            " and e.user_id = #{userId}"+
            "</if>)" +
            " union all "+
            "(select e.id as id, e.main_business as mainBusiness, e.enterprise_name as enterpriseName, " +
            "e.address as address, e.register_date as registerDate,e.description as description, " +
            " e.contacts as contacts,e.phone as phone,e.image as image,e.user_id as userId,e.title as title,e.trade as trade,e.price as price,e.top as top" +
            " from t_enterprise_info e where e.status=1 and e.top=0 " +
            "<if test='keyword != null'>" +
            " and (e.title like concat('%','${keyword}','%') or e.main_business like concat('%','${keyword}','%') or e.enterprise_name like concat('%','${keyword}','%') or e.address like concat('%','${keyword}','%') " +
            " or e.description like concat('%','${keyword}','%') or e.trade like concat('%','${keyword}','%'))"+
            "</if>"+
            "<if test='userId != null'>"+
            " and e.user_id = #{userId}"+
            "</if>)) tmp "+
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
            " order by status desc,id asc "+
            " limit #{from},#{limit}"+
            "</script>")
    public List<EnterpriseInfoVo> selectAdminEnterpriseInfoListByCondition(EnterpriseAdminConditionVo enterpriseConditionVo);

    @Select("<script> " +
            "select id as id, main_business as mainBusiness, enterprise_name as enterpriseName, " +
            "address as address, register_date as registerDate,description as description, " +
            " contacts as contacts,phone as phone,image as image,user_id as userId,title as title,trade as trade,price as price," +
            "create_ts as createTs,update_ts as updateTs,status as status"+
            " from t_enterprise_info where 1=1 and id=#{id} and status=1" +
            "</script>")
    public List<EnterpriseInfoVo> selectAdminEnterpriseInfoListById(Long id);

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
