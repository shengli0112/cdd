package com.cdd.gsl.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UrlInfoDao {
    @Select("select url from t_url_info")
    public List<String> selectUrlList();
}
