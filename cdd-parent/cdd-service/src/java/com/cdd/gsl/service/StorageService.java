package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.StorageInfoDomain;

public interface StorageService {
    public CommonResult createStorage(StorageInfoDomain storageInfoDomain);
}
