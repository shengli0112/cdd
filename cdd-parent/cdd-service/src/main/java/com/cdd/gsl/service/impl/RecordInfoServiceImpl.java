package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.RecordInfoDao;
import com.cdd.gsl.dao.RecordInfoDomainMapper;
import com.cdd.gsl.domain.RecordInfoDomain;
import com.cdd.gsl.service.RecordInfoService;
import com.cdd.gsl.vo.RecordInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordInfoServiceImpl implements RecordInfoService {

    @Autowired
    private RecordInfoDomainMapper recordInfoDomainMapper;

    @Autowired
    private RecordInfoDao recordInfoDao;

    @Override
    public CommonResult createRecordInfo(RecordInfoDomain recordInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(recordInfoDomain != null){
            recordInfoDomainMapper.insertSelective(recordInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }

    @Override
    public List<RecordInfoVo> findRecordInfoList(Long houseId) {
        List<RecordInfoVo> recordInfoList = new ArrayList<>();
        if(houseId != null){
            recordInfoList = recordInfoDao.findRecordInfoByHouseId(houseId);
        }
        return recordInfoList;
    }
}
