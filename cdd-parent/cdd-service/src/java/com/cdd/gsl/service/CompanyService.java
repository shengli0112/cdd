package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.vo.CompanyVo;
import com.cdd.gsl.vo.UserCompanyInfoVo;

public interface CompanyService {
    CommonResult createCompany(Long userId,CompanyVo companyVo);

    CommonResult<UserCompanyInfoVo> findUserByPhone(Long companyId, String phone);

    CommonResult inviteUserToCompany(Long companyId,Long userId);
}
