package com.cdd.gsl.dao;

import com.cdd.gsl.vo.ChatMessageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChatMessageDao {
    @Select("select id as id,send_user_id as sendUserId,receive_user_id as receiveUserId,obj_id as objId," +
            "type as type,message_content as messageContent,create_ts as createTs " +
            "from t_chat_message " +
            "where 1=1 and obj_id=#{objId} and type=#{type} and (receive_user_id=#{userId} or send_user_id=#{userId})" +
            " order by create_ts")
    List<ChatMessageVo> chatMessageList(@Param("objId") Long objId, @Param("type") String type,@Param("userId") Long userId);
}
