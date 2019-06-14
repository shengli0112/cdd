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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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

    @Autowired
    private CheckPhoneDomainMapper checkPhoneDomainMapper;

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
            //.andAreaBetween(fromArea.intValue(),toArea.intValue())
            houseInfoDomainExample.createCriteria().andStatusEqualTo(1)
                    .andCityEqualTo(entrustInfoDomain.getCity()).andCountyEqualTo(entrustInfoDomain.getCounty()).andTownEqualTo(entrustInfoDomain.getTown())
                    .andHouseTypeEqualTo(entrustInfoDomain.getEntrustType()).andHouseUseTypeEqualTo(houseUseType);
            List<HouseInfoDomain> houseInfoDomainList = houseInfoDomainMapper.selectByExample(houseInfoDomainExample);
            if(houseInfoDomainList != null && houseInfoDomainList.size() > 0){
                Map<Long,List<HouseInfoDomain>> map = houseInfoDomainList.stream().collect(Collectors.groupingBy(HouseInfoDomain::getUserId));
                for(Long userId:map.keySet()){
                    HouseInfoDomain houseInfoDomain = map.get(userId).get(0);
                    EntrustUserMappingDomain entrustUserMappingDomain = new EntrustUserMappingDomain();
                    entrustUserMappingDomain.setEntrustId(entrustInfoDomain.getId());
                    entrustUserMappingDomain.setUserId(houseInfoDomain.getUserId());
                    entrustUserMappingDomainMapper.insert(entrustUserMappingDomain);
                    MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
                    messageInfoDomain.setUserId(houseInfoDomain.getUserId());
                    messageInfoDomain.setEntrustId(entrustInfoDomain.getId());
                    messageInfoDomain.setHouseId(houseInfoDomain.getId());
                    messageInfoDomain.setMessage(CddConstant.MESSAGE_CONTENT_MATCH);
                    messageInfoDomain.setMessageType("house");
                    messageInfoDomainMapper.insertSelective(messageInfoDomain);
                }

               /* int size = 0;
                List<Integer> list = new ArrayList<>();
                if(houseInfoDomainList.size() >= 3){
                    size = 3;
                    list = getRandomNum(houseInfoDomainList.size());
                }else if (houseInfoDomainList.size() < 3){
                    size = houseInfoDomainList.size();
                }else{
                    size = 3;
                }*/
               /* if(size == 3){
                    for(int i=0;i<size;i++){
                        HouseInfoDomain houseInfoDomain = null;
                        if(houseInfoDomainList.size() > 3){
                            houseInfoDomain = houseInfoDomainList.get(list.get(i));
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

                }*/

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
            List<EntrustInfoVo> entrustInfoVoList = new ArrayList<>();
            if(entrustInfoVos != null && entrustInfoVos.size() > 0){
                for(EntrustInfoVo entrustInfoVo:entrustInfoVos){
                    CheckPhoneDomainExample checkPhoneDomainExample = new CheckPhoneDomainExample();
                    if(entrustConditionVo.getUserId() != null){
                        checkPhoneDomainExample.createCriteria().andUserIdEqualTo(entrustConditionVo.getUserId())
                                .andInfoIdEqualTo(entrustInfoVo.getEntrustId()).andTypeEqualTo("entrust");
                    }else{
                        checkPhoneDomainExample.createCriteria()
                                .andInfoIdEqualTo(entrustInfoVo.getEntrustId()).andTypeEqualTo("entrust");
                    }

                    List<CheckPhoneDomain> checkPhoneDomains = checkPhoneDomainMapper.selectByExample(checkPhoneDomainExample);
                    if(checkPhoneDomains != null && checkPhoneDomains.size() > 0){
                        entrustInfoVo.setCheckPhone(1);
                    }else{
                        entrustInfoVo.setCheckPhone(0);
                    }
                    entrustInfoVoList.add(entrustInfoVo);
                }
            }
            commonResult.setData(entrustInfoVoList);
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
        List<EntrustInfoVo> entrustInfoVoList = new ArrayList<>();
        if(entrustInfoVos != null && entrustInfoVos.size() > 0){
            for(EntrustInfoVo entrustInfoVo:entrustInfoVos){
                CheckPhoneDomainExample checkPhoneDomainExample = new CheckPhoneDomainExample();
                if(entrustConditionVo.getUserId() != null){
                    checkPhoneDomainExample.createCriteria().andUserIdEqualTo(entrustConditionVo.getUserId())
                            .andInfoIdEqualTo(entrustInfoVo.getEntrustId()).andTypeEqualTo("entrust");
                    List<CheckPhoneDomain> checkPhoneDomains = checkPhoneDomainMapper.selectByExample(checkPhoneDomainExample);
                    if(checkPhoneDomains != null && checkPhoneDomains.size() > 0){
                        entrustInfoVo.setCheckPhone(1);
                    }else{
                        entrustInfoVo.setCheckPhone(0);
                    }
                }else{
                    entrustInfoVo.setCheckPhone(0);
                }


                entrustInfoVoList.add(entrustInfoVo);
            }
        }
        commonResult.setData(entrustInfoVoList);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");

        return commonResult;
    }

    public List<Integer> getRandomNum(int num){
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        int i = random.nextInt(num);
        int j = random.nextInt(num);
        int k = random.nextInt(num);
        while (i == j) {
            j = random.nextInt(num);
        }
        while (k == j || k == i) {
            k = random.nextInt(num);
        }
        list.add(i);
        list.add(j);
        list.add(k);
        return list;
    }

    public static void main(String[] args){

//        System.out.println(i + "," + j + "," + k);
//        System.out.println((int)(Math.random() * 10));
    }
}
