package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.EntrustInfoDao;
import com.cdd.gsl.dao.EntrustInfoDomainMapper;
import com.cdd.gsl.domain.EntrustInfoDomain;
import com.cdd.gsl.service.EntrustService;
import com.cdd.gsl.vo.EntrustConditionVo;
import com.cdd.gsl.vo.EntrustInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrustServiceImpl implements EntrustService {

    @Autowired
    private EntrustInfoDomainMapper entrustInfoDomainMapper;

    @Autowired
    private EntrustInfoDao entrustInfoDao;

    @Override
    public CommonResult createEntrust(EntrustInfoDomain entrustInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(entrustInfoDomain != null){
            entrustInfoDomainMapper.insertSelective(entrustInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateEntrust(EntrustInfoDomain entrustInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(entrustInfoDomain != null){
            entrustInfoDomainMapper.updateByPrimaryKeySelective(entrustInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<List<EntrustInfoVo>> findEntrustInfoList(EntrustConditionVo entrustConditionVo) {
        CommonResult<List<EntrustInfoVo>> commonResult = new CommonResult<>();
        if(entrustConditionVo.getUserId() != null && entrustConditionVo.getEntrustType() != null){
            List<EntrustInfoVo> entrustInfoVos = entrustInfoDao.findEntrustInfoByUserId(entrustConditionVo);
            commonResult.setData(entrustInfoVos);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }
}
