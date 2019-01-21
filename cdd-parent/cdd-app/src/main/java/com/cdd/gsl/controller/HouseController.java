package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.vo.HouseConditionVo;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.HouseInfoDomainVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  求租房屋
 *  created:2018/12/28
 */
@RestController
@RequestMapping("house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    private Logger logger = LoggerFactory.getLogger(HouseController.class);

    @RequestMapping("addHouse")
    public CommonResult addHouse(HouseInfoDomain houseInfoDomain){
        CommonResult commonResult = new CommonResult();
        if(houseInfoDomain != null){
            houseService.addHouse(houseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("添加失败");
        }
        return commonResult;
    }

    @RequestMapping("updateHouse")
    public CommonResult updateHouse(HouseInfoDomain houseInfoDomain){
        CommonResult commonResult = new CommonResult();
        if(houseInfoDomain != null){
            houseService.updateHouse(houseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("更新成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("更新失败");
        }
        return commonResult;
    }

    @RequestMapping("deleteHouse")
    public CommonResult deleteHouse(Long houseId){
        CommonResult commonResult = new CommonResult();

        if(houseId != null){
            HouseInfoDomain houseInfoDomain = new HouseInfoDomain();
            houseInfoDomain.setId(houseId);
            houseInfoDomain.setStatus(0);
            houseService.updateHouse(houseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("删除成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("删除失败，参数不正确");
        }
        return commonResult;
    }

    @RequestMapping("findHouseInfoDetail")
    public CommonResult<HouseInfoDetailVo> findHouseInfoDetail(Long houseId){
        logger.info("HouseController findHouseInfoDetail houseId-{}",houseId);
        CommonResult<HouseInfoDetailVo> commonResult = new CommonResult<>();

        if(houseId != null){
            HouseInfoDetailVo houseInfoDomain = houseService.findHouseInfoById(houseId);
            commonResult.setFlag(1);
            commonResult.setMessage("查询成功");
            commonResult.setData(houseInfoDomain);

        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("查询失败，参数不正确");
        }
        return commonResult;
    }

    @RequestMapping("findHouseInfoList")
    public CommonResult<List<HouseInfoDomainVo>> findHouseInfoList(HouseConditionVo houseConditionVo){
        logger.info("HouseController findHouseInfoList");
        CommonResult<List<HouseInfoDomainVo>> commonResult = new CommonResult<>();

        if(houseConditionVo != null){
            List<HouseInfoDomainVo> houseInfoDomainList = houseService.findHouseInfoList(houseConditionVo);
            commonResult.setFlag(1);
            commonResult.setMessage("查询成功");
            commonResult.setData(houseInfoDomainList);

        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("查询失败，参数不能为空");
        }
        return commonResult;
    }

}
