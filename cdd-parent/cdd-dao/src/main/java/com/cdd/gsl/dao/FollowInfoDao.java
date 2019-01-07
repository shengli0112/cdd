package com.cdd.gsl.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface FollowInfoDao {
    @Delete("delete from t_follow_info where user_id=#{userId} and follow_id in (#{followIds})")
    public void deleteFollowInfos(@Param("userId")Long userId,@Param("followIds")String followIds);
}
