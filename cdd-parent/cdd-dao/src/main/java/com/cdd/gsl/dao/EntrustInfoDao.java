package com.cdd.gsl.dao;

import com.cdd.gsl.vo.EntrustConditionVo;
import com.cdd.gsl.vo.EntrustInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EntrustInfoDao {
    @Select("<script> " +
            "select e.id as entrustId, u.username as username,e.entrust_type as type " +
            "(select dict_value from t_common_dict where dict_name='entrustType' and dict_code=e.entrust_type) as entrustType, " +
            "(select dict_value from t_common_dict where dict_name='entrustUseType' and dict_code=e.entrust_use_type) as entrustUseType, " +
            "concat(e.city,e.county) as address, e.create_ts as createTs, " +
            " e.contacts as contacts,e.phone as phone" +
            "from t_entrust_info e left join t_user_info u on e.user_id=u.id " +
            "where status=1 and e.user_id=#{userId} " +
            "<if test='entrustType != null'>" +
            " and e.entrust_type=#{entrustType} "+
            "</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<EntrustInfoVo> findEntrustInfoByUserId(EntrustConditionVo entrustConditionVo);
}
