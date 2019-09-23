package com.cdd.gsl.service;

import com.cdd.gsl.admin.AdminServiceConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.ServiceInfoDomain;
import com.cdd.gsl.vo.ServiceInfoConditionVo;

public interface ServiceInfoService {
    public CommonResult createServiceInfo(ServiceInfoDomain serviceInfoDomain);

    public CommonResult findServiceInfoList(ServiceInfoConditionVo serviceInfoConditionVo);

    public CommonResult findAdminServiceInfoList(AdminServiceConditionVo serviceInfoConditionVo);

    public CommonResult checkPhone(Long userId,Long serviceId);

    public CommonResult updateServiceInfo(ServiceInfoDomain serviceInfoDomain);

    public CommonResult deleteServiceInfo(Long userId,Long serviceId);
}
