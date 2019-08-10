package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.DateUtil;
import com.cdd.gsl.common.util.PageUtil;
import com.cdd.gsl.common.util.ResultPage;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService{
    private Logger logger = LoggerFactory.getLogger(HouseServiceImpl.class);
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

    @Autowired
    private CheckPhoneDomainMapper checkPhoneDomainMapper;

    @Autowired
    private UserInfoDomainMapper userInfoDomainMapper;

    @Autowired
    private HouseTopDomainMapper houseTopDomainMapper;

    @Autowired
    private MessageInfoDomainMapper messageInfoDomainMapper;

    @Autowired
    private TopInfoDomainMapper topInfoDomainMapper;

    @Autowired
    private ConsumeRecordDomainMapper consumeRecordDomainMapper;

    @Override
    public CommonResult addHouse(HouseInfoDomain houseInfoDomain) {
        CommonResult commonResult = new CommonResult();
        logger.info("HouseServiceImpl addHouse -{}",JSONObject.toJSON(houseInfoDomain).toString());
        Long userId = houseInfoDomain.getUserId();
        try {
            List<Long> userIds = applyBrokerInfoDao.selectBrokerByUserId(userId);
            if(!CollectionUtils.isEmpty(userIds)){
                List<Long> ids = houseInfoDao.selectHouseByRegionAndUserId(houseInfoDomain,userIds);
                if(CollectionUtils.isEmpty(ids)){
                    houseInfoDomainMapper.insertSelective(houseInfoDomain);
                    userInfoDao.updateUserintegralById(houseInfoDomain.getUserId(),CddConstant.AWARD_CURRENCY_COUNT);
                    /*MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
                    messageInfoDomain.setUserId(houseInfoDomain.getUserId());
                    messageInfoDomain.setHouseId(houseInfoDomain.getId());
                    messageInfoDomain.setMessage("您发布\""+houseInfoDomain.getTitle()+"\"成功，奖励5个多多币");
                    messageInfoDomain.setMessageType(CddConstant.MESSAGE_CURRENCY_TYPE);
                    messageInfoDomainMapper.insertSelective(messageInfoDomain);*/
                    ConsumeRecordDomain consumeRecordDomain = new ConsumeRecordDomain();
                    consumeRecordDomain.setTitle(CddConstant.CREATE_HOUSE_TITLE);
                    consumeRecordDomain.setUserId(houseInfoDomain.getUserId());
                    consumeRecordDomain.setAction(CddConstant.CONSUME_RECORD_AWARD);
                    consumeRecordDomain.setIntegral(CddConstant.AWARD_CURRENCY_COUNT);
                    consumeRecordDomain.setType(CddConstant.CONSUME_RECORD_TYPE_ADD_HOUSE);
                    consumeRecordDomainMapper.insertSelective(consumeRecordDomain);
                    commonResult.setFlag(1);
                    commonResult.setMessage("添加成功");
                }else{
                    commonResult.setFlag(0);
                    commonResult.setMessage("重复房源,不能发布");
                }
            }else{
                houseInfoDomainMapper.insertSelective(houseInfoDomain);
                userInfoDao.updateUserintegralById(houseInfoDomain.getUserId(),CddConstant.AWARD_CURRENCY_COUNT);
                MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
                messageInfoDomain.setUserId(houseInfoDomain.getUserId());
                messageInfoDomain.setHouseId(houseInfoDomain.getId());
                messageInfoDomain.setMessage("您发布\""+houseInfoDomain.getTitle()+"\"成功，奖励多多币5枚");
                messageInfoDomain.setMessageType(CddConstant.MESSAGE_CURRENCY_TYPE);
                messageInfoDomainMapper.insertSelective(messageInfoDomain);
                commonResult.setFlag(1);
                commonResult.setMessage("添加成功");
            }
        }catch (Exception e){
            logger.info("HouseServiceImpl addHouse error");
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("发布异常");
        }

        return commonResult;

    }

    @Override
    public CommonResult updateHouse(HouseInfoDomain houseInfoDomain) {
        CommonResult commonResult = new CommonResult();
        Long userId = houseInfoDomain.getUserId();
        List<Long> userIds = applyBrokerInfoDao.selectBrokerByUserId(userId);
        if(!CollectionUtils.isEmpty(userIds)) {
            List<Long> ids = houseInfoDao.selectHouseByRegionAndUserIdAndHouseId(houseInfoDomain, userIds);
            if(CollectionUtils.isEmpty(ids)){
                HouseInfoDomainExample houseInfoDomainExample = new HouseInfoDomainExample();
                houseInfoDomainExample.createCriteria().andStatusEqualTo(1).andIdEqualTo(houseInfoDomain.getId());
                List<HouseInfoDomain> houseInfoDomainList = houseInfoDomainMapper.selectByExample(houseInfoDomainExample);
                if(houseInfoDomainList != null && houseInfoDomainList.size() > 0){
                    houseInfoDomainMapper.updateByPrimaryKeySelective(houseInfoDomain);
                    commonResult.setFlag(1);
                    commonResult.setMessage("更新成功");
                }else{
                    commonResult.setFlag(0);
                    commonResult.setMessage("没有对应的房源");
                }
            }else{
                commonResult.setFlag(0);
                commonResult.setMessage("同公司已有经纪人发布，不能更新为相同的房源");
            }
        }else{
            HouseInfoDomainExample houseInfoDomainExample = new HouseInfoDomainExample();
            houseInfoDomainExample.createCriteria().andStatusEqualTo(1).andIdEqualTo(houseInfoDomain.getId());
            List<HouseInfoDomain> houseInfoDomainList = houseInfoDomainMapper.selectByExample(houseInfoDomainExample);
            if(houseInfoDomainList != null && houseInfoDomainList.size() > 0){
                houseInfoDomainMapper.updateByPrimaryKeySelective(houseInfoDomain);
                commonResult.setFlag(1);
                commonResult.setMessage("更新成功");
            }else{
                commonResult.setFlag(0);
                commonResult.setMessage("没有对应的房源");
            }
        }
        return commonResult;

    }

    @Override
    public void deleteHouse(HouseInfoDomain houseInfoDomain) {
        HouseInfoDomainExample houseInfoDomainExample = new HouseInfoDomainExample();
        houseInfoDomainExample.createCriteria().andIdEqualTo(houseInfoDomain.getId());
        List<HouseInfoDomain> houseInfoDomainList = houseInfoDomainMapper.selectByExample(houseInfoDomainExample);
        if(houseInfoDomainList != null && houseInfoDomainList.size() > 0){
            houseInfoDomainMapper.updateByPrimaryKeySelective(houseInfoDomain);
        }
    }

    @Override
    public HouseInfoDetailVo findHouseInfoById(Long houseId) {
        long start = System.currentTimeMillis();
        HouseInfoDetailVo houseInfoDetailVo = houseInfoDao.selectHouseInfoById(houseId);
        logger.info("HouseServiceImpl selectHouseInfoById ms --{}",(System.currentTimeMillis() - start));
        if(houseInfoDetailVo != null){
            long start1 = System.currentTimeMillis();
            SingleUserInfoVo singleUserInfoVo = userInfoDao.findUserInfoById(houseInfoDetailVo.getUserId());
            logger.info("HouseServiceImpl findUserInfoById ms --{}",(System.currentTimeMillis() - start1));
            long start2 = System.currentTimeMillis();
            List<UserBrokerVo> userList = houseInfoDao.selectUserByHouseInfo(houseInfoDetailVo);
            logger.info("HouseServiceImpl selectUserByHouseInfo ms --{}",(System.currentTimeMillis() - start2));
            if(!CollectionUtils.isEmpty(userList)){
                userList.forEach(userBrokerVo -> {
                    int count = houseInfoDao.selectHouseCountByUserId(userBrokerVo.getUserId());
                    userBrokerVo.setHouseCount(count);
                });
            }
            houseInfoDetailVo.setUser_list(userList);
            houseInfoDetailVo.setUser(singleUserInfoVo);
            long start3 = System.currentTimeMillis();
            List<HouseInfoDomainVo> houseInfoDomainVos = houseInfoDao.selectHouseInfoListByDetailLike(houseInfoDetailVo.getCounty());
            logger.info("HouseServiceImpl selectUserByHouseInfo ms --{}",(System.currentTimeMillis() - start3));
            houseInfoDetailVo.setLikes(houseInfoDomainVos);
            BrowseHouseRecordDomain browseHouseRecordDomain = new BrowseHouseRecordDomain();
            browseHouseRecordDomain.setUserId(houseInfoDetailVo.getUserId());
            browseHouseRecordDomain.setHouseId(houseId);
            long start4 = System.currentTimeMillis();
            browseHouseRecordDomainMapper.insertSelective(browseHouseRecordDomain);
            logger.info("HouseServiceImpl selectUserByHouseInfo ms --{}",(System.currentTimeMillis() - start4));
            BrowseHouseRecordDomainExample browseHouseRecordDomainExample = new BrowseHouseRecordDomainExample();
            browseHouseRecordDomainExample.createCriteria().andHouseIdEqualTo(houseId);
            long start5 = System.currentTimeMillis();
            List<BrowseHouseRecordDomain> browseHouseRecordDomains = browseHouseRecordDomainMapper.selectByExample(browseHouseRecordDomainExample);
            logger.info("HouseServiceImpl selectUserByHouseInfo ms --{}",(System.currentTimeMillis() - start5));
            houseInfoDetailVo.setBrowseCount(browseHouseRecordDomains.size());
        }

        return houseInfoDetailVo;
    }

    @Override
    public JSONObject findHouseInfoList(HouseConditionVo houseConditionVo) {
        HashMap map = new HashMap();
        /*map.put("page", houseConditionVo.getPageNo());
        map.put("size", houseConditionVo.getPageSize());
        map = PageUtil.getPageMap(map);*/
        List<HouseInfoDomainVo> houseInfoDomainList = houseInfoDao.selectHouseInfoListByCondition(houseConditionVo);
        int houseCount = houseInfoDao.countUserHouseInfoListByCondition(houseConditionVo);
        int topHouseCount = houseInfoDao.selectTopHouseInfoListByCondition(houseConditionVo);

        houseCount = houseCount+topHouseCount;
        JSONObject data = new JSONObject();

        data.put("houseCount",houseCount);
        data.put("houseList",houseInfoDomainList);
        return data;
    }

    @Override
    public JSONObject findHomeHouseInfoList(HouseConditionVo houseConditionVo) {
        List<HouseInfoDomainVo> topHouseDomainList = houseInfoDao.selectTopHomeHouseListByCondition(houseConditionVo);
        List<HouseInfoDomainVo> houseInfoDomainList = houseInfoDao.selectHomeHouseListByCondition(houseConditionVo);
        int houseCount = houseInfoDao.countUserHouseInfoListByCondition(houseConditionVo);
        houseCount = houseCount + topHouseDomainList.size();
        JSONObject data = new JSONObject();

        data.put("houseCount",houseCount);
        data.put("houseList",houseInfoDomainList);
        return data;
    }

    @Override
    public List<HouseInfoDomainVo> selectUserHouseInfoListByCondition(HouseConditionVo houseConditionVo) {
        List<HouseInfoDomainVo> houseInfoDomainList = houseInfoDao.selectUserHouseInfoListByCondition(houseConditionVo);
        List<HouseInfoDomainVo> houseInfoDomains = new ArrayList<>();
        if(houseInfoDomainList != null && houseInfoDomainList.size() > 0){
            for(HouseInfoDomainVo houseInfoDomainVo:houseInfoDomainList){
                CheckPhoneDomainExample checkPhoneDomainExample = new CheckPhoneDomainExample();
                checkPhoneDomainExample.createCriteria().andUserIdEqualTo(houseInfoDomainVo.getUserId())
                        .andInfoIdEqualTo(houseInfoDomainVo.getId()).andTypeEqualTo("house");
                List<CheckPhoneDomain> checkPhoneDomains = checkPhoneDomainMapper.selectByExample(checkPhoneDomainExample);
                if(checkPhoneDomains != null && checkPhoneDomains.size() > 0){
                    houseInfoDomainVo.setCheckPhone(true);
                }else{
                    houseInfoDomainVo.setCheckPhone(false);
                }
                houseInfoDomains.add(houseInfoDomainVo);
            }
        }
        return houseInfoDomains;
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

        List<HouseInfoDomainVo> houseInfoDomains = new ArrayList<>();
        if(houseInfoDomainList != null && houseInfoDomainList.size() > 0){
            for(HouseInfoDomainVo houseInfoDomainVo:houseInfoDomainList){
                CheckPhoneDomainExample checkPhoneDomainExample = new CheckPhoneDomainExample();
                checkPhoneDomainExample.createCriteria().andUserIdEqualTo(houseConditionVo.getUserId())
                        .andInfoIdEqualTo(houseInfoDomainVo.getId()).andTypeEqualTo("house");
                List<CheckPhoneDomain> checkPhoneDomains = checkPhoneDomainMapper.selectByExample(checkPhoneDomainExample);
                if(checkPhoneDomains != null && checkPhoneDomains.size() > 0){
                    houseInfoDomainVo.setCheckPhone(true);
                }else{
                    houseInfoDomainVo.setCheckPhone(false);
                }
                houseInfoDomains.add(houseInfoDomainVo);
            }
        }

        return houseInfoDomains;
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

    @Override
    public void delayTopHouse() {
        logger.info("HouseServiceImpl delayTopHouse start");
        HouseTopDomainExample houseTopDomainExample = new HouseTopDomainExample();
        houseTopDomainExample.createCriteria().andStatusEqualTo(1);
        List<HouseTopDomain> houseTopDomainList = houseTopDomainMapper.selectByExample(houseTopDomainExample);

        if(!CollectionUtils.isEmpty(houseTopDomainList)){
            houseTopDomainList.forEach(houseTop ->{
                int days = DateUtil.differentDaysByMillisecond(new Date(),houseTop.getCreateTs());
                TopInfoDomain topInfoDomain = topInfoDomainMapper.selectByPrimaryKey(houseTop.getTopId());
                if(days > topInfoDomain.getDay()){
                    HouseTopDomain houseTopDomain = new HouseTopDomain();
                    houseTopDomain.setId(houseTop.getId());
                    houseTopDomain.setStatus(0);
                    houseTopDomainMapper.updateByPrimaryKeySelective(houseTopDomain);
                    logger.info("HouseServiceImpl delayTopHouse expire objid-{}, type-{}",houseTop.getObjId(),houseTop.getType());
                }
            });
        }
    }

    @Override
    public CommonResult switchHouse(Long fromUserId, Long toUserId) {
        CommonResult commonResult = new CommonResult();
        HouseInfoDomainExample houseInfoDomainExample = new HouseInfoDomainExample();
        houseInfoDomainExample.createCriteria().andUserIdEqualTo(fromUserId);
        List<HouseInfoDomain> houseInfoDomainList = houseInfoDomainMapper.selectByExample(houseInfoDomainExample);
        if(!CollectionUtils.isEmpty(houseInfoDomainList)){
            houseInfoDomainList.forEach(fromHouse ->{
                HouseInfoDomain houseInfoDomain = new HouseInfoDomain();
                houseInfoDomain.setId(fromHouse.getId());
                houseInfoDomain.setUserId(toUserId);
                houseInfoDomainMapper.updateByPrimaryKeySelective(houseInfoDomain);
            });

        }
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("切换成功");
        return commonResult;
    }

    @Override
    public CommonResult houseCount() {
        CommonResult commonResult = new CommonResult();
        try {
            //厂房数量
            int cfCount = houseInfoDao.countHouseByHouseType(CddConstant.HOUSE_TYPE_CF);
            //仓库数量
            int ckCount = houseInfoDao.countHouseByHouseType(CddConstant.HOUSE_TYPE_CK);
            //土地数量
            int tdCount = houseInfoDao.countHouseByHouseType(CddConstant.HOUSE_TYPE_TD);
            JSONObject data = new JSONObject();
            data.put("cfCount",cfCount);
            data.put("ckCount",ckCount);
            data.put("tdCount",tdCount);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);
        }catch (Exception e){
            logger.error("HouseServerImpl houseCount error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("系统异常");
        }

        return commonResult;
    }
}
