package com.cdd.gsl.dao;

import com.cdd.gsl.vo.RecordInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordInfoDao {
    @Select("Select id as id,record as record,username as username,house_id as houseId " +
            "from t_record_info r left join t_user_info u on r.user_id=u.id where r.house_id=#{houseId}")
    public List<RecordInfoVo> findRecordInfoByHouseId(Long houseId);
}
