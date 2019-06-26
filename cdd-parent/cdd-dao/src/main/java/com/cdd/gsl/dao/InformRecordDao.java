package com.cdd.gsl.dao;

import com.cdd.gsl.domain.InformHouseRecordDomain;
import com.cdd.gsl.vo.AdminInformInfoConditionVo;
import com.cdd.gsl.vo.InformHouseConditionVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InformRecordDao {
    @Select("<script>" +
            "select id as id,user_id as userId,house_id as houseId,inform_info as informInfo,description as description," +
            "image as image,create_ts as createTs,status as status" +
            " from t_inform_house_record where 1=1 " +
            " limit #{from},#{limit}"+
            "</script>")
    List<InformHouseRecordDomain> informHouseList(AdminInformInfoConditionVo informHouseConditionVo);

    @Select("<script>" +
            "select count(*) " +
            " from t_inform_house_record where 1=1 " +
            "</script>")
    int informHouseCount(AdminInformInfoConditionVo informHouseConditionVo);
}
