package com.cdd.gsl.dao;

import com.cdd.gsl.admin.AdminServiceConditionVo;
import com.cdd.gsl.vo.ServiceInfoConditionVo;
import com.cdd.gsl.vo.ServiceInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ServiceInfoDao {

    @Select("<script>" +
            "select si.id as id,si.username as username,si.phone as phone,si.description as description," +
            "st.id as serviceTypeId,st.service_type_name as serviceTypeName,si.create_ts as createTs " +
            "from t_service_info si left join t_service_type st on si.service_type_id=st.id where si.status=1" +
            "<if test='keyword != null'>" +
            " and (si.username like concat('%','${keyword}','%') or si.description like concat('%','${keyword}','%')" +
            " or st.service_type_name like concat('%','${keyword}','%'))" +
            " order by si.create_ts desc " +
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<ServiceInfoVo> getServiceInfoList(ServiceInfoConditionVo serviceInfoConditionVo);


    @Select("<script>" +
            "select si.id as id,si.username as username,si.phone as phone,si.description as description," +
            "st.id as serviceTypeId,st.service_type_name as serviceTypeName,si.create_ts as createTs " +
            "from t_service_info si left join t_service_type st on si.service_type_id=st.id where si.status=1" +
            "<if test='keyword != null'>" +
            " and (si.username like concat('%','${keyword}','%') or si.description like concat('%','${keyword}','%')" +
            " or st.service_type_name like concat('%','${keyword}','%'))" +
            " order by si.create_ts desc " +
            " limit #{from},#{limit}"+
            "</script>")
    public List<ServiceInfoVo> getAdminServiceInfoList(AdminServiceConditionVo serviceInfoConditionVo);
}
