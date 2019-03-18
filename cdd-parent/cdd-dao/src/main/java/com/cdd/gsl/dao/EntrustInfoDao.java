package com.cdd.gsl.dao;

import com.cdd.gsl.vo.EntrustConditionVo;
import com.cdd.gsl.vo.EntrustInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EntrustInfoDao {
    @Select("<script> " +
            "select e.id as entrustId, u.username as username,e.entrust_type as type, " +
            "(select dict_value from t_common_dict where dict_name='entrustType' and dict_code=e.entrust_type) as entrustType, " +
            "(select dict_value from t_common_dict where dict_name='entrustUseType' and dict_code=e.entrust_use_type) as entrustUseType, " +
            "concat(e.city,e.county,e.town) as address, e.create_ts as createTs, " +
            " e.contacts as contacts,e.phone as phone"  +
            " from t_entrust_user_mapping eum " +
             "left join t_entrust_info e on eum.entrust_id=e.id " +
             "left join t_user_info u on eum.user_id=u.id " +
             "where eum.user_id=#{userId} " +
            "<if test='entrustType != null'>" +
            " and e.entrust_type=#{entrustType} "+
            "</if>"+
            "<if test='entrustUseType != null'>" +
            " and e.entrust_use_type=#{entrustUseType} "+
            "</if>"+
            " order by e.create_ts desc " +
            "<if test=\"areaOrder != null\">"+
            "<if test=\"areaOrder == 1\">,area</if>"+
            "<if test=\"areaOrder == 2\">,area desc</if>"+
            "</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<EntrustInfoVo> findEntrustInfoByUserId(EntrustConditionVo entrustConditionVo);
}
