package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.UserBrokerConditionVo;
import com.cdd.gsl.admin.UserBrokerVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.ExcelUtil;
import com.cdd.gsl.dao.UserBrokerInfoDao;
import com.cdd.gsl.dao.UserBrokerInfoDomainMapper;
import com.cdd.gsl.dao.UserInfoDao;
import com.cdd.gsl.dao.UserInfoDomainMapper;
import com.cdd.gsl.domain.UserBrokerInfoDomain;
import com.cdd.gsl.domain.UserBrokerInfoDomainExample;
import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.domain.UserInfoDomainExample;
import com.cdd.gsl.service.UserBrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserBrokerServiceImpl implements UserBrokerService {

    private Logger logger = LoggerFactory.getLogger(UserBrokerServiceImpl.class);
    @Autowired
    private UserBrokerInfoDomainMapper userBrokerInfoDomainMapper;

    @Autowired
    private UserInfoDomainMapper userInfoDomainMapper;

    @Autowired
    private UserBrokerInfoDao userBrokerInfoDao;


    @Override
    public CommonResult importUserBroker(MultipartFile file) {
        CommonResult commonResult = new CommonResult();
        try{
            InputStream in =null;
            List<List<Object>> listob = null;
            try {
                in = file.getInputStream();
                listob = ExcelUtil.getFirstSheetListByExcel(in,file.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
                commonResult.setFlag(0);
                commonResult.setMessage("服务器导入异常");
            }

            List<UserBrokerInfoDomain> userBrokerInfoDomains = new ArrayList<>();
            int totalCount = 0;
            int errorCount = 0;
            if(listob != null && listob.size() > 0){
                for (List<Object> objList:listob) {
                        /*if(objList.size() < 4){
                            modelResult.setFlagToFailure();
                            modelResult.setMessage("文件内容不正确");
                            return modelResult;
                        }*/
                    UserBrokerInfoDomain userBrokerInfoDomain = new UserBrokerInfoDomain();
                    userBrokerInfoDomain.setName(String.valueOf(objList.get(0)).trim());
                    userBrokerInfoDomain.setPhone(String.valueOf(objList.get(1)).trim());
                    userBrokerInfoDomain.setCity(String.valueOf(objList.get(2)).trim());
                    userBrokerInfoDomain.setCounty(String.valueOf(objList.get(3)).trim());
                    userBrokerInfoDomain.setTown(String.valueOf(objList.get(4)).trim());
                    boolean flag = checkUserBrokerInfoDomain(userBrokerInfoDomain);
                    if(flag){
                        userBrokerInfoDomainMapper.insertSelective(userBrokerInfoDomain);
                    }else{
                        //TODO 错误结果导出
                        errorCount++;

                    }

                }
                totalCount = listob.size();
                if(errorCount == 0){
                    commonResult.setFlag(1);
                    commonResult.setMessage("本次成功导入"+totalCount+"条记录");
                    commonResult.setData(1);
                }else{
                    commonResult.setFlag(201);
                    commonResult.setMessage("本次共导入"+totalCount+"条记录，成功"+(totalCount-errorCount)+"条");
                    commonResult.setData(0);
                }
            } else{
                commonResult.setFlag(0);
                commonResult.setMessage("导入失败，数据为空");
            }
        }catch (Exception e){
            logger.error("importUserBroker error");
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("文件内容不正确");
        }

        return commonResult;
    }

    @Override
    public CommonResult findUserBroker(UserBrokerConditionVo userBrokerConditionVo) {
        CommonResult commonResult = new CommonResult();
        try {
            List<UserBrokerVo> userBrokerVoList = userBrokerInfoDao.getUserBrokerList(userBrokerConditionVo);
            int total = userBrokerInfoDao.countUserBroker(userBrokerConditionVo);
            JSONObject data = new JSONObject();
            data.put("userBrokerList",userBrokerVoList);
            data.put("total",total);
            commonResult.setData(data);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateUserBroker(UserBrokerInfoDomain userBrokerInfoDomain) {
        CommonResult commonResult = new CommonResult();
        try {
            userBrokerInfoDomainMapper.updateByPrimaryKeySelective(userBrokerInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult addUserBroker(UserBrokerInfoDomain userBrokerInfoDomain) {
        CommonResult commonResult = new CommonResult();
        try {
            userBrokerInfoDomainMapper.insertSelective(userBrokerInfoDomain);
//            userBrokerInfoDao.insert(userBrokerInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    public boolean checkUserBrokerInfoDomain(UserBrokerInfoDomain userBrokerInfoDomain){
        if(userBrokerInfoDomain == null
                || userBrokerInfoDomain.getPhone() == null
                || userBrokerInfoDomain.getCity() == null
                || userBrokerInfoDomain.getTown() == null){
            return false;
        } else {
            UserBrokerInfoDomainExample userBrokerInfoDomainExample = new UserBrokerInfoDomainExample();
            userBrokerInfoDomainExample.createCriteria().andPhoneEqualTo(userBrokerInfoDomain.getPhone()).andStatusEqualTo(1);
            List<UserBrokerInfoDomain> userBrokerInfoDomains = userBrokerInfoDomainMapper.selectByExample(userBrokerInfoDomainExample);
            UserInfoDomainExample userInfoDomainExample = new UserInfoDomainExample();
            userInfoDomainExample.createCriteria().andPhoneEqualTo(userBrokerInfoDomain.getPhone()).andStatusEqualTo(1);
            List<UserInfoDomain> userList = userInfoDomainMapper.selectByExample(userInfoDomainExample);
            if(CollectionUtils.isEmpty(userList)
                    && CollectionUtils.isEmpty(userBrokerInfoDomains)){
                return true;
            }else{
                return false;
            }
        }
    }
}
