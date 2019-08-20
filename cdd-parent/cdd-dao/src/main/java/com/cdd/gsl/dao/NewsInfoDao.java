package com.cdd.gsl.dao;

import com.cdd.gsl.admin.NewsAdminConditionVo;
import com.cdd.gsl.domain.NewsInfoDomain;
import com.cdd.gsl.vo.NewsInfoVo;
import com.cdd.gsl.vo.NewsParamVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NewsInfoDao {

    @Select("<script>" +
            "select id as id,title as title,link_desc as linkDesc,link_url as linkUrl," +
            "home_image as homeImage,create_ts as createTs,status as status from t_news_info " +
            " where 1=1" +
            " <if test='keyword != null'>" +
            " and (title like concat('%','${keyword}','%') or link_desc like concat('%','${keyword}','%') " +
            "or link_url like concat('%','${keyword}','%') or home_image like concat('%','${keyword}','%')" +
            " or content like concat('%','${keyword}','%'))  " +
            "</if>" +
            " order by create_ts desc " +
            " limit #{from},#{limit}"+
            "</script>")
    public List<NewsInfoDomain> findNewsList(NewsAdminConditionVo newsAdminConditionVo);

    @Select("<script>" +
            "select id as id,title as title,link_desc as linkDesc,link_url as linkUrl," +
            "home_image as homeImage,create_ts as createTs,status as status from t_news_info " +
            " where 1=1 and status=1" +
            " <if test='keyword != null'>" +
            " and (title like concat('%','${keyword}','%') or link_desc like concat('%','${keyword}','%') " +
            "or link_url like concat('%','${keyword}','%') or home_image like concat('%','${keyword}','%')" +
            " or content like concat('%','${keyword}','%'))  " +
            "</if>" +
            " order by create_ts desc " +
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<NewsInfoVo> newsList(NewsParamVo newsParamVo);

    @Select("select id as id,title as title,link_desc as linkDesc,link_url as linkUrl," +
            "home_image as homeImage,content as content,create_ts as createTs,status as status from t_news_info " +
            " where 1=1 and status=1 and id=#{newsId}")
    public List<NewsInfoVo> findNewsById(Long newsId);

    @Select("<script>" +
            "select count(*) from t_news_info " +
            " where 1=1" +
            " <if test='keyword != null'>" +
            " and (title like concat('%','${keyword}','%') or link_desc like concat('%','${keyword}','%') " +
            "or link_url like concat('%','${keyword}','%') or home_image like concat('%','${keyword}','%')" +
            " or content like concat('%','${keyword}','%'))  " +
            "</if>" +
            "</script>")
    public int findNewsCount(NewsAdminConditionVo newsAdminConditionVo);

    @Update("update t_news_info set status=#{status},update_ts=#{updateTs} where id=#{id}")
    void updateNewsStatus(NewsInfoDomain newsInfoDomain);
}
