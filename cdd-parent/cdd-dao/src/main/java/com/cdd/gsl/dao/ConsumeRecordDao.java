package com.cdd.gsl.dao;

import com.cdd.gsl.vo.ConsumeRecordConditionVo;
import com.cdd.gsl.vo.ConsumeRecordVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ConsumeRecordDao {

    @Select("select cr.id as id,cr.title as title,u.id as userId,u.portrait as portrait,cr.integral as integral," +
            "cr.action as action,cr.type as type,cr.deadline as deadline,cr.create_ts as createTs " +
            " from t_user_info u left join t_consume_record cr on u.id=cr.user_id" +
            " where cr.user_id=#{userId} and cr.status=1 order by cr.create_ts desc" +
            " limit #{from},#{pageSize}")
    List<ConsumeRecordVo> selectConsumeRecordByUserId(ConsumeRecordConditionVo consumeRecordConditionVo);
}
