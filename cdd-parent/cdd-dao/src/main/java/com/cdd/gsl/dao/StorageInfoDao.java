package com.cdd.gsl.dao;

import com.cdd.gsl.vo.StorageConditionVo;
import com.cdd.gsl.vo.StorageInfoDomainVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StorageInfoDao {
    //TODO
    @Select("")
    public List<StorageInfoDomainVo> selectStorageInfoListByCondition(StorageConditionVo storageConditionVo);
}
