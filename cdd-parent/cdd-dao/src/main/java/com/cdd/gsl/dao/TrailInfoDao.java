package com.cdd.gsl.dao;

import com.cdd.gsl.vo.TrailInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TrailInfoDao {
    @Select("select t.id as trailId,u.username as username,t.trail_info as trailInfo," +
            "t.create_ts as createTs,t.house_id as houseId " +
            " from t_user_info u left join t_trail_info t on u.id=t.user_id " +
             "where t.house_id=#{houseId} order by t.create_ts desc")
    List<TrailInfoVo> findTrailList(Long houseId);

}
