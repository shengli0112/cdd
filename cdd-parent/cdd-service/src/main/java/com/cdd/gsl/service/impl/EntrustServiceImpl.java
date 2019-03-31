package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.EntrustInfoDao;
import com.cdd.gsl.dao.EntrustInfoDomainMapper;
import com.cdd.gsl.dao.EntrustUserMappingDomainMapper;
import com.cdd.gsl.dao.HouseInfoDomainMapper;
import com.cdd.gsl.domain.EntrustInfoDomain;
import com.cdd.gsl.domain.EntrustUserMappingDomain;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.domain.HouseInfoDomainExample;
import com.cdd.gsl.service.EntrustService;
import com.cdd.gsl.vo.EntrustConditionVo;
import com.cdd.gsl.vo.EntrustInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrustServiceImpl implements EntrustService {

    @Autowired
    private EntrustInfoDomainMapper entrustInfoDomainMapper;

    @Autowired
    private EntrustInfoDao entrustInfoDao;

    @Autowired
    private HouseInfoDomainMapper houseInfoDomainMapper;

    @Autowired
    private EntrustUserMappingDomainMapper entrustUserMappingDomainMapper;

    @Override
    public CommonResult createEntrust(EntrustInfoDomain entrustInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(entrustInfoDomain != null){
            entrustInfoDomainMapper.insertSelective(entrustInfoDomain);
            Integer area = entrustInfoDomain.getArea();
            Double fromArea =  area * 0.8;
            Double toArea = area * 1.2;
            Integer entrustUseType = entrustInfoDomain.getEntrustUseType();
            Integer houseUseType = 0;
            if(entrustUseType == 1){
                houseUseType = 3;
            }else if(entrustUseType == 2){
                houseUseType = 4;
            }else if(entrustUseType == 3){
                houseUseType = 1;
            }else if(entrustUseType == 4){
                houseUseType = 2;
            }
            HouseInfoDomainExample houseInfoDomainExample = new HouseInfoDomainExample();
            houseInfoDomainExample.createCriteria().andAreaBetween(fromArea.intValue(),toArea.intValue())
                    .andCityEqualTo(entrustInfoDomain.getCity()).andCountyEqualTo(entrustInfoDomain.getCounty())
                    .andHouseTypeEqualTo(entrustInfoDomain.getEntrustType()).andHouseUseTypeEqualTo(houseUseType);
            List<HouseInfoDomain> houseInfoDomainList = houseInfoDomainMapper.selectByExample(houseInfoDomainExample);
            if(houseInfoDomainList != null && houseInfoDomainList.size() > 0){
                for(int i=0;i<3;i++){
                    HouseInfoDomain houseInfoDomain = houseInfoDomainList.get((int)Math.random() * houseInfoDomainList.size());
                    EntrustUserMappingDomain entrustUserMappingDomain = new EntrustUserMappingDomain();
                    entrustUserMappingDomain.setEntrustId(entrustInfoDomain.getId());
                    entrustUserMappingDomain.setUserId(houseInfoDomain.getUserId());
                    entrustUserMappingDomainMapper.insert(entrustUserMappingDomain);
                }
            }

            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateEntrust(EntrustInfoDomain entrustInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(entrustInfoDomain != null){
            entrustInfoDomainMapper.updateByPrimaryKeySelective(entrustInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<List<EntrustInfoVo>> findEntrustInfoList(EntrustConditionVo entrustConditionVo) {
        CommonResult<List<EntrustInfoVo>> commonResult = new CommonResult<>();
        // && entrustConditionVo.getEntrustType() != null
        if(entrustConditionVo.getUserId() != null){
            List<EntrustInfoVo> entrustInfoVos = entrustInfoDao.findEntrustInfoByUserId(entrustConditionVo);
            commonResult.setData(entrustInfoVos);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }
}
