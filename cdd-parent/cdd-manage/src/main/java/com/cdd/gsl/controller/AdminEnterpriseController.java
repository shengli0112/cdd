package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.HouseAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.EnterpriseInfoDomain;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.service.EnterpriseService;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.vo.EnterpriseAdminConditionVo;
import com.cdd.gsl.vo.EnterpriseConditionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enterprise")
public class AdminEnterpriseController {

    private Logger logger = LoggerFactory.getLogger(AdminEnterpriseController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private EnterpriseService enterpriseService;
    /**
     * 房源列表
     * @param enterpriseConditionVo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findEnterpriseList")
    public CommonResult findEnterpriseList(EnterpriseAdminConditionVo enterpriseConditionVo) throws Exception {
        logger.info("AdminEnterpriseController findEnterpriseList start");
        return enterpriseService.findEnterpriseAdminList(enterpriseConditionVo);
    }

    @RequestMapping("deleteEnterprise")
    public CommonResult deleteHouse(@RequestBody JSONObject json){
        CommonResult commonResult = new CommonResult();

        if(json != null){
            EnterpriseInfoDomain enterpriseInfoDomain = new EnterpriseInfoDomain();
            enterpriseInfoDomain.setId(json.getLong("enterpriseId"));
            enterpriseInfoDomain.setStatus(0);
            enterpriseService.updateEnterprise(enterpriseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("删除成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("删除失败，参数不正确");
        }
        return commonResult;
    }

    /**
     * 恢复企业
     */
    @RequestMapping("recoverEnterprise")
    public CommonResult recoverHouse(@RequestBody JSONObject json){
        CommonResult commonResult = new CommonResult();

        if(json != null){
            EnterpriseInfoDomain enterpriseInfoDomain = new EnterpriseInfoDomain();
            enterpriseInfoDomain.setId(json.getLong("enterpriseId"));
            enterpriseInfoDomain.setStatus(1);
            enterpriseService.updateEnterprise(enterpriseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("恢复成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("恢复失败，参数不正确");
        }
        return commonResult;
    }

    /**
     * 修改企业圈
     */
    @RequestMapping("updateEnterprise")
    public CommonResult updateHouse(@RequestBody EnterpriseInfoDomain enterpriseInfoDomain){
        CommonResult commonResult = new CommonResult();
        if(enterpriseInfoDomain != null){
            commonResult = enterpriseService.updateEnterprise(enterpriseInfoDomain);
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
