package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.ParkAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.HttpClientUtils;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.ParkService;
import com.cdd.gsl.vo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ParkServiceImpl implements ParkService {

    private Logger logger = LoggerFactory.getLogger(ParkServiceImpl.class);

    @Autowired
    private SellParkInfoDomainMapper sellParkInfoDomainMapper;

    @Autowired
    private LeaseParkInfoDomainMapper leaseParkInfoDomainMapper;

    @Autowired
    private SellParkDao sellParkDao;

    @Autowired
    private LeaseParkDao leaseParkDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private MessageInfoDomainMapper messageInfoDomainMapper;

    @Autowired
    private ConsumeRecordDomainMapper consumeRecordDomainMapper;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Value("${verify.code.url}")
    private String verifyCodeUrl;

    @Value("${verify.code.key}")
    private String verifyCodeKey;

    @Value("${verify.park.id}")
    private String verifyParkId;

    @Override
    public CommonResult createSellPark(SellParkInfoDomain sellParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        logger.info("ParkServiceImpl createSellPark start");
        try{
            if(sellParkInfoDomain != null){
                logger.info("ParkServiceImpl createSellPark sellParkInfoDomain -{}",JSONObject.toJSON(sellParkInfoDomain).toString());
                sellParkInfoDomainMapper.insertSelective(sellParkInfoDomain);
                userInfoDao.updateUserintegralById(sellParkInfoDomain.getUserId(),CddConstant.AWARD_CURRENCY_COUNT);
                /*MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
                messageInfoDomain.setUserId(sellParkInfoDomain.getUserId());
                messageInfoDomain.setMessage("您发布园区\""+sellParkInfoDomain.getParkName()+"\"成功，奖励多多币5枚");
                messageInfoDomain.setMessageType(CddConstant.MESSAGE_CURRENCY_TYPE);
                messageInfoDomainMapper.insertSelective(messageInfoDomain);*/
                ConsumeRecordDomain consumeRecordDomain = new ConsumeRecordDomain();
                consumeRecordDomain.setTitle(CddConstant.CREATE_ENTERPRISE_TITLE);
                consumeRecordDomain.setUserId(sellParkInfoDomain.getUserId());
                consumeRecordDomain.setAction(CddConstant.CONSUME_RECORD_AWARD);
                consumeRecordDomain.setIntegral(CddConstant.AWARD_CURRENCY_COUNT);
                consumeRecordDomain.setType(CddConstant.CONSUME_RECORD_TYPE_ADD_HOUSE);
                consumeRecordDomainMapper.insertSelective(consumeRecordDomain);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("创建成功");
                if(Strings.isNotEmpty(sellParkInfoDomain.getPhone())){
                    logger.info("出售园区提醒短信发送开始");
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            logger.info("出售园区提醒短信 createSellPark sellParkInfoDomain -{}",sellParkInfoDomain.toString());
                            sendSellParkSms(sellParkInfoDomain);
                        }
                    });
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不正确");
            }
        }catch (Exception e){
            logger.error("ParkServiceImpl createSellPark error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数异常");
        }

        return commonResult;
    }

    @Override
    public CommonResult createLeasePark(LeaseParkInfoDomain leaseParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        try{
            if(leaseParkInfoDomain != null){
                logger.info("ParkServiceImpl createLeasePark leaseParkInfoDomain -{}",JSONObject.toJSON(leaseParkInfoDomain).toString());
                leaseParkInfoDomainMapper.insertSelective(leaseParkInfoDomain);
                userInfoDao.updateUserintegralById(leaseParkInfoDomain.getUserId(),CddConstant.AWARD_CURRENCY_COUNT);
                /*MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
                messageInfoDomain.setUserId(leaseParkInfoDomain.getUserId());
                messageInfoDomain.setMessage("您发布园区\""+leaseParkInfoDomain.getParkName()+"\"成功，奖励多多币5枚");
                messageInfoDomain.setMessageType(CddConstant.MESSAGE_CURRENCY_TYPE);
                messageInfoDomainMapper.insertSelective(messageInfoDomain);*/
                ConsumeRecordDomain consumeRecordDomain = new ConsumeRecordDomain();
                consumeRecordDomain.setTitle(CddConstant.CREATE_ENTERPRISE_TITLE);
                consumeRecordDomain.setUserId(leaseParkInfoDomain.getUserId());
                consumeRecordDomain.setAction(CddConstant.CONSUME_RECORD_AWARD);
                consumeRecordDomain.setIntegral(CddConstant.AWARD_CURRENCY_COUNT);
                consumeRecordDomain.setType(CddConstant.CONSUME_RECORD_TYPE_ADD_HOUSE);
                consumeRecordDomainMapper.insertSelective(consumeRecordDomain);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("创建成功");
                if(Strings.isNotEmpty(leaseParkInfoDomain.getPhone())){
                    logger.info("出租园区提醒短信发送开始");
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            logger.info("出租园区提醒短信 createLeasePark leaseParkInfoDomain -{}",leaseParkInfoDomain.toString());
                            sendLeaseParkSms(leaseParkInfoDomain);
                        }
                    });
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不正确");
            }
        }catch (Exception e){
            logger.error("ParkServiceImpl createLeasePark error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数异常");
        }

        return commonResult;
    }

    //出售短信提醒
    public void sendSellParkSms(SellParkInfoDomain sellParkInfoDomain){
        String tplValue = null;
        try {
            StringBuffer address = new StringBuffer();
            address.append(" 园区名称:").append(sellParkInfoDomain.getParkName()).append(" 适合行业：")
                .append(sellParkInfoDomain.getIndustry());

            SingleUserInfoVo user = userInfoDao.findUserInfoById(sellParkInfoDomain.getUserId());
            tplValue = URLEncoder.encode("#code#="+address.toString()+"&#content#=网址：http://cddwang.com，欢迎点击网址进入平台查看或发布信息。","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuffer uri = new StringBuffer().append(verifyCodeUrl)
                .append("?mobile=").append(sellParkInfoDomain.getPhone()).append("&tpl_id=").append(verifyParkId)
                .append("&tpl_value=").append(tplValue).append("&key=").append(verifyCodeKey);
        String response = HttpClientUtils.getInstance().doGetWithJsonResult(uri.toString());
        if(Strings.isNotEmpty(response)){
            JSONObject res = JSONObject.parseObject(response);
            Integer flag = res.getInteger("error_code");
            if(flag == 0){
                logger.info("createSellPark 验证码发送成功");
            }else{
                logger.info("createSellPark 验证码发送失败");
            }
        }else{
            logger.info("createSellPark 验证码发送失败");
        }

    }

    //出租短信提醒
    public void sendLeaseParkSms(LeaseParkInfoDomain leaseParkInfoDomain){
        String tplValue = null;
        try {
            StringBuffer address = new StringBuffer();
            /*address.append(leaseParkInfoDomain.getCity()).append(leaseParkInfoDomain.getCounty()).append(leaseParkInfoDomain.getTown())
                    .append(" "+leaseParkInfoDomain.getParkName()).append(" 出租园区面积：").append(leaseParkInfoDomain.getTotalArea()).append("㎡，")
                    .append("价格 "+leaseParkInfoDomain.getUnitPrice());

            if(leaseParkInfoDomain.getPriceType() == 1){
                address.append("元/㎡/天");
            }else if(leaseParkInfoDomain.getPriceType() == 2){
                address.append("元/㎡/月");
            }else if(leaseParkInfoDomain.getPriceType() == 3){
                address.append("元/㎡/年");
            }*/
            address.append(" 园区名称:").append(leaseParkInfoDomain.getParkName()).append(" 适合行业：")
                    .append(leaseParkInfoDomain.getIndustry());

            SingleUserInfoVo user = userInfoDao.findUserInfoById(leaseParkInfoDomain.getUserId());
            String userStr = user.getUsername()+" "+user.getPhone();
            tplValue = URLEncoder.encode("#code#="+address.toString()+"&#name#="+userStr+"&#content#=网址：http://cddwang.com，欢迎点击网址进入平台查看或发布信息。","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuffer uri = new StringBuffer().append(verifyCodeUrl)
                .append("?mobile=").append(leaseParkInfoDomain.getPhone()).append("&tpl_id=").append(verifyParkId)
                .append("&tpl_value=").append(tplValue).append("&key=").append(verifyCodeKey);
        String response = HttpClientUtils.getInstance().doGetWithJsonResult(uri.toString());
        if(Strings.isNotEmpty(response)){
            JSONObject res = JSONObject.parseObject(response);
            Integer flag = res.getInteger("error_code");
            if(flag == 0){
                logger.info("createLeasePark 验证码发送成功");
            }else{
                logger.info("createLeasePark 验证码发送失败");
            }
        }else{
            logger.info("createLeasePark 验证码发送失败");
        }

    }

    @Override
    public CommonResult updateSellPark(SellParkInfoDomain sellParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(sellParkInfoDomain != null){
            sellParkInfoDomainMapper.updateByPrimaryKeySelective(sellParkInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateLeasePark(LeaseParkInfoDomain leaseParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(leaseParkInfoDomain != null){
            leaseParkInfoDomainMapper.updateByPrimaryKeySelective(leaseParkInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult findSellParkDetail(Long sellParkId) {
        CommonResult  commonResult = new CommonResult<>();
        if(sellParkId != null){
            List<SellParkInfoVo> sellParkInfoVoList = sellParkDao.selectSellParkInfoById(sellParkId);
            if(CollectionUtils.isNotEmpty(sellParkInfoVoList)){
                SellParkInfoVo sellParkInfoVo = sellParkInfoVoList.get(0);
                SingleUserInfoVo userInfoVo = userInfoDao.findUserInfoById(sellParkInfoVo.getUserId());
                sellParkInfoVo.setUser(userInfoVo);
                List<SellParkInfoVo> sellParkInfoVoRand = sellParkDao.selectSellParkRand();
                sellParkInfoVo.setLikes(sellParkInfoVoRand);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("查询成功");
                commonResult.setData(sellParkInfoVo);
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该出售园区不存在");
            }


        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<LeaseParkInfoVo> findLeaseParkDetail(Long leaseParkId) {
        CommonResult<LeaseParkInfoVo>  commonResult = new CommonResult<>();
        if(leaseParkId != null){
            List<LeaseParkInfoVo> leaseParkInfos = leaseParkDao.selectLeaseParkInfoById(leaseParkId);
            LeaseParkInfoVo leaseParkInfoVo = new LeaseParkInfoVo();
            if(leaseParkInfos != null && leaseParkInfos.size() > 0){
                leaseParkInfoVo = leaseParkInfos.get(0);
                SingleUserInfoVo userInfoVo = userInfoDao.findUserInfoById(leaseParkInfoVo.getUserId());
                leaseParkInfoVo.setUser(userInfoVo);
                List<LeaseParkInfoVo> randomLeaseParkList = leaseParkDao.selectLeaseParkInfoByRandom();
                leaseParkInfoVo.setLikes(randomLeaseParkList);
            }
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(leaseParkInfoVo);

        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public List<SellParkInfoDomain> findSellParkList(SellParkCondition sellParkCondition) {
        List<SellParkInfoDomain> sellParkInfoDomainList = sellParkDao.selectSellParkInfoList(sellParkCondition);
        return sellParkInfoDomainList;
    }

    @Override
    public List<LeaseParkInfoVo> findLeaseParkList(LeaseParkCondition leaseParkCondition) {
        return leaseParkDao.selectLeaseParkInfoList(leaseParkCondition);
    }

    @Override
    public CommonResult findAdminSellParkList(ParkAdminConditionVo parkAdminConditionVo) {
        CommonResult commonResult = new CommonResult();
        int total = sellParkDao.sellParkCount(parkAdminConditionVo);
        List<SellParkInfoDomain> sellParkInfoDomainList = sellParkDao.selectAdminSellParkInfoList(parkAdminConditionVo);
        JSONObject json = new JSONObject();
        json.put("total",total);
        json.put("sellParkList",sellParkInfoDomainList);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }

    @Override
    public CommonResult findAdminLeaseParkList(ParkAdminConditionVo parkAdminConditionVo) {
        CommonResult commonResult = new CommonResult();
        int total = leaseParkDao.leaseParkCount(parkAdminConditionVo);
        List<LeaseParkInfoVo> leaseParkInfoVoList = leaseParkDao.selectAdminLeaseParkInfoList(parkAdminConditionVo);
        JSONObject json = new JSONObject();
        json.put("total",total);
        json.put("leaseParkList",leaseParkInfoVoList);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }

    @Override
    public CommonResult deleteSellPark(Long sellParkId) {
        return null;
    }

    @Override
    public CommonResult deleteLeasePark(Long leaseParkId) {
        return null;
    }
}
