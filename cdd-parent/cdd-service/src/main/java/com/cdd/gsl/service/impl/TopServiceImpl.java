package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.DateUtil;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.TopService;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.HouseTopParamVo;
import com.cdd.gsl.vo.TopInfoVo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class TopServiceImpl implements TopService {

    private Logger logger = LoggerFactory.getLogger(TopServiceImpl.class);

    @Autowired
    private UserInfoDomainMapper userInfoDomainMapper;

    @Autowired
    private HouseTopDomainMapper houseTopDomainMapper;

    @Autowired
    private TopInfoDomainMapper topInfoDomainMapper;

    @Autowired
    private TopInfoDao topInfoDao;

    @Autowired
    private HouseInfoDao houseInfoDao;

    @Autowired
    private HouseInfoDomainMapper houseInfoDomainMapper;

    @Autowired
    private EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    private EnterpriseInfoDomainMapper enterpriseInfoDomainMapper;

    @Autowired
    private LeaseParkInfoDomainMapper leaseParkInfoDomainMapper;

    @Autowired
    private SellParkInfoDomainMapper sellParkInfoDomainMapper;

    @Autowired
    private ConsumeRecordDomainMapper consumeRecordDomainMapper;

    @Override
    public CommonResult topAction(HouseTopParamVo houseTopParamVo) {
        logger.info("HouseServiceImpl topHouse houseId-{}，userId-{}",houseTopParamVo.getObjId(),houseTopParamVo.getUserId());
        CommonResult commonResult = new CommonResult();
        try {
            UserInfoDomain userInfoDomain = userInfoDomainMapper.selectByPrimaryKey(houseTopParamVo.getUserId());
            if(userInfoDomain != null){
                if(userInfoDomain.getIntegral()> CddConstant.PAY_INTERGAL_TOP){
                    HouseTopDomain houseTopDomain = new HouseTopDomain();
                    houseTopDomain.setObjId(houseTopParamVo.getObjId());
                    houseTopDomain.setUserId(houseTopParamVo.getUserId());
                    houseTopDomain.setTopId(houseTopParamVo.getTopId());
                    houseTopDomain.setStatus(1);
                    houseTopDomain.setType(houseTopParamVo.getType());
                    houseTopDomain.setCreateTs(new Date());
                    houseTopDomain.setUpdateTs(new Date());
                    houseTopDomainMapper.insert(houseTopDomain);
                    TopInfoDomain topInfoDomain = topInfoDomainMapper.selectByPrimaryKey(houseTopParamVo.getTopId());
                    UserInfoDomain user = new UserInfoDomain();
                    user.setId(userInfoDomain.getId());
                    user.setIntegral(userInfoDomain.getIntegral()-topInfoDomain.getIntegral());
                    userInfoDomainMapper.updateByPrimaryKeySelective(user);
                    ConsumeRecordDomain consumeRecordDomain = new ConsumeRecordDomain();
                    String title = "";
                    if(houseTopParamVo.getType().equals(CddConstant.CONSUME_RECORD_TYPE_HOUSE)){
//                        HouseInfoDetailVo houseInfoDetailVo = houseInfoDao.selectHouseInfoById(houseTopParamVo.getObjId());
                        HouseInfoDomain houseInfoDomain = new HouseInfoDomain();
                        houseInfoDomain.setId(houseTopParamVo.getObjId());
                        houseInfoDomain.setTop(1);
                        houseInfoDomainMapper.updateByPrimaryKeySelective(houseInfoDomain);
                        title = CddConstant.TOP_HOUSE_TITLE;
                    }else if(houseTopParamVo.getType().equals(CddConstant.CONSUME_RECORD_TYPE_ENTERPRISE)){
                        /*EnterpriseInfoDomainExample enterpriseInfoDomainExample = new EnterpriseInfoDomainExample();
                        enterpriseInfoDomainExample.createCriteria().andIdEqualTo(houseTopParamVo.getObjId()).andStatusEqualTo(1);
                        List<EnterpriseInfoDomain> enterpriseInfoDomainList = enterpriseInfoDomainMapper.selectByExample(enterpriseInfoDomainExample);
                        if(CollectionUtils.isNotEmpty(enterpriseInfoDomainList)){
                            title = enterpriseInfoDomainList.get(0).getTitle();
                        }*/
                        EnterpriseInfoDomain enterpriseInfoDomain = new EnterpriseInfoDomain();
                        enterpriseInfoDomain.setId(houseTopParamVo.getObjId());
                        enterpriseInfoDomain.setTop(1);
                        enterpriseInfoDomainMapper.updateByPrimaryKeySelective(enterpriseInfoDomain);
                        title = CddConstant.TOP_ENTERPRISE_TITLE;
                    }else if(houseTopParamVo.getType().equals(CddConstant.CONSUME_RECORD_TYPE_LEASE_PARK)){
                        /*LeaseParkInfoDomainExample leaseParkInfoDomainExample = new LeaseParkInfoDomainExample();
                        leaseParkInfoDomainExample.createCriteria().andIdEqualTo(houseTopParamVo.getObjId()).andStatusEqualTo(1);
                        List<LeaseParkInfoDomain> leaseParkInfoDomainList = leaseParkInfoDomainMapper.selectByExample(leaseParkInfoDomainExample);
                        if(CollectionUtils.isNotEmpty(leaseParkInfoDomainList)){
                            title = leaseParkInfoDomainList.get(0).getParkName();
                        }*/
                        LeaseParkInfoDomain leaseParkInfoDomain = new LeaseParkInfoDomain();
                        leaseParkInfoDomain.setId(houseTopParamVo.getObjId());
                        leaseParkInfoDomain.setTop(1);
                        leaseParkInfoDomainMapper.updateByPrimaryKeySelective(leaseParkInfoDomain);
                        title = CddConstant.TOP_PARK_TITLE;
                    }else if(houseTopParamVo.getType().equals(CddConstant.CONSUME_RECORD_TYPE_SELL_PARK)){
                        /*SellParkInfoDomainExample sellParkInfoDomainExample = new SellParkInfoDomainExample();
                        sellParkInfoDomainExample.createCriteria().andIdEqualTo(houseTopParamVo.getObjId()).andStatusEqualTo(1);
                        List<SellParkInfoDomain> sellParkInfoDomainList = sellParkInfoDomainMapper.selectByExample(sellParkInfoDomainExample);
                        if(CollectionUtils.isNotEmpty(sellParkInfoDomainList)){
                            title = sellParkInfoDomainList.get(0).getParkName();
                        }*/
                        SellParkInfoDomain sellParkInfoDomain = new SellParkInfoDomain();
                        sellParkInfoDomain.setId(houseTopParamVo.getObjId());
                        sellParkInfoDomain.setTop(1);
                        sellParkInfoDomainMapper.updateByPrimaryKeySelective(sellParkInfoDomain);
                        title = CddConstant.TOP_PARK_TITLE;
                    }
                    consumeRecordDomain.setTitle(title);
                    consumeRecordDomain.setUserId(userInfoDomain.getId());
                    consumeRecordDomain.setAction(CddConstant.CONSUME_RECORD_CONSUME);
                    consumeRecordDomain.setIntegral(topInfoDomain.getIntegral());
                    String deadline = DateUtil.getSomeDay(new Date(),topInfoDomain.getDay());
                    consumeRecordDomain.setDeadline(deadline);
                    consumeRecordDomain.setType(CddConstant.CONSUME_RECORD_TYPE_TOP);
                    consumeRecordDomainMapper.insertSelective(consumeRecordDomain);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setData("置顶成功");
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setData("多多币不足，请充值");
                }
            }
        }catch (Exception e){
            logger.error("HouseServiceImpl topHouse error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setData("系统异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult topList() {
        CommonResult commonResult = new CommonResult();
        try{
            List<TopInfoVo> topInfoVoList = topInfoDao.selectTopInfoList();
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(topInfoVoList);
        }catch (Exception e){
            logger.error("TopServiceImpl topList error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }

        return commonResult;
    }
}
