package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.vo.HouseCompanyVo;
import com.cdd.gsl.vo.HouseConditionVo;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.HouseInfoDomainVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void addHouse(HouseInfoDomain houseInfoDomain) {
        houseInfoDomainMapper.insertSelective(houseInfoDomain);
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
        return houseInfoDetailVo;
    }

    @Override
    public List<HouseInfoDomainVo> findHouseInfoList(HouseConditionVo houseConditionVo) {

        List<HouseInfoDomainVo> houseInfoDomainList = houseInfoDao.selectHouseInfoListByCondition(houseConditionVo);
        return houseInfoDomainList;
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
}
