package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.LeaseParkInfoDomainMapper;
import com.cdd.gsl.dao.SellParkDao;
import com.cdd.gsl.dao.SellParkInfoDomainMapper;
import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomain;
import com.cdd.gsl.service.ParkService;
import com.cdd.gsl.vo.SellParkCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkServiceImpl implements ParkService {

    @Autowired
    private SellParkInfoDomainMapper sellParkInfoDomainMapper;

    @Autowired
    private LeaseParkInfoDomainMapper leaseParkInfoDomainMapper;

    @Autowired
    private SellParkDao sellParkDao;

    @Override
    public CommonResult createSellPark(SellParkInfoDomain sellParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(sellParkInfoDomain != null){
            sellParkInfoDomainMapper.insertSelective(sellParkInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("创建成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult createLeasePark(LeaseParkInfoDomain leaseParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(leaseParkInfoDomain != null){
            leaseParkInfoDomainMapper.insertSelective(leaseParkInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("创建成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateSellPark(SellParkInfoDomain sellParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(sellParkInfoDomain != null){
            sellParkInfoDomainMapper.updateByPrimaryKeySelective(sellParkInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateLeasePark(LeaseParkInfoDomain leaseParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(leaseParkInfoDomain != null){
            leaseParkInfoDomainMapper.updateByPrimaryKeySelective(leaseParkInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult<SellParkInfoDomain> findSellParkDetail(Long sellParkId) {
        CommonResult<SellParkInfoDomain>  commonResult = new CommonResult<>();
        if(sellParkId != null){
            SellParkInfoDomain sellParkInfoDomain = sellParkInfoDomainMapper.selectByPrimaryKey(sellParkId);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(sellParkInfoDomain);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<LeaseParkInfoDomain> findLeaseParkDetail(Long leaseParkId) {
        CommonResult<LeaseParkInfoDomain>  commonResult = new CommonResult<>();
        if(leaseParkId != null){
            LeaseParkInfoDomain leaseParkInfoDomain = leaseParkInfoDomainMapper.selectByPrimaryKey(leaseParkId);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(leaseParkInfoDomain);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public List<SellParkInfoDomain> findSellParkList(SellParkCondition sellParkCondition) {
        List<SellParkInfoDomain> sellParkInfoDomainList = sellParkDao.selectSellParkInfoList(sellParkCondition);
        return sellParkInfoDomainList;
    }

    @Override
    public List<LeaseParkInfoDomain> findLeaseParkList(String region, String price, String area, Long leaseParkId) {
        return null;
    }
}
