package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.StorageInfoDomainMapper;
import com.cdd.gsl.domain.StorageInfoDomain;
import com.cdd.gsl.domain.StorageInfoDomainExample;
import com.cdd.gsl.service.StorageService;
import com.cdd.gsl.vo.StorageConditionVo;
import com.cdd.gsl.vo.StorageInfoDomainVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageInfoDomainMapper storageInfoDomainMapper;

    @Override
    public CommonResult createStorage(StorageInfoDomain storageInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(storageInfoDomain != null){
            storageInfoDomainMapper.insertSelective(storageInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public List<StorageInfoDomain> getStorageList(StorageConditionVo storageConditionVo) {
        return null;
    }

    @Override
    public StorageInfoDomainVo getStorageInfoById(Long id) {
        if(id != null){

            StorageInfoDomain storageInfoDomain = storageInfoDomainMapper.selectByPrimaryKey(id);
            StorageInfoDomainVo storageInfoDomainVo = new StorageInfoDomainVo();
            BeanUtils.copyProperties(storageInfoDomain,storageInfoDomainVo);
            //1 求租 2 求购 3 出租 4 出售
            if(storageInfoDomain.getType() == 1){
                storageInfoDomainVo.setStorageType("求租");
            }else if(storageInfoDomain.getType() == 2){
                storageInfoDomainVo.setStorageType("求购");
            }else if(storageInfoDomain.getType() == 3){
                storageInfoDomainVo.setStorageType("出租");
            }else if(storageInfoDomain.getType() == 4){
                storageInfoDomainVo.setStorageType("出售");
            }
        }
        return null;
    }


}
