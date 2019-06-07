package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.DateUtil;
import com.cdd.gsl.dao.CurrencyDao;
import com.cdd.gsl.dao.CurrencyInfoDomainMapper;
import com.cdd.gsl.dao.UserCurrencyDao;
import com.cdd.gsl.dao.UserCurrencyMappingDomainMapper;
import com.cdd.gsl.service.CurrencyService;
import com.cdd.gsl.vo.CurrencyVo;
import com.cdd.gsl.vo.UserCurrencyVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private UserCurrencyMappingDomainMapper userCurrencyMappingDomainMapper;

    @Autowired
    private UserCurrencyDao userCurrencyDao;

    @Override
    public CommonResult currencyList() {
        List<CurrencyVo> currencyVoList = currencyDao.currencyList();
        CommonResult commonResult = new CommonResult();
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(currencyVoList);
        return commonResult;
    }

    public CommonResult isPublish(Long userId){
        CommonResult commonResult = new CommonResult();
        List<UserCurrencyVo> userCurrencyVos = userCurrencyDao.findUserCurrencyByUserId(userId);
        if(userCurrencyVos != null && userCurrencyVos.size() > 0){
            UserCurrencyVo userCurrencyVo = userCurrencyVos.get(0);
            List<CurrencyVo> currencyVos = currencyDao.currencyListById(userCurrencyVo.getId());
            if(CollectionUtils.isNotEmpty(currencyVos)){
                CurrencyVo currencyVo = currencyVos.get(0);
                int days = DateUtil.differentDaysByMillisecond(new Date(),userCurrencyVo.getCreateTs());
                if(days <= currencyVo.getMonth()*30){
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("可以发布");
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("发布已到期请充值");
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该币不存在");
            }
        }
        return commonResult;
    }
}
