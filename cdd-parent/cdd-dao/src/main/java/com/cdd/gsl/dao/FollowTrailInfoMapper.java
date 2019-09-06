package com.cdd.gsl.dao;

import com.cdd.gsl.domain.FollowTrailInfo;
import com.cdd.gsl.domain.FollowTrailInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowTrailInfoMapper {
    int countByExample(FollowTrailInfoExample example);

    int deleteByExample(FollowTrailInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FollowTrailInfo record);

    int insertSelective(FollowTrailInfo record);

    List<FollowTrailInfo> selectByExample(FollowTrailInfoExample example);

    FollowTrailInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FollowTrailInfo record, @Param("example") FollowTrailInfoExample example);

    int updateByExample(@Param("record") FollowTrailInfo record, @Param("example") FollowTrailInfoExample example);

    int updateByPrimaryKeySelective(FollowTrailInfo record);

    int updateByPrimaryKey(FollowTrailInfo record);
}