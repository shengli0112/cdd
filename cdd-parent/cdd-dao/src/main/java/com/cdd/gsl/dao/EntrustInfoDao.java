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
            "concat(e.city,e.county,e.town) as address, e.create_ts as createTs, e.area as area," +
            " e.contacts as contacts,e.phone as phone,e.business as business"   +
            " from t_entrust_info e " +
            " left join t_entrust_user_mapping um on e.id=um.entrust_id "+
             "left join t_user_info u on um.user_id=u.id " +
             "where um.user_id=#{userId} " +
            "<if test=\"city != null\">" +
            " and e.city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and e.county=#{county}"+
            "</if>"+
            "<if test=\"town != null\">" +
            " and e.town=#{town}"+
            "</if>"+
            "<if test='entrustType != null'>" +
            " and e.entrust_type=#{entrustType} "+
            "</if>"+
            "<if test='entrustUseType != null'>" +
            " and e.entrust_use_type=#{entrustUseType} "+
            "</if>"+
            "<if test='keyword != null'>"+
            " and (e.business like concat('%','${keyword}','%') or e.contacts like concat('%','${keyword}','%') or e.phone like concat('%','${keyword}','%') ）" +
            "</if>"+
            " order by e.create_ts desc " +
            "<if test='areaOrder == 1'>,e.area</if>"+
            "<if test='areaOrder == 2'>,e.area desc</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<EntrustInfoVo> findEntrustInfoByUserId(EntrustConditionVo entrustConditionVo);

    @Select("<script> " +
            "select e.id as entrustId, u.username as username,e.entrust_type as type, " +
            "(select dict_value from t_common_dict where dict_name='entrustType' and dict_code=e.entrust_type) as entrustType, " +
            "(select dict_value from t_common_dict where dict_name='entrustUseType' and dict_code=e.entrust_use_type) as entrustUseType, " +
            "concat(e.city,e.county,e.town) as address, e.create_ts as createTs, e.area as area," +
            " e.contacts as contacts,e.phone as phone,e.business as business"   +
            " from t_entrust_info e " +
            "left join t_user_info u on e.user_id=u.id " +
            "where 1=1 " +
            "<if test=\"city != null\">" +
            " and e.city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and e.county=#{county}"+
            "</if>"+
            "<if test=\"town != null\">" +
            " and e.town=#{town}"+
            "</if>"+
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
    public List<EntrustInfoVo> findEntrustInfo(EntrustConditionVo entrustConditionVo);

    @Select("select e.id as entrustId, u.username as username,e.entrust_type as type, " +
            "(select dict_value from t_common_dict where dict_name='entrustType' and dict_code=e.entrust_type) as entrustType, " +
            "(select dict_value from t_common_dict where dict_name='entrustUseType' and dict_code=e.entrust_use_type) as entrustUseType, " +
            "concat(e.city,e.county,e.town) as address, e.create_ts as createTs, e.area as area," +
            " e.contacts as contacts,e.phone as phone,e.business as business"   +
            " from t_entrust_info e " +
            " left join t_entrust_user_mapping um on e.id=um.entrust_id "+
            "left join t_user_info u on um.user_id=u.id " +
            "where 1=1 and e.id=#{id}")
    List<EntrustInfoVo> findEntrustInfoById(Long id);
}
