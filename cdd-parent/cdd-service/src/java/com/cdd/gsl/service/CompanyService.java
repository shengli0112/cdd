package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.vo.CompanyVo;

public interface CompanyService {
    CommonResult createCompany(Long userId,CompanyVo companyVo);

    CommonResult findUserByPhone(Long companyId,String phone);

    CommonResult inviteUserToCompany(Long companyId,Long userId);
}
