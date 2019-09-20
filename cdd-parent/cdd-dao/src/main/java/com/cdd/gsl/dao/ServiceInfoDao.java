package com.cdd.gsl.dao;

import com.cdd.gsl.admin.AdminServiceConditionVo;
import com.cdd.gsl.vo.ServiceInfoConditionVo;
import com.cdd.gsl.vo.ServiceInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ServiceInfoDao {

    @Select("<script>" +
            "select si.id as id,si.username as username,si.phone as phone,si.description as description," +
            "si.service_type_name as serviceTypeName ,si.create_ts as createTs,si.city as city," +
            "si.county as county,si.town as town,si.trade as trade " +
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
            " or si.trade like concat('%','${keyword}','%') or si.trade like concat('%','${keyword}','%')) " +
            "</if>" +
            " order by si.create_ts desc " +
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<ServiceInfoVo> getServiceInfoList(ServiceInfoConditionVo serviceInfoConditionVo);


    @Select("<script>" +
            "select si.id as id,si.username as username,si.phone as phone,si.description as description," +
            "st.service_type_name as serviceTypeName,si.create_ts as createTs,si.city as city, " +
            "si.county as county,si.town as town,si.trade as trade " +
            "from t_service_info si left join t_service_type st on si.service_type_id=st.id where si.status=1" +
            "<if test='keyword != null'>" +
            " and (si.username like concat('%','${keyword}','%') or si.description like concat('%','${keyword}','%')" +
            " or st.service_type_name like concat('%','${keyword}','%'))" +
            "</if>" +
            " order by si.create_ts desc " +
            " limit #{from},#{limit}"+
            "</script>")
    public List<ServiceInfoVo> getAdminServiceInfoList(AdminServiceConditionVo serviceInfoConditionVo);
}
