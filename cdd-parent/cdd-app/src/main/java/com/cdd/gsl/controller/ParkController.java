package com.cdd.gsl.controller;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomain;
import com.cdd.gsl.service.ParkService;
import com.cdd.gsl.vo.CompanyVo;
import com.cdd.gsl.vo.LeaseParkInfoVo;
import com.cdd.gsl.vo.SellParkInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *  园区
 *  created:2019/01/07
 */
@RestController
@RequestMapping("park")
public class ParkController {

    private Logger logger = LoggerFactory.getLogger(ParkController.class);

    @Autowired
    private ParkService parkService;

    /**
     * 创建出售园区信息
     * @param sellParkInfoDomain
     * @return
     */
    @RequestMapping("createSellPark")
    public CommonResult createSellPark(@RequestBody SellParkInfoDomain sellParkInfoDomain){
        logger.info("ParkController createSellPark sellParkInfoDomain -{}",sellParkInfoDomain);
        CommonResult result = parkService.createSellPark(sellParkInfoDomain);
        return result;
    }

    /**
     * 更新出售园区信息
     * @param sellParkInfoDomain
     * @return
     */
    @RequestMapping("updateSellPark")
    public CommonResult updateSellPark(@RequestBody SellParkInfoDomain sellParkInfoDomain){
        logger.info("ParkController createSellPark sellParkInfoDomain -{}",sellParkInfoDomain);
        CommonResult result = parkService.updateSellPark(sellParkInfoDomain);
        return result;
    }

    /**
     * 删除出售园区信息
     * @param sellParkId
     * @return
     */
    @RequestMapping("deleteSellPark")
    public CommonResult deleteSellPark(Long sellParkId){
        logger.info("ParkController createSellPark sellParkId -{}",sellParkId);
        SellParkInfoDomain sellParkInfoDomain = new SellParkInfoDomain();
        sellParkInfoDomain.setId(sellParkId);
        sellParkInfoDomain.setStatus(0);
        CommonResult result = parkService.updateSellPark(sellParkInfoDomain);
        if(result.getFlag() == CddConstant.RESULT_SUCCESS_CODE){
            result.setMessage("删除成功");
        }
        return result;
    }

    /**
     * 创建出租园区信息
     * @param leaseParkInfoDomain
     * @return
     */
    @RequestMapping("createLeasePark")
    public CommonResult createLeasePark(@RequestBody LeaseParkInfoDomain leaseParkInfoDomain){
        logger.info("ParkController createLeasePark leaseParkInfoDomain -{}",leaseParkInfoDomain);
        CommonResult result = parkService.createLeasePark(leaseParkInfoDomain);
        return result;
    }

    /**
     * 更新出租园区信息
     * @param leaseParkInfoDomain
     * @return
     */
    @RequestMapping("updateLeasePark")
    public CommonResult updateLeasePark(@RequestBody LeaseParkInfoDomain leaseParkInfoDomain){
        logger.info("ParkController createLeasePark leaseParkInfoDomain -{}",leaseParkInfoDomain);
        CommonResult result = parkService.updateLeasePark(leaseParkInfoDomain);
        return result;
    }

    /**
     * 更新出租园区信息
     * @return
     */
    @RequestMapping("deleteLeasePark")
    public CommonResult deleteLeasePark(@RequestBody LeaseParkInfoDomain leaseParkInfo){
        logger.info("ParkController createLeasePark leaseParkId -{}",leaseParkInfo.getId());
        LeaseParkInfoDomain leaseParkInfoDomain = new LeaseParkInfoDomain();
        leaseParkInfoDomain.setId(leaseParkInfo.getId());
        leaseParkInfoDomain.setUserId(leaseParkInfo.getUserId());
        leaseParkInfoDomain.setStatus(0);
        CommonResult result = parkService.updateLeasePark(leaseParkInfoDomain);
        if(result.getFlag() == CddConstant.RESULT_SUCCESS_CODE){
            result.setMessage("删除成功");
        }
        return result;
    }

    /**
     * 获取出售园区列表
     * @return
     */
    @RequestMapping("findSellParkList")
    public CommonResult<List<SellParkInfoVo>> findSellParkList(String region,String price,String area,Long sellParkId){
        logger.info("ParkController findSellParkDetail sellParkId -{}",sellParkId);
        CommonResult<List<SellParkInfoVo>> commonResult = new CommonResult();
        List<SellParkInfoDomain> sellParkInfoDomainList = parkService.findSellParkList(region,price,area,sellParkId);
        List<SellParkInfoVo> sellParkInfoVos = new ArrayList<>();
        if(sellParkInfoDomainList != null && sellParkInfoDomainList.size() > 0){
            sellParkInfoDomainList.forEach(sellParkInfoDomain -> {
                SellParkInfoVo sellParkInfoVo = new SellParkInfoVo();
                BeanUtils.copyProperties(sellParkInfoDomain, sellParkInfoVo);
                sellParkInfoVos.add(sellParkInfoVo);
            });
        }
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(sellParkInfoVos);
        return commonResult;
    }

    /**
     * 获取出售园区详情
     * @param sellParkId
     * @return
     */
    @RequestMapping("findSellParkDetail")
    public CommonResult findSellParkDetail(Long sellParkId){
        logger.info("ParkController findSellParkDetail sellParkId -{}",sellParkId);
        CommonResult commonResult = parkService.findSellParkDetail(sellParkId);
        return commonResult;
    }

    /**
     * 获取出租园区列表
     * @return
     */
    @RequestMapping("findLeaseParkList")
    public CommonResult findLeaseParkList(String region,String price,String area,Long leaseParkId){
        logger.info("ParkController findLeaseParkDetail leaseParkId -{}",leaseParkId);
        CommonResult<List<LeaseParkInfoVo>> commonResult = new CommonResult();
        List<LeaseParkInfoDomain> leaseParkInfoDomainList = parkService.findLeaseParkList(region,price,area,leaseParkId);
        List<LeaseParkInfoVo> leaseParkInfoVos = new ArrayList<>();
        if(leaseParkInfoDomainList != null && leaseParkInfoDomainList.size() > 0){
            leaseParkInfoDomainList.forEach(leaseParkInfoDomain -> {
                LeaseParkInfoVo leaseParkInfoVo = new LeaseParkInfoVo();
                BeanUtils.copyProperties(leaseParkInfoDomain, leaseParkInfoVo);
                leaseParkInfoVos.add(leaseParkInfoVo);
            });
        }
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(leaseParkInfoVos);
        return commonResult;
    }

    /**
     * 获取出租园区详情
     * @param leaseParkId
     * @return
     */
    @RequestMapping("findLeaseParkDetail")
    public CommonResult findLeaseParkDetail(Long leaseParkId){
        logger.info("ParkController findLeaseParkDetail leaseParkId -{}",leaseParkId);
        CommonResult result = parkService.findLeaseParkDetail(leaseParkId);
        return result;
    }
}
