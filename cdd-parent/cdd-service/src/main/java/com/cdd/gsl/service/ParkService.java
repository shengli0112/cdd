package com.cdd.gsl.service;

import com.cdd.gsl.admin.ParkAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomain;
import com.cdd.gsl.vo.LeaseParkCondition;
import com.cdd.gsl.vo.LeaseParkInfoVo;
import com.cdd.gsl.vo.SellParkCondition;

import java.util.List;

public interface ParkService {
    CommonResult createSellPark(SellParkInfoDomain sellParkInfoDomain);

    CommonResult createLeasePark(LeaseParkInfoDomain leaseParkInfoDomain);

    CommonResult updateSellPark(SellParkInfoDomain sellParkInfoDomain);

    CommonResult updateLeasePark(LeaseParkInfoDomain leaseParkInfoDomain);

    CommonResult findSellParkDetail(Long sellParkId);

    CommonResult<LeaseParkInfoVo> findLeaseParkDetail(Long leaseParkId);

    List<SellParkInfoDomain> findSellParkList(SellParkCondition sellParkCondition);

    List<LeaseParkInfoVo> findLeaseParkList(LeaseParkCondition leaseParkCondition);

    /******************************管理员****************************************/
    CommonResult findAdminSellParkList(ParkAdminConditionVo parkAdminConditionVo);

    CommonResult findAdminLeaseParkList(ParkAdminConditionVo parkAdminConditionVo);

    CommonResult deleteSellPark(Long sellParkId);

    CommonResult deleteLeasePark(Long leaseParkId);
}
