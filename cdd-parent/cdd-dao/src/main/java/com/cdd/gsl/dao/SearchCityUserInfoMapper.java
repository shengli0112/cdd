package com.cdd.gsl.dao;

import com.cdd.gsl.domain.SearchCityUserInfo;
import com.cdd.gsl.domain.SearchCityUserInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchCityUserInfoMapper {
    int countByExample(SearchCityUserInfoExample example);

    int deleteByExample(SearchCityUserInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SearchCityUserInfo record);

    int insertSelective(SearchCityUserInfo record);

    List<SearchCityUserInfo> selectByExample(SearchCityUserInfoExample example);

    SearchCityUserInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SearchCityUserInfo record, @Param("example") SearchCityUserInfoExample example);

    int updateByExample(@Param("record") SearchCityUserInfo record, @Param("example") SearchCityUserInfoExample example);

    int updateByPrimaryKeySelective(SearchCityUserInfo record);

    int updateByPrimaryKey(SearchCityUserInfo record);
}