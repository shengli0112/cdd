package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.RecordInfoDomain;
import com.cdd.gsl.vo.RecordInfoVo;

import java.util.List;

public interface RecordInfoService {

    public CommonResult createRecordInfo(RecordInfoDomain recordInfoDomain);

    public List<RecordInfoVo> findRecordInfoList(Long houseId);
}
