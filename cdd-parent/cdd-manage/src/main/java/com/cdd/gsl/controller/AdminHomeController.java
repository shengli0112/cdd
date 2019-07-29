package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.HouseAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.vo.ApplyBrokerConditionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("house")
public class AdminHomeController {

    private Logger logger = LoggerFactory.getLogger(AdminHomeController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private HouseService houseService;
    /**
     * 房源列表
     * @param houseConditionVo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findHouseList")
    public CommonResult findHouseList(HouseAdminConditionVo houseConditionVo) throws Exception {
        logger.info("AdminHomeController findHouseList start");
        return adminService.findHouseList(houseConditionVo);
    }

    @RequestMapping("deleteHouse")
    public CommonResult deleteHouse(@RequestBody JSONObject json){
        CommonResult commonResult = new CommonResult();

        if(json != null){
            HouseInfoDomain houseInfoDomain = new HouseInfoDomain();
            houseInfoDomain.setId(json.getLong("houseId"));
            houseInfoDomain.setStatus(0);
            houseInfoDomain.setUpdateTs(new Date());
            houseService.deleteHouse(houseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("删除成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("删除失败，参数不正确");
        }
        return commonResult;
    }

    /**
     * 恢复房源
     */
    @RequestMapping("recoverHouse")
    public CommonResult recoverHouse(@RequestBody JSONObject json){
        CommonResult commonResult = new CommonResult();

        if(json != null){
            HouseInfoDomain houseInfoDomain = new HouseInfoDomain();
            houseInfoDomain.setId(json.getLong("houseId"));
            houseInfoDomain.setStatus(1);
            houseInfoDomain.setUpdateTs(new Date());
            houseService.deleteHouse(houseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("恢复成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("恢复失败，参数不正确");
        }
        return commonResult;
    }

    /**
     * 修改房源
     */
    @RequestMapping("updateHouse")
    public CommonResult updateHouse(@RequestBody HouseInfoDomain houseInfoDomain){
        CommonResult commonResult = new CommonResult();
        if(houseInfoDomain != null){
            commonResult = houseService.updateHouse(houseInfoDomain);
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
    public CommonResult topHouse(@RequestBody JSONObject json){
        CommonResult commonResult = new CommonResult();

        if(json != null){
            HouseInfoDomain houseInfoDomain = new HouseInfoDomain();
            houseInfoDomain.setId(json.getLong("houseId"));
            houseInfoDomain.setStatus(1);
            houseService.deleteHouse(houseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("恢复成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("恢复失败，参数不正确");
        }
        return commonResult;
    }

    /**
     * 厂房 土地 仓库 数量
     */
    @RequestMapping("houseCount")
    public CommonResult houseCount(){
        CommonResult commonResult = houseService.houseCount();

        return commonResult;
    }
}
