package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.ParkAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.LeaseParkDao;
import com.cdd.gsl.dao.LeaseParkInfoDomainMapper;
import com.cdd.gsl.dao.SellParkDao;
import com.cdd.gsl.dao.SellParkInfoDomainMapper;
import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomain;
import com.cdd.gsl.service.ParkService;
import com.cdd.gsl.vo.LeaseParkCondition;
import com.cdd.gsl.vo.LeaseParkInfoVo;
import com.cdd.gsl.vo.SellParkCondition;
import com.cdd.gsl.vo.SellParkInfoVo;
import org.apache.commons.collections4.CollectionUtils;
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

    @Autowired
    private LeaseParkDao leaseParkDao;

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
    public CommonResult findSellParkDetail(Long sellParkId) {
        CommonResult  commonResult = new CommonResult<>();
        if(sellParkId != null){
            List<SellParkInfoVo> sellParkInfoVoList = sellParkDao.selectSellParkInfoById(sellParkId);
            if(CollectionUtils.isNotEmpty(sellParkInfoVoList)){
                SellParkInfoVo sellParkInfoVo = sellParkInfoVoList.get(0);
                List<SellParkInfoVo> sellParkInfoVoRand = sellParkDao.selectSellParkRand();
                sellParkInfoVo.setLikes(sellParkInfoVoRand);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("查询成功");
                commonResult.setData(sellParkInfoVo);
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该出售园区不存在");
            }


        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<LeaseParkInfoVo> findLeaseParkDetail(Long leaseParkId) {
        CommonResult<LeaseParkInfoVo>  commonResult = new CommonResult<>();
        if(leaseParkId != null){
            List<LeaseParkInfoVo> leaseParkInfos = leaseParkDao.selectLeaseParkInfoById(leaseParkId);
            LeaseParkInfoVo leaseParkInfoVo = new LeaseParkInfoVo();
            if(leaseParkInfos != null && leaseParkInfos.size() > 0){
                leaseParkInfoVo = leaseParkInfos.get(0);
                List<LeaseParkInfoVo> randomLeaseParkList = leaseParkDao.selectLeaseParkInfoByRandom();
                leaseParkInfoVo.setLikes(randomLeaseParkList);
            }
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(leaseParkInfoVo);

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
    public List<LeaseParkInfoVo> findLeaseParkList(LeaseParkCondition leaseParkCondition) {
        return leaseParkDao.selectLeaseParkInfoList(leaseParkCondition);
    }

    @Override
    public CommonResult findAdminSellParkList(ParkAdminConditionVo parkAdminConditionVo) {
        CommonResult commonResult = new CommonResult();
        int total = sellParkDao.sellParkCount(parkAdminConditionVo);
        List<SellParkInfoDomain> sellParkInfoDomainList = sellParkDao.selectAdminSellParkInfoList(parkAdminConditionVo);
        JSONObject json = new JSONObject();
        json.put("total",total);
        json.put("sellParkList",sellParkInfoDomainList);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }

    @Override
    public CommonResult findAdminLeaseParkList(ParkAdminConditionVo parkAdminConditionVo) {
        CommonResult commonResult = new CommonResult();
        int total = leaseParkDao.leaseParkCount(parkAdminConditionVo);
        List<LeaseParkInfoVo> leaseParkInfoVoList = leaseParkDao.selectAdminLeaseParkInfoList(parkAdminConditionVo);
        JSONObject json = new JSONObject();
        json.put("total",total);
        json.put("leaseParkList",leaseParkInfoVoList);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }

    @Override
    public CommonResult deleteSellPark(Long sellParkId) {
        return null;
    }

    @Override
    public CommonResult deleteLeasePark(Long leaseParkId) {
        return null;
    }
}
