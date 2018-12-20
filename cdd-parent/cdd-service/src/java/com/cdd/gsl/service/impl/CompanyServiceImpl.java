package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.CompanyInfoDomainMapper;
import com.cdd.gsl.domain.CompanyInfoDomain;
import com.cdd.gsl.service.CompanyService;
import com.cdd.gsl.vo.CompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyInfoDomainMapper companyInfoDomainMapper;
    @Override
    public CommonResult createCompany(Long userId,CompanyVo companyVo) {
        CompanyInfoDomain companyInfoDomain = new CompanyInfoDomain();
        CommonResult result = new CommonResult();
        if(userId != null && companyVo != null){
            companyInfoDomain.setUserId(userId);
            companyInfoDomain.setBackground(companyVo.getBackground());
            companyInfoDomain.setCompanyName(companyVo.getCompanyName());
            companyInfoDomain.setDescription(companyVo.getDescription());
            companyInfoDomainMapper.insertSelective(companyInfoDomain);
            result.setFlag(CommonResult.RESULT_SUCCESS_FLAG);
            result.setMessage("创建公司成功");
        }else{
            result.setFlag(CommonResult.RESULT_FAILURE_FLAG);
            result.setMessage("创建公司失败，参数异常");
        }
        return result;
    }

    @Override
    public CommonResult findUserByPhone(Long companyId, String phone) {
        return null;
    }

    @Override
    public CommonResult inviteUserToCompany(Long companyId, Long userId) {
        return null;
    }
}
