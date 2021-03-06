package com.cdd.gsl.dao;

import com.cdd.gsl.admin.AdminServiceConditionVo;
import com.cdd.gsl.admin.ServiceAdminConditionVo;
import com.cdd.gsl.vo.ServiceInfoConditionVo;
import com.cdd.gsl.vo.ServiceInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ServiceInfoDao {

    @Select("<script>" +
            "select si.id as id,si.username as username,si.phone as phone,si.description as description," +
            "si.service_type_name as serviceTypeName ,si.create_ts as createTs,si.city as city," +
            "si.county as county,si.town as town,referrer as referrer,referrer_phone as referrerPhone " +
            "from t_service_info si where si.status=1" +
            "<if test=\"city != null\">" +
            " and si.city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and si.county=#{county}"+
            "</if>" +
            "<if test=\"town != null\">" +
            " and si.town=#{town}"+
            "</if>" +
            "<if test=\"serviceTypeName != null\">" +
            " and si.service_type_name like concat('%','${serviceTypeName}','%')"+
            "</if>" +
            "<if test='keyword != null'>" +
            " and (si.username like concat('%','${keyword}','%') or si.description like concat('%','${keyword}','%')" +
            " or si.service_type_name like concat('%','${keyword}','%') or si.city like concat('%','${keyword}','%')" +
            " or si.county like concat('%','${keyword}','%') or si.town like concat('%','${keyword}','%') " +
            " or si.referrer like concat('%','${keyword}','%')) " +
            "</if>" +
            " order by si.create_ts desc " +
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<ServiceInfoVo> getServiceInfoList(ServiceInfoConditionVo serviceInfoConditionVo);

    @Select("<script>" +
            "select si.id as id,si.username as username,si.phone as phone,si.description as description," +
            "si.service_type_name as serviceTypeName ,si.create_ts as createTs,si.city as city," +
            "si.county as county,si.town as town,referrer as referrer,referrer_phone as referrerPhone " +
            "from t_service_info si where si.status=1 and si.user_id=#{userId} " +
            "<if test=\"city != null\">" +
            " and si.city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and si.county=#{county}"+
            "</if>" +
            "<if test=\"town != null\">" +
            " and si.town=#{town}"+
            "</if>" +
            "<if test=\"serviceTypeName != null\">" +
            " and si.service_type_name like concat('%','${serviceTypeName}','%')"+
            "</if>" +
            "<if test='keyword != null'>" +
            " and (si.username like concat('%','${keyword}','%') or si.description like concat('%','${keyword}','%')" +
            " or si.service_type_name like concat('%','${keyword}','%') or si.city like concat('%','${keyword}','%')" +
            " or si.county like concat('%','${keyword}','%') or si.town like concat('%','${keyword}','%') " +
            " or si.referrer like concat('%','${keyword}','%')) " +
            "</if>" +
            " order by si.create_ts desc " +
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<ServiceInfoVo> getServiceInfoListByUserId(ServiceInfoConditionVo serviceInfoConditionVo);

    @Select("<script>" +
            "select si.id as id,si.username as username,si.phone as phone,si.description as description," +
            "si.service_type_name as serviceTypeName ,si.create_ts as createTs,si.city as city," +
            "si.county as county,si.town as town,si.referrer as referrer,si.referrer_phone as referrerPhone " +
            "from t_service_info si where si.status=1 and si.id=#{serviceId} " +
            "</script>")
    public List<ServiceInfoVo> getServiceInfoById(Long serviceId);


    @Select("<script> " +
            "select si.id as id,si.username as username,si.phone as phone,si.description as description," +
            "si.service_type_name as serviceTypeName ,si.create_ts as createTs,si.city as city," +
            "si.county as county,si.town as town,si.referrer as referrer,si.referrer_phone as referrerPhone,si.status as status " +
            " from t_service_info si " +
            "where 1=1 " +

            "<if test='keyword != null'>"+
            " and (si.username like concat('%','${keyword}','%') or si.phone like concat('%','${keyword}','%') " +
            "or si.description like concat('%','${keyword}','%') or si.referrer like concat('%','${keyword}','%') " +
            "or si.city like concat('%','${keyword}','%') or si.county like concat('%','${keyword}','%')" +
            " or si.town like concat('%','${keyword}','%') or si.referrer_phone like concat('%','${keyword}','%'))" +

            "</if>"+
            " order by si.create_ts desc " +

            " limit #{from},#{limit}"+
            "</script>")
    public List<ServiceInfoVo> findAdminServiceInfoList(ServiceAdminConditionVo serviceAdminConditionVo);

    @Select("select count(id) from t_service_info")
    int countAdminServiceInfo();
}
