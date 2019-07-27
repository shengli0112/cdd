package com.cdd.gsl.dao;

import com.cdd.gsl.domain.SlideInfoDomain;
import com.cdd.gsl.vo.SlideConditionVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SlideInfoDao {
    @Select("<script>" +
            "select id as id,title as title,slide_url as slideUrl,sequence as sequence,status as status" +
            " from t_slide_info where 1=1 " +
            " limit #{from},#{limit}"+
            "</script>")
    List<SlideInfoDomain> slideList(SlideConditionVo slideConditionVo);

    @Select("<script>" +
            "select id as id,title as title,slide_url as slideUrl,sequence as sequence" +
            " from t_slide_info where 1=1 and status=1  and city='全国' " +
            "<if test='city != null'>" +
            " or city=#{city}" +
            "</if>" +
            "</script>")
    List<SlideInfoDomain> slideInfoList(String city);

    @Select("<script>" +
            "select count(*)" +
            " from t_slide_info where 1=1 " +
            "</script>")
    int slideCount(SlideConditionVo slideConditionVo);
}
