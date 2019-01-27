package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.StorageInfoDomain;
import com.cdd.gsl.vo.StorageConditionVo;
import com.cdd.gsl.vo.StorageInfoDomainVo;

import java.util.List;

public interface StorageService {
    public CommonResult createStorage(StorageInfoDomain storageInfoDomain);

    public List<StorageInfoDomain> getStorageList(StorageConditionVo storageConditionVo);

    public StorageInfoDomainVo getStorageInfoById(Long id);
}
