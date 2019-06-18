package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.HouseAdminConditionVo;
import com.cdd.gsl.admin.ParkAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.service.ParkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("park")
public class AdminParkController {

    private Logger logger = LoggerFactory.getLogger(AdminParkController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private ParkService parkService;
    /**
     * 出售园区列表
     * @param parkConditionVo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findSellParkList")
    public CommonResult findSellParkList(ParkAdminConditionVo parkConditionVo) throws Exception {
        logger.info("AdminParkController findParkList start");
        return parkService.findAdminSellParkList(parkConditionVo);
    }

    /**
     * 出售园区列表
     * @param parkConditionVo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findLeaseParkList")
    public CommonResult findLeaseParkList(ParkAdminConditionVo parkConditionVo) throws Exception {
        logger.info("AdminParkController findParkList start");
        return parkService.findAdminLeaseParkList(parkConditionVo);
    }

    @RequestMapping("deleteSell")
    public CommonResult deleteSellPark(@RequestBody JSONObject json){
        CommonResult commonResult = new CommonResult();

        if(json != null){
            SellParkInfoDomain sellParkInfoDomain = new SellParkInfoDomain();
            sellParkInfoDomain.setId(json.getLong("sellParkId"));
            sellParkInfoDomain.setStatus(0);
            parkService.updateSellPark(sellParkInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("删除成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("删除失败，参数不正确");
        }
        return commonResult;
    }

    @RequestMapping("deleteLease")
    public CommonResult deleteLeasPark(@RequestBody JSONObject json){
        CommonResult commonResult = new CommonResult();

        if(json != null){
            LeaseParkInfoDomain leaseParkInfoDomain = new LeaseParkInfoDomain();
            leaseParkInfoDomain.setId(json.getLong("leaseParkId"));
            leaseParkInfoDomain.setStatus(0);
            parkService.updateLeasePark(leaseParkInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("删除成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("删除失败，参数不正确");
        }
        return commonResult;
    }

    /**
     * 恢复出售园区
     */
    @RequestMapping("recoverSell")
    public CommonResult recoverSellPark(@RequestBody JSONObject json){
        CommonResult commonResult = new CommonResult();

        if(json != null){
            SellParkInfoDomain sellParkInfoDomain = new SellParkInfoDomain();
            sellParkInfoDomain.setId(json.getLong("sellParkId"));
            sellParkInfoDomain.setStatus(1);
            parkService.updateSellPark(sellParkInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("恢复成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("恢复失败，参数不正确");
        }
        return commonResult;
    }

    @RequestMapping("recoverLease")
    public CommonResult recoverLeasPark(@RequestBody JSONObject json){
        CommonResult commonResult = new CommonResult();

        if(json != null){
            LeaseParkInfoDomain leaseParkInfoDomain = new LeaseParkInfoDomain();
            leaseParkInfoDomain.setId(json.getLong("leaseParkId"));
            leaseParkInfoDomain.setStatus(1);
            parkService.updateLeasePark(leaseParkInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("恢复成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("删除失败，参数不正确");
        }
        return commonResult;
    }

    /**
     * 修改出售园区
     */
    @RequestMapping("updateSellPark")
    public CommonResult updateSellPark(@RequestBody SellParkInfoDomain sellParkInfoDomain){
        CommonResult commonResult = new CommonResult();
        if(sellParkInfoDomain != null){
            commonResult = parkService.updateSellPark(sellParkInfoDomain);
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("更新失败");
        }
        return commonResult;
    }

    /**
     * 修改出租园区
     */
    @RequestMapping("updateLeasePark")
    public CommonResult updateLeasePark(@RequestBody LeaseParkInfoDomain leaseParkInfoDomain){
        CommonResult commonResult = new CommonResult();
        if(leaseParkInfoDomain != null){
            commonResult = parkService.updateLeasePark(leaseParkInfoDomain);
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("更新失败");
        }
        return commonResult;
    }

    /**
     * 置顶
     */
    @RequestMapping("topHouse")
    public CommonResult topHouse(@RequestParam("houseId") Long houseId){
        CommonResult commonResult = new CommonResult();

        if(houseId != null){
            HouseInfoDomain houseInfoDomain = new HouseInfoDomain();
            houseInfoDomain.setId(houseId);
            houseInfoDomain.setStatus(1);
//            houseService.deleteHouse(houseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("恢复成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("恢复失败，参数不正确");
        }
        return commonResult;
    }
}
