package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.EntrustAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.HttpClientUtils;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.EntrustService;
import com.cdd.gsl.vo.EntrustConditionVo;
import com.cdd.gsl.vo.EntrustInfoVo;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntrustServiceImpl implements EntrustService {

    private Logger logger = LoggerFactory.getLogger(EntrustServiceImpl.class);

    @Autowired
    private EntrustInfoDomainMapper entrustInfoDomainMapper;

    @Autowired
    private EntrustInfoDao entrustInfoDao;

    @Autowired
    private UserBrokerInfoDao userBrokerInfoDao;

    @Autowired
    private HouseInfoDomainMapper houseInfoDomainMapper;

    @Autowired
    private EntrustUserMappingDomainMapper entrustUserMappingDomainMapper;

    @Autowired
    private MessageInfoDomainMapper messageInfoDomainMapper;

    @Autowired
    private CheckPhoneDomainMapper checkPhoneDomainMapper;

    @Autowired
    private UserInfoDao userInfoDao;

    @Value("${verify.code.url}")
    private String verifyCodeUrl;

    @Value("${verify.code.key}")
    private String verifyCodeKey;

    @Value("${entrust.warn.id}")
    private String entrustWarnId;

    @Override
    public CommonResult createEntrust(EntrustInfoDomain entrustInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(entrustInfoDomain != null){
            entrustInfoDomainMapper.insertSelective(entrustInfoDomain);
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
    public CommonResult deleteEntrust(EntrustInfoDomain entrustInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(entrustInfoDomain != null){
            entrustInfoDomainMapper.updateByPrimaryKeySelective(entrustInfoDomain);
            EntrustUserMappingDomainExample entrustUserMappingDomainExample = new EntrustUserMappingDomainExample();
            entrustUserMappingDomainExample.createCriteria().andEntrustIdEqualTo(entrustInfoDomain.getId());
            entrustUserMappingDomainMapper.deleteByExample(entrustUserMappingDomainExample);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("删除成功");
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
                    //TODO 查看号码逻辑暂时取消
                   /* CheckPhoneDomainExample checkPhoneDomainExample = new CheckPhoneDomainExample();
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
                    }*/
                    entrustInfoVo.setCheckPhone(1);
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
    public CommonResult findEntrustDetail(Long entrustId) {
        CommonResult commonResult = new CommonResult();
        if(entrustId != null){
            EntrustInfoDomain entrustInfoDomain = entrustInfoDomainMapper.selectByPrimaryKey(entrustId);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(entrustInfoDomain);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<List<EntrustInfoVo>> findEntrustInfoByUserId(EntrustConditionVo entrustConditionVo) {

        CommonResult<List<EntrustInfoVo>> commonResult = new CommonResult<>();
        // && entrustConditionVo.getEntrustType() != null
        if(entrustConditionVo.getUserId() != null){
            List<EntrustInfoVo> entrustInfoVos = entrustInfoDao.findEntrustByUserId(entrustConditionVo);
           /* List<EntrustInfoVo> entrustInfoVoList = new ArrayList<>();
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
            }*/
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
        List<EntrustInfoVo> entrustInfoVoList = new ArrayList<>();
        if(entrustInfoVos != null && entrustInfoVos.size() > 0){
            for(EntrustInfoVo entrustInfoVo:entrustInfoVos){

                //TODO 限制查看电话号码逻辑 暂时取消
                /*CheckPhoneDomainExample checkPhoneDomainExample = new CheckPhoneDomainExample();
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
                }*/

                entrustInfoVo.setCheckPhone(1);
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

    @Override
    public CommonResult findAdminEntrustList(EntrustAdminConditionVo entrustAdminConditionVo) {
        CommonResult commonResult = new CommonResult();
        try{
            List<EntrustInfoVo> entrustInfoVoList = entrustInfoDao.findEntrustInfoList(entrustAdminConditionVo);
            int total = entrustInfoDao.countAllEntrust(entrustAdminConditionVo);
            JSONObject data = new JSONObject();
            data.put("entrustInfoList",entrustInfoVoList);
            data.put("total",total);
            commonResult.setData(data);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
        }catch (Exception e){
            logger.error("findAdminEntrustList error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult exportEntrustList(String keyword) {
        CommonResult commonResult = new CommonResult();
        List<EntrustInfoVo> entrustInfoVoList = entrustInfoDao.findExportEntrustInfoList(keyword);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(entrustInfoVoList);
        return commonResult;
    }

    @Override
    public CommonResult checkEntrust(EntrustInfoDomain entrust) {
        CommonResult commonResult = new CommonResult();
        try {
            entrust.setIsUsed(1);
            if(entrust == null
                    || entrust.getId() == null
                    || entrust.getIsUsed() == null){
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不能为空");
                return commonResult;
            }

            entrustInfoDomainMapper.updateByPrimaryKeySelective(entrust);
            EntrustInfoDomain entrustInfoDomain = entrustInfoDomainMapper.selectByPrimaryKey(entrust.getId());
            try {
                sendEntrustSms(entrustInfoDomain);
            }catch (Exception e){
                e.printStackTrace();
            }
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



            }

        } catch (Exception e){

        }
        return null;
    }

    //委托短信提醒
    public void sendEntrustSms(EntrustInfoDomain entrustInfoDomain){
        String tplValue = null;
        try {
            StringBuffer address = new StringBuffer();
            address.append(entrustInfoDomain.getContacts());
            if(entrustInfoDomain.getEntrustUseType() == 1){
                address.append("求租");
            }else if(entrustInfoDomain.getEntrustUseType() == 2){
                address.append("求购");
            }else if(entrustInfoDomain.getEntrustUseType() == 3){
                address.append("出租");
            }else if(entrustInfoDomain.getEntrustUseType() == 4){
                address.append("出售");
            }
            if(!StringUtils.isEmpty(entrustInfoDomain.getCity())){
                address.append(entrustInfoDomain.getCity());
            }

            if(!StringUtils.isEmpty(entrustInfoDomain.getCounty())){
                address.append(entrustInfoDomain.getCounty());
            }

            if(!StringUtils.isEmpty(entrustInfoDomain.getTown())){
                address.append(entrustInfoDomain.getTown());
            }

            if(entrustInfoDomain.getEntrustType() == 1){
                address.append(entrustInfoDomain.getArea()+"㎡").append("厂房");
            }else if(entrustInfoDomain.getEntrustType() == 2){
                address.append(entrustInfoDomain.getArea()+"㎡").append("仓库");
            }else if(entrustInfoDomain.getEntrustType() == 3){
                address.append(entrustInfoDomain.getArea()+"亩").append("土地");
            }

           /* if(!StringUtils.isEmpty(entrustInfoDomain.getBusiness())){
                address.append(" "+entrustInfoDomain.getBusiness());
            }*/


            tplValue = URLEncoder.encode("#code#="+address.toString()+"&#content#=http://cddwang.com ","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<String> phoneList = userInfoDao.findPhoneByServiceArea(entrustInfoDomain.getCity());
        List<String> brokerPhoneList = userBrokerInfoDao.getPhoneByCity(entrustInfoDomain.getCity());
        Set<String> phoneSet = new HashSet<>();
        phoneList.forEach(phone -> {
            phoneSet.add(phone);
        });
        brokerPhoneList.forEach(phone -> {
            phoneSet.add(phone);
        });
        if(!CollectionUtils.isEmpty(phoneSet)){
            for(String phone:phoneSet){
                StringBuffer uri = new StringBuffer().append(verifyCodeUrl)
                        .append("?mobile=").append(phone).append("&tpl_id=").append(entrustWarnId)
                        .append("&tpl_value=").append(tplValue).append("&key=").append(verifyCodeKey);
                String response = HttpClientUtils.getInstance().doGetWithJsonResult(uri.toString());
                if(Strings.isNotEmpty(response)){
                    JSONObject res = JSONObject.parseObject(response);
                    Integer flag = res.getInteger("error_code");
                    if(flag == 0){
                        logger.info("sendEntrustSms手机号 -{} 匹配信息发送成功",phone);
                    }else{
                        logger.info("sendEntrustSms手机号 -{} 匹配信息发送失败",phone);
                    }
                }else{
                    logger.info("sendEntrustSms手机号 -{} 匹配信息发送失败",phone);
                }
            }

        }


    }
}
