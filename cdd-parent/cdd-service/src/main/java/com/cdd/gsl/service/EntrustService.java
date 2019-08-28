package com.cdd.gsl.service;

import com.cdd.gsl.admin.EntrustAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.EntrustInfoDomain;
import com.cdd.gsl.vo.EntrustConditionVo;
import com.cdd.gsl.vo.EntrustInfoVo;

import java.util.List;

public interface EntrustService {
    public CommonResult createEntrust(EntrustInfoDomain entrustInfoDomain);

    public CommonResult updateEntrust(EntrustInfoDomain entrustInfoDomain);

    public CommonResult deleteEntrust(EntrustInfoDomain entrustInfoDomain);

    public CommonResult<List<EntrustInfoVo>> findEntrustInfoList(EntrustConditionVo entrustConditionVo);

    public CommonResult<List<EntrustInfoVo>> findEntrustList(EntrustConditionVo entrustConditionVo);

    //*********************************admin manager***************************************************************

    public CommonResult findAdminEntrustList(EntrustAdminConditionVo entrustAdminConditionVo);
}
