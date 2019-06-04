package com.cdd.gsl.controller;

import com.cdd.gsl.admin.HouseAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.vo.ApplyBrokerConditionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResult deleteHouse(@RequestParam("houseId") Long houseId){
        CommonResult commonResult = new CommonResult();

        if(houseId != null){
            HouseInfoDomain houseInfoDomain = new HouseInfoDomain();
            houseInfoDomain.setId(houseId);
            houseInfoDomain.setStatus(0);
            houseService.deleteHouse(houseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("删除成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("删除失败，参数不正确");
        }
        return commonResult;
    }
}
