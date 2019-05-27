package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomain;
import com.cdd.gsl.vo.SellParkCondition;

import java.util.List;

public interface ParkService {
    CommonResult createSellPark(SellParkInfoDomain sellParkInfoDomain);

    CommonResult createLeasePark(LeaseParkInfoDomain leaseParkInfoDomain);

    CommonResult updateSellPark(SellParkInfoDomain sellParkInfoDomain);

    CommonResult updateLeasePark(LeaseParkInfoDomain leaseParkInfoDomain);

    CommonResult<SellParkInfoDomain> findSellParkDetail(Long sellParkId);

    CommonResult<LeaseParkInfoDomain> findLeaseParkDetail(Long leaseParkId);

    List<SellParkInfoDomain> findSellParkList(SellParkCondition sellParkCondition);

    List<LeaseParkInfoDomain> findLeaseParkList(String region,String price,String area,Long leaseParkId);
}
