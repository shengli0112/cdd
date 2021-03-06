package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.EntrustInfoDomain;
import com.cdd.gsl.service.EntrustService;
import com.cdd.gsl.vo.EntrustConditionVo;
import com.cdd.gsl.vo.EntrustInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  托管
 *  created:2018/12/28
 */
@RestController
@RequestMapping("entrust")
public class EntrustController {

    @Autowired
    private EntrustService entrustService;

    @RequestMapping("createEntrust")
    public CommonResult createEntrust(@RequestBody EntrustInfoDomain entrustInfoDomain){
        return entrustService.createEntrust(entrustInfoDomain);
    }

    @RequestMapping("updateEntrust")
    public CommonResult updateEntrust(@RequestBody EntrustInfoDomain entrustInfoDomain){
        return entrustService.updateEntrust(entrustInfoDomain);
    }

    @RequestMapping("deleteEntrust")
    public CommonResult deleteEntrust(Long entrustId){
        EntrustInfoDomain entrustInfoDomain = new EntrustInfoDomain();
        entrustInfoDomain.setId(entrustId);
        entrustInfoDomain.setStatus(0);
        return entrustService.updateEntrust(entrustInfoDomain);
    }

    @RequestMapping("findEntrustDetail")
    public CommonResult findEntrustDetail(Long entrustId){
        return entrustService.findEntrustDetail(entrustId);
    }

    /**
     * 个人房源列表
     */
    @RequestMapping("findEntrustInfoList")
    public CommonResult<List<EntrustInfoVo>> findEntrustInfoList(EntrustConditionVo entrustConditionVo){
        //TODO 有问题条件没有判断清楚
        return entrustService.findEntrustInfoList(entrustConditionVo);
    }

    /**
     * 个人发布的委托列表
     */
    @RequestMapping("findEntrustInfoByUserId")
    public CommonResult<List<EntrustInfoVo>> findEntrustInfoByUserId(EntrustConditionVo entrustConditionVo){
        //TODO 有问题条件没有判断清楚
        return entrustService.findEntrustInfoByUserId(entrustConditionVo);
    }


    /**
     * 所有人都可以查看托管信息
     */
    @RequestMapping("findEntrustList")
    public CommonResult<List<EntrustInfoVo>> findEntrustList(EntrustConditionVo entrustConditionVo){
        return entrustService.findEntrustList(entrustConditionVo);
    }


}
