package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.TrailInfoDomainMapper;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.service.TrailService;
import com.cdd.gsl.vo.HouseConditionVo;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.HouseInfoDomainVo;
import com.cdd.gsl.vo.HouseTopParamVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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

    @Autowired
    private TrailService trailService;

    private Logger logger = LoggerFactory.getLogger(HouseController.class);

    @RequestMapping("addHouse")
    public CommonResult addHouse(@RequestBody HouseInfoDomain houseInfoDomain){
        return houseService.addHouse(houseInfoDomain);
    }

    @RequestMapping("addTrail")
    public CommonResult addTrail(@RequestBody TrailInfoDomain trailInfoDomain){
        CommonResult commonResult = new CommonResult();
        if(trailInfoDomain != null){
            commonResult = trailService.addTrail(trailInfoDomain);
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("添加失败");
        }
        return commonResult;
    }

    @RequestMapping("findTrailList")
    public CommonResult findTrailList(Long houseId){

        return trailService.findTrailList(houseId);
    }

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

    @RequestMapping("findHouseInfoDetail")
    public CommonResult<HouseInfoDetailVo> findHouseInfoDetail(Long houseId){
        logger.info("HouseController findHouseInfoDetail houseId-{}",houseId);
        CommonResult<HouseInfoDetailVo> commonResult = new CommonResult<>();

        if(houseId != null){
            long start = System.currentTimeMillis();
            HouseInfoDetailVo houseInfoDomain = houseService.findHouseInfoById(houseId);
            logger.info("HouseController findHouseInfoDetail ms --{}",(System.currentTimeMillis() - start));
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
    public CommonResult findHouseInfoList(HouseConditionVo houseConditionVo){
        logger.info("HouseController findHouseInfoList");
        CommonResult commonResult = new CommonResult();

        if(houseConditionVo != null){
            JSONObject data = houseService.findHouseInfoList(houseConditionVo);
            commonResult.setFlag(1);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);

        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("查询失败，参数不能为空");
        }
        return commonResult;
    }

    /**
     * 首页房源列表
     * @param houseConditionVo
     * @return
     */
    @RequestMapping("findHomeHouseInfoList")
    public CommonResult findHomeHouseInfoList(HouseConditionVo houseConditionVo){
        logger.info("HouseController findHomeHouseInfoList");
        CommonResult commonResult = new CommonResult();

        if(houseConditionVo != null){
            JSONObject data = houseService.findHomeHouseInfoList(houseConditionVo);
            commonResult.setFlag(1);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);

        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("查询失败，参数不能为空");
        }
        return commonResult;
    }

    @RequestMapping("findUserHouseList")
    public CommonResult<List<HouseInfoDomainVo>> findUserHouseList(HouseConditionVo houseConditionVo){
        logger.info("HouseController findUserHouseList");
        CommonResult<List<HouseInfoDomainVo>> commonResult = new CommonResult<>();

        if(houseConditionVo != null){
            List<HouseInfoDomainVo> data = houseService.selectUserHouseInfoListByCondition(houseConditionVo);
            commonResult.setFlag(1);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);

        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("查询失败，参数不能为空");
        }
        return commonResult;
    }

    @RequestMapping("findCompanyHouseList")
    public CommonResult<List<HouseInfoDomainVo>> findCompanyHouseList(HouseConditionVo houseConditionVo){
        logger.info("HouseController findCompanyHouseList");
        CommonResult<List<HouseInfoDomainVo>> commonResult = new CommonResult<>();

        if(houseConditionVo != null){
            List<HouseInfoDomainVo> houseInfoDomainList = houseService.selectCompanyHouseInfoListByCondition(houseConditionVo);
            commonResult.setFlag(1);
            commonResult.setMessage("查询成功");
            commonResult.setData(houseInfoDomainList);

        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("查询失败，参数不能为空");
        }
        return commonResult;
    }

    @RequestMapping("informHouseInfo")
    public CommonResult informHouseInfo(@RequestBody InformHouseRecordDomain informHouseRecordDomain){
       return houseService.informHouseInfo(informHouseRecordDomain);
    }

    @RequestMapping("switchHouse")
    public CommonResult switchHouse(@RequestParam("fromUserId") Long fromUserId,@RequestParam("toUserId")Long toUserId){
        CommonResult commonResult = new CommonResult();
        if(fromUserId != null && toUserId != null){
            commonResult = houseService.switchHouse(fromUserId,toUserId);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

}
