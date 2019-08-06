package com.cdd.gsl.dao;

import com.cdd.gsl.domain.MessageInfoDomain;
import com.cdd.gsl.vo.MessageConditionVo;
import com.cdd.gsl.vo.MessageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MessageInfoDao {

    @Select("select * from ((select id as messageId,message as message,user_id as userId,entrust_id as entrustId," +
            "house_id as houseId,is_read as isRead,create_ts as createTs,message_type as messageType," +
            "obj_id as objId,type as type,send_user_id as sendUserId,receive_user_id as receiveUserId " +
            "from t_message_info where user_id=#{userId} and message_type != 'chat')" +
            "union all" +
            "(select id as messageId,message as message,user_id as userId,entrust_id as entrustId," +
            "house_id as houseId,is_read as isRead,create_ts as createTs,message_type as messageType," +
            "obj_id as objId,type as type,send_user_id as sendUserId,receive_user_id as receiveUserId " +
            " from t_message_info where id in (select max(id) " +
            "from t_message_info where user_id=#{userId} and message_type='chat' group by send_user_id))) t" +
            " order by createTs desc " +
            " limit #{from},#{pageSize}" )
    List<MessageVo> messageList(MessageConditionVo messageConditionVo);
    @Update("update t_message_info set is_read=1 where id=#{messageId}")
    void updateMessageRead(Long messageId);

    @Select("select count(id) from t_message_info where user_id=#{userId} and is_read=0")
    int countUnReadMessageCount(Long userId);

    @Update("update t_message_info set is_read=1 where obj_id=#{objId} and type=#{type} " +
            "and (send_user_id=#{sendUserId} and receive_user_id=#{receiveUserId})")
    void updateMessageIsRead(@Param("objId") Long objId,@Param("type") String type,@Param("sendUserId") Long sendUserId,@Param("receiveUserId") Long receiveUserId);


}
