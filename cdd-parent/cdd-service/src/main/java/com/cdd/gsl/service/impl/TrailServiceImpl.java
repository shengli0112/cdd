package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.TrailInfoDao;
import com.cdd.gsl.dao.TrailInfoDomainMapper;
import com.cdd.gsl.domain.TrailInfoDomain;
import com.cdd.gsl.domain.TrailInfoDomainExample;
import com.cdd.gsl.service.TrailService;
import com.cdd.gsl.vo.TrailInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TrailServiceImpl implements TrailService {
    @Autowired
    private TrailInfoDomainMapper trailInfoDomainMapper;

    @Autowired
    private TrailInfoDao trailInfoDao;

    @Override
    public CommonResult addTrail(TrailInfoDomain trailInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(trailInfoDomain != null){
            trailInfoDomain.setCreateTs(new Date());
            trailInfoDomainMapper.insertSelective(trailInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
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
}
