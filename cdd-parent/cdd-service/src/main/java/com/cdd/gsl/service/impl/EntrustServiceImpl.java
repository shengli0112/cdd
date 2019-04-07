package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
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

    @Autowired
    private MessageInfoDomainMapper messageInfoDomainMapper;

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
                int size = 0;
                if(houseInfoDomainList.size() >= 3){
                    size = 3;
                }else if (houseInfoDomainList.size() < 3){
                    size = houseInfoDomainList.size();
                }else{
                    size = 3;
                }
                if(size == 3){
                    for(int i=0;i<size;i++){
                        HouseInfoDomain houseInfoDomain = null;
                        if(houseInfoDomainList.size() > 3){
                            houseInfoDomain = houseInfoDomainList.get((int)(Math.random() * 3));
                       }else{
                           houseInfoDomain = houseInfoDomainList.get(i);
                       }
                        EntrustUserMappingDomain entrustUserMappingDomain = new EntrustUserMappingDomain();
                        entrustUserMappingDomain.setEntrustId(entrustInfoDomain.getId());
                        entrustUserMappingDomain.setUserId(houseInfoDomain.getUserId());
                        entrustUserMappingDomainMapper.insert(entrustUserMappingDomain);
                        MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
                        messageInfoDomain.setUserId(houseInfoDomain.getUserId());
                        messageInfoDomain.setEntrustId(entrustInfoDomain.getId());
                        messageInfoDomain.setHouseId(houseInfoDomain.getId());
                        messageInfoDomain.setMessage("匹配成功，点击查看");
                        messageInfoDomainMapper.insertSelective(messageInfoDomain);
                    }
                }else{
                    for(int i=0;i<size;i++){
                        HouseInfoDomain  houseInfoDomain = houseInfoDomainList.get(i);
                        EntrustUserMappingDomain entrustUserMappingDomain = new EntrustUserMappingDomain();
                        entrustUserMappingDomain.setEntrustId(entrustInfoDomain.getId());
                        entrustUserMappingDomain.setUserId(houseInfoDomain.getUserId());
                        entrustUserMappingDomainMapper.insert(entrustUserMappingDomain);
                        MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
                        messageInfoDomain.setUserId(houseInfoDomain.getUserId());
                        messageInfoDomain.setEntrustId(entrustInfoDomain.getId());
                        messageInfoDomain.setHouseId(houseInfoDomain.getId());
                        messageInfoDomainMapper.insertSelective(messageInfoDomain);
                    }

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

    @Override
    public CommonResult<List<EntrustInfoVo>> findEntrustList(EntrustConditionVo entrustConditionVo) {
        CommonResult<List<EntrustInfoVo>> commonResult = new CommonResult<>();
        // && entrustConditionVo.getEntrustType() != null
        List<EntrustInfoVo> entrustInfoVos = entrustInfoDao.findEntrustInfo(entrustConditionVo);
        commonResult.setData(entrustInfoVos);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");

        return commonResult;
    }

    public static void main(String[] args){
        System.out.println((int)(Math.random() * 3));
    }
}
