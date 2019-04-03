package com.cdd.gsl.dao;

import com.cdd.gsl.domain.MessageInfoDomain;
import com.cdd.gsl.vo.MessageVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageInfoDao {

    @Select("select id as messageId,message as message,user_id as userId,entrust_id as entrustId," +
            "house_id as houseId,is_read as isRead,create_ts as createTs from t_message_info where user_id=#{userId} order by create_ts desc" +
            " limit #{from},#{pageSize}")
    List<MessageVo> messageList(Long userId);
}
