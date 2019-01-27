package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.CompanyInfoDao;
import com.cdd.gsl.dao.CompanyInfoDomainMapper;
import com.cdd.gsl.dao.CompanyUserMappingDomainMapper;
import com.cdd.gsl.domain.CompanyInfoDomain;
import com.cdd.gsl.domain.CompanyUserMappingDomain;
import com.cdd.gsl.service.CompanyService;
import com.cdd.gsl.vo.CompanyVo;
import com.cdd.gsl.vo.UserCompanyInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyInfoDomainMapper companyInfoDomainMapper;

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Autowired
    private CompanyUserMappingDomainMapper companyUserMappingDomainMapper;

    @Override
    public CommonResult createCompany(CompanyInfoDomain companyVo) {
        CommonResult result = new CommonResult();
        if(companyVo.getUserId() != null && companyVo != null){
            companyInfoDomainMapper.insertSelective(companyVo);
            result.setFlag(CommonResult.RESULT_SUCCESS_FLAG);
            result.setMessage("创建公司成功");
        }else{
            result.setFlag(CommonResult.RESULT_FAILURE_FLAG);
            result.setMessage("创建公司失败，参数异常");
        }
        return result;
    }

    @Override
    public CommonResult<UserCompanyInfoVo> findUserByPhone(Long companyId, String phone)
    {
        CommonResult<UserCompanyInfoVo> commonResult = new CommonResult<>();
        if(companyId != null && phone != null){
            UserCompanyInfoVo userCompanyInfoVo = companyInfoDao.selectUserByCompanyAndPhone(companyId,phone);
            Integer agree = userCompanyInfoVo.getAgree();
            if(agree == null){
                userCompanyInfoVo.setAgree(3);
            }
            commonResult.setFlag(CommonResult.RESULT_SUCCESS_FLAG);
            commonResult.setMessage("查询成功");
            commonResult.setData(userCompanyInfoVo);
        }else{
            commonResult.setFlag(CommonResult.RESULT_FAILURE_FLAG);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult inviteUserToCompany(Long companyId, Long userId) {
        CommonResult commonResult = new CommonResult();
        if(companyId != null && userId != null){
            CompanyUserMappingDomain companyUserMappingDomain = new CompanyUserMappingDomain();
            companyUserMappingDomain.setCompanyId(companyId);
            companyUserMappingDomain.setUserId(userId);
            companyUserMappingDomainMapper.insertSelective(companyUserMappingDomain);
            commonResult.setFlag(CommonResult.RESULT_SUCCESS_FLAG);
            commonResult.setMessage("邀请用户成功，等待通过");
        }else{
            commonResult.setFlag(CommonResult.RESULT_FAILURE_FLAG);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }




}
