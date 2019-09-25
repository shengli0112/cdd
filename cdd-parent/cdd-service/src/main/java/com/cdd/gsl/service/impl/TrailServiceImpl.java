package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.TrailService;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.TrailInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TrailServiceImpl implements TrailService {
    @Autowired
    private TrailInfoDomainMapper trailInfoDomainMapper;

    @Autowired
    private TrailInfoDao trailInfoDao;

    @Autowired
    private HouseInfoDao houseInfoDao;

    @Autowired
    private HouseInfoDomainMapper houseInfoDomainMapper;

    @Autowired
    private FollowTrailInfoMapper followTrailInfoMapper;

    @Override
    public CommonResult addTrail(TrailInfoDomain trailInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(trailInfoDomain != null){
            trailInfoDomainMapper.insertSelective(trailInfoDomain);
            FollowTrailInfoExample followTrailInfoExample = new FollowTrailInfoExample();
            followTrailInfoExample.createCriteria().andUserIdEqualTo(trailInfoDomain.getUserId())
                    .andHouseIdEqualTo(trailInfoDomain.getHouseId());
            followTrailInfoMapper.deleteByExample(followTrailInfoExample);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult addFollowTrail(FollowTrailInfo followTrailInfo) {
        CommonResult commonResult = new CommonResult();
        if(followTrailInfo != null){
            FollowTrailInfoExample followTrailInfoExample = new FollowTrailInfoExample();
            followTrailInfoExample.createCriteria().andUserIdEqualTo(followTrailInfo.getUserId()).andHouseIdEqualTo(followTrailInfo.getHouseId());
            List<FollowTrailInfo> followTrailInfoList = followTrailInfoMapper.selectByExample(followTrailInfoExample);
            if(followTrailInfoList != null && followTrailInfoList.size() > 0){
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("已存在相应数据");
            }else{
                followTrailInfoMapper.insertSelective(followTrailInfo);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("添加成功");
            }

        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult findTrailList(Long houseId) {
        CommonResult commonResult = new CommonResult();
        if(houseId != null){
            List<TrailInfoVo> trailInfoVos = trailInfoDao.findTrailList(houseId);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(trailInfoVos);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult findFollowTrailList(Long userId) {
        CommonResult commonResult = new CommonResult();
        if(userId != null){
            FollowTrailInfoExample followTrailInfoExample = new FollowTrailInfoExample();
            followTrailInfoExample.createCriteria().andUserIdEqualTo(userId);
            List<FollowTrailInfo> followTrailInfos = followTrailInfoMapper.selectByExample(followTrailInfoExample);
            List<JSONObject> data = new ArrayList<>();
            followTrailInfos.forEach(followTrailInfo -> {
                JSONObject dataJson = new JSONObject();
                Long houseId = followTrailInfo.getHouseId();
                dataJson.put("houseId",houseId);
                dataJson.put("userId",followTrailInfo.getUserId());
                HouseInfoDomain houseInfoDomain = houseInfoDomainMapper.selectByPrimaryKey(houseId);
                if(houseInfoDomain.getHouseUseType().intValue() == 1 || houseInfoDomain.getHouseUseType().intValue() == 2 ){
                    dataJson.put("houseUseType",1);//客源
                }else if(houseInfoDomain.getHouseUseType().intValue() == 3 || houseInfoDomain.getHouseUseType().intValue() == 4){
                    dataJson.put("houseUseType",2);//房源
                }
                data.add(dataJson);
            });
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }
}
