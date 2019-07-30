package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.HouseTopDomainMapper;
import com.cdd.gsl.dao.TopInfoDao;
import com.cdd.gsl.dao.TopInfoDomainMapper;
import com.cdd.gsl.dao.UserInfoDomainMapper;
import com.cdd.gsl.domain.HouseTopDomain;
import com.cdd.gsl.domain.TopInfoDomain;
import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.service.TopService;
import com.cdd.gsl.vo.HouseTopParamVo;
import com.cdd.gsl.vo.TopInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
                    houseTopDomainMapper.insert(houseTopDomain);
                    TopInfoDomain topInfoDomain = topInfoDomainMapper.selectByPrimaryKey(houseTopParamVo.getTopId());
                    UserInfoDomain user = new UserInfoDomain();
                    user.setId(userInfoDomain.getId());
                    user.setIntegral(userInfoDomain.getIntegral()-topInfoDomain.getIntegral());
                    userInfoDomainMapper.updateByPrimaryKeySelective(user);
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
