package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService{
    @Autowired
    private HouseInfoDomainMapper houseInfoDomainMapper;

    @Autowired
    private HouseInfoDao houseInfoDao;

    @Autowired
    private CompanyInfoDomainMapper companyInfoDomainMapper;

    @Autowired
    private ApplyBrokerInfoDomainMapper applyBrokerInfoDomainMapper;

    @Autowired
    private BrowseHouseRecordDomainMapper browseHouseRecordDomainMapper;

    @Autowired
    private InformHouseRecordDomainMapper informHouseRecordDomainMapper;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private ApplyBrokerInfoDao applyBrokerInfoDao;

    @Override
    public CommonResult addHouse(HouseInfoDomain houseInfoDomain) {
        CommonResult commonResult = new CommonResult();
        Long userId = houseInfoDomain.getUserId();
        List<Long> userIds = applyBrokerInfoDao.selectBrokerByUserId(userId);
        if(!CollectionUtils.isEmpty(userIds)){
            List<Long> ids = houseInfoDao.selectHouseByRegionAndUserId(houseInfoDomain,userIds);
            if(CollectionUtils.isEmpty(ids)){
                houseInfoDomainMapper.insertSelective(houseInfoDomain);
                commonResult.setFlag(1);
                commonResult.setMessage("添加成功");
            }else{
                commonResult.setFlag(0);
                commonResult.setMessage("同公司已有咨询师发布，不能重复发布");
            }
        }else{
            houseInfoDomainMapper.insertSelective(houseInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("添加成功");
        }
        return commonResult;

    }

    @Override
    public void updateHouse(HouseInfoDomain houseInfoDomain) {
        HouseInfoDomainExample houseInfoDomainExample = new HouseInfoDomainExample();
        houseInfoDomainExample.createCriteria().andStatusEqualTo(1).andIdEqualTo(houseInfoDomain.getId());
        List<HouseInfoDomain> houseInfoDomainList = houseInfoDomainMapper.selectByExample(houseInfoDomainExample);
        if(houseInfoDomainList != null && houseInfoDomainList.size() > 0){
            houseInfoDomainMapper.updateByPrimaryKeySelective(houseInfoDomain);
        }

    }

    @Override
    public HouseInfoDetailVo findHouseInfoById(Long houseId) {
        HouseInfoDetailVo houseInfoDetailVo = houseInfoDao.selectHouseInfoById(houseId);
        if(houseInfoDetailVo != null){
            SingleUserInfoVo singleUserInfoVo = userInfoDao.findUserInfoById(houseInfoDetailVo.getUserId());
            List<UserBrokerVo> userList = houseInfoDao.selectUserByHouseInfo(houseInfoDetailVo);
            if(!CollectionUtils.isEmpty(userList)){
                userList.forEach(userBrokerVo -> {
                    int count = houseInfoDao.selectHouseCountByUserId(userBrokerVo.getUserId());
                    userBrokerVo.setHouseCount(count);
                });
            }
            houseInfoDetailVo.setUser_list(userList);
            houseInfoDetailVo.setUser(singleUserInfoVo);
            List<HouseInfoDomainVo> houseInfoDomainVos = houseInfoDao.selectHouseInfoListByLike();
            houseInfoDetailVo.setLikes(houseInfoDomainVos);
            BrowseHouseRecordDomain browseHouseRecordDomain = new BrowseHouseRecordDomain();
            browseHouseRecordDomain.setUserId(houseInfoDetailVo.getUserId());
            browseHouseRecordDomain.setHouseId(houseId);
            browseHouseRecordDomainMapper.insertSelective(browseHouseRecordDomain);
            BrowseHouseRecordDomainExample browseHouseRecordDomainExample = new BrowseHouseRecordDomainExample();
            browseHouseRecordDomainExample.createCriteria().andHouseIdEqualTo(houseId);
            List<BrowseHouseRecordDomain> browseHouseRecordDomains = browseHouseRecordDomainMapper.selectByExample(browseHouseRecordDomainExample);
            houseInfoDetailVo.setBrowseCount(browseHouseRecordDomains.size());
        }

        return houseInfoDetailVo;
    }

    @Override
    public JSONObject findHouseInfoList(HouseConditionVo houseConditionVo) {

        List<HouseInfoDomainVo> houseInfoDomainList = houseInfoDao.selectHouseInfoListByCondition(houseConditionVo);
        int houseCount = houseInfoDao.countUserHouseInfoListByCondition(houseConditionVo);
        JSONObject data = new JSONObject();
        data.put("houseCount",houseCount);
        data.put("houseList",houseInfoDomainList);
        return data;
    }

    @Override
    public List<HouseInfoDomainVo> selectUserHouseInfoListByCondition(HouseConditionVo houseConditionVo) {
        List<HouseInfoDomainVo> houseInfoDomainList = houseInfoDao.selectUserHouseInfoListByCondition(houseConditionVo);
        return houseInfoDomainList;
    }

    @Override
    public List<HouseInfoDomainVo> selectCompanyHouseInfoListByCondition(HouseConditionVo houseConditionVo) {
        Long userId = houseConditionVo.getUserId();
        ApplyBrokerInfoDomainExample applyBrokerInfoDomainExample = new ApplyBrokerInfoDomainExample();
        applyBrokerInfoDomainExample.createCriteria().andUserIdEqualTo(userId).andApplyTypeEqualTo(2);
        List<ApplyBrokerInfoDomain> applyBrokerInfoDomains = applyBrokerInfoDomainMapper.selectByExample(applyBrokerInfoDomainExample);
        List<HouseInfoDomainVo> houseInfoDomainList = null;
        if(applyBrokerInfoDomains != null && applyBrokerInfoDomains.size() > 0){
            ApplyBrokerInfoDomain applyBrokerInfoDomain = applyBrokerInfoDomains.get(0);
            ApplyBrokerInfoDomainExample applyBrokerInfoExample = new ApplyBrokerInfoDomainExample();
            applyBrokerInfoExample.createCriteria().andCompanyNameEqualTo(applyBrokerInfoDomain.getCompanyName()).andApplyTypeEqualTo(2);
            List<ApplyBrokerInfoDomain> applyBrokerInfos = applyBrokerInfoDomainMapper.selectByExample(applyBrokerInfoExample);
            List<Long> userIds = new ArrayList<>();
            if(applyBrokerInfos != null && applyBrokerInfos.size() > 0){
                applyBrokerInfos.forEach(applyBrokerInfo -> {
                    userIds.add(applyBrokerInfo.getUserId());
                });
            }
            HouseCompanyVo houseCompanyVo = new HouseCompanyVo();
            BeanUtils.copyProperties(houseConditionVo,houseCompanyVo);
            houseCompanyVo.setUserIds(userIds);
            if(applyBrokerInfoDomain.getBrokerType() == CddConstant.COMMON_BROKER_TYPE){
                houseInfoDomainList = houseInfoDao.selectCompanyHouseInfoList(houseCompanyVo);
            }else if(applyBrokerInfoDomain.getBrokerType() == CddConstant.MANAGE_BROKER_TYPE){
                houseInfoDomainList = houseInfoDao.selectCompanyAllHouseInfoList(houseCompanyVo);
            }

        }
        return houseInfoDomainList;
    }

    @Override
    public CommonResult informHouseInfo(InformHouseRecordDomain informHouseRecordDomain) {
        CommonResult commonResult = new CommonResult();
        if(informHouseRecordDomain != null){
            InformHouseRecordDomainExample informHouseRecordDomainExample = new InformHouseRecordDomainExample();
            informHouseRecordDomainExample.createCriteria().andUserIdEqualTo(informHouseRecordDomain.getUserId()).andHouseIdEqualTo(informHouseRecordDomain.getHouseId());
            List<InformHouseRecordDomain> informHouseRecordDomains = informHouseRecordDomainMapper.selectByExample(informHouseRecordDomainExample);
            if(informHouseRecordDomains != null && informHouseRecordDomains.size() > 0){
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("已经举报");
            }else{
                informHouseRecordDomainMapper.insertSelective(informHouseRecordDomain);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("举报成功");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }

        return commonResult;
    }
}
