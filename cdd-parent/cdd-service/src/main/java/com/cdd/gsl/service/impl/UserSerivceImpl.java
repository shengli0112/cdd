package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.HttpClientUtils;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.UserService;
import com.cdd.gsl.vo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service
public class UserSerivceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserSerivceImpl.class);

    @Autowired
    private ThirdUserInfoDomainMapper thirdUserInfoDomainMapper;

    @Autowired
    private UserInfoDomainMapper userInfoDomainMapper;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private FollowInfoDao followInfoDao;

    @Autowired
    private FollowInfoDomainMapper followInfoDomainMapper;

    @Autowired
    private CommonDictDomainMapper commonDictDomainMapper;

    @Autowired
    private CompanyUserMappingDomainMapper companyUserMappingDomainMapper;

    @Autowired
    private UserTicketDomainMapper userTicketDomainMapper;

    @Autowired
    private ApplyBrokerInfoDomainMapper applyBrokerInfoDomainMapper;

    @Autowired
    private ApplyBrokerInfoDao applyBrokerInfoDao;

    @Autowired
    private VerifyPhoneDomainMapper verifyPhoneDomainMapper;

    @Autowired
    private DeviceLoginDomainMapper deviceLoginDomainMapper;

    @Autowired
    private DeviceLoginDao deviceLoginDao;

    @Autowired
    private HouseInfoDao houseInfoDao;

    @Autowired
    private SellParkDao sellParkDao;

    @Autowired
    private LeaseParkDao leaseParkDao;

    @Autowired
    private EnterpriseInfoDao enterpriseInfoDao;
    @Value("${verify.code.url}")
    private String verifyCodeUrl;

    @Value("${verify.code.key}")
    private String verifyCodeKey;

    @Value("${verify.code.id}")
    private String verifyCodeId;

    @Override
    public CommonResult thirdLogin(ThirdUserInfoDomain thirdUserInfoDomain) {
        ThirdUserInfoDomainExample thirdUserInfoDomainExample = new ThirdUserInfoDomainExample();
        ThirdUserInfoDomainExample.Criteria criteria = thirdUserInfoDomainExample.createCriteria();
        criteria.andUuidEqualTo(thirdUserInfoDomain.getUuid());
        thirdUserInfoDomainExample.setDistinct(true);
        List<ThirdUserInfoDomain> thirdUserInfoDomainList = thirdUserInfoDomainMapper.selectByExample(thirdUserInfoDomainExample);
        if(thirdUserInfoDomainList != null && thirdUserInfoDomainList.size() > 0){
            ThirdUserInfoDomain thirdUserInfo = thirdUserInfoDomainList.get(0);
            UserInfoDomain userInfoDomain = userInfoDomainMapper.selectByPrimaryKey(thirdUserInfo.getUserId());
        }else{
            UserInfoDomain userInfoDomain = new UserInfoDomain();
            userInfoDomain.setPortrait(thirdUserInfoDomain.getPortrait());
            userInfoDomain.setUsername(thirdUserInfoDomain.getNickname());
            userInfoDomain.setUserType(1);
            String salt = BCrypt.gensalt();
            userInfoDomain.setSalt(salt);
            userInfoDao.insertUserInfo(userInfoDomain);
            thirdUserInfoDomain.setUserId(userInfoDomain.getId());
            thirdUserInfoDomainMapper.insertSelective(thirdUserInfoDomain);
        }
        return null;
    }

    @Override
    public CommonResult register(UserInfoVo userInfoVo) {
        CommonResult commonResult = new CommonResult();
        if(!Strings.isNullOrEmpty(userInfoVo.getPhone())){
            UserInfoDomainExample userInfoDomainExample = new UserInfoDomainExample();
            userInfoDomainExample.createCriteria().andPhoneEqualTo(userInfoVo.getPhone()).andStatusEqualTo(1);
            List<UserInfoDomain> userInfoDomains = userInfoDomainMapper.selectByExample(userInfoDomainExample);
            if(userInfoDomains != null && userInfoDomains.size() > 0){
                //已经存在该手机号跳到登录页
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该手机号已存在");
            }else{
                //发送手机号和验证码校验是否正确
                VerifyPhoneDomainExample verifyPhoneDomainExample = new VerifyPhoneDomainExample();
                verifyPhoneDomainExample.createCriteria().andPhoneEqualTo(userInfoVo.getPhone()).andVerifyCodeEqualTo(userInfoVo.getVerfication());
                List<VerifyPhoneDomain> verifyPhoneDomains = verifyPhoneDomainMapper.selectByExample(verifyPhoneDomainExample);
                if(CollectionUtils.isNotEmpty(verifyPhoneDomains)){
                    UserInfoDomain userInfoDomain = new UserInfoDomain();
                    userInfoDomain.setPhone(userInfoVo.getPhone());
                    userInfoDomain.setUsername(userInfoVo.getPhone());
                    userInfoDomain.setUserType(1);
                    String saltPassword = createPassword(userInfoVo.getPassword());
                    String[] str = saltPassword.split(",");
                    userInfoDomain.setSalt(str[0]);
                    userInfoDomain.setPassword(str[1]);
                    userInfoDomainMapper.insertSelective(userInfoDomain);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("注册成功");

                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("验证码错误");
                }
            }
        }

        return commonResult;
    }

    @Override
    public CommonResult checkPhone(String phone) {
        CommonResult commonResult = new CommonResult();
        if(!Strings.isNullOrEmpty(phone)){
            UserInfoDomainExample userInfoDomainExample = new UserInfoDomainExample();
            userInfoDomainExample.createCriteria().andPhoneEqualTo(phone).andStatusEqualTo(1);
            List<UserInfoDomain> userInfoDomainList = userInfoDomainMapper.selectByExample(userInfoDomainExample);
            if(userInfoDomainList != null && userInfoDomainList.size() > 0){
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("手机号正确");
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("手机号不存在，请注册");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("请输入手机号");
        }
        return commonResult;
    }

    @Override
    public CommonResult forgetPassword(UserInfoVo userInfoVo) {
        CommonResult commonResult = new CommonResult();
        if(userInfoVo != null){
            UserInfoDomainExample userInfoDomainExample = new UserInfoDomainExample();
            userInfoDomainExample.createCriteria().andPhoneEqualTo(userInfoVo.getPhone()).andStatusEqualTo(1);
            List<UserInfoDomain> userInfoDomainList = userInfoDomainMapper.selectByExample(userInfoDomainExample);
            if(userInfoDomainList != null && userInfoDomainList.size() > 0){
                //验证校验码
                VerifyPhoneDomainExample verifyPhoneDomainExample = new VerifyPhoneDomainExample();
                verifyPhoneDomainExample.createCriteria().andPhoneEqualTo(userInfoVo.getPhone()).andVerifyCodeEqualTo(userInfoVo.getVerfication());
                List<VerifyPhoneDomain> verifyPhoneDomains = verifyPhoneDomainMapper.selectByExample(verifyPhoneDomainExample);
                if(CollectionUtils.isNotEmpty(verifyPhoneDomains)){
                    UserInfoDomain userInfoDomain = userInfoDomainList.get(0);
                    //缺少加密的步骤
                    String saltPassword = createPassword(userInfoVo.getPassword());
                    String[] str = saltPassword.split(",");
                    userInfoDomain.setSalt(str[0]);
                    userInfoDomain.setPassword(str[1]);
                    userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("重置密码成功");
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("验证码不正确");
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该手机号未注册");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<LoginTokenVo> login(LoginUserVo loginUserVo) {
        logger.info("UserSerivceImpl login loginUserVo --{}",loginUserVo);
        CommonResult<LoginTokenVo> commonResult = new CommonResult<>();
        if(loginUserVo != null){

            //该手机号是否登录过
            List<DeviceLoginDomain> deviceLoginDomains = deviceLoginDao.selectDeviceLoginByPhone(loginUserVo.getPhone());
            if(CollectionUtils.isNotEmpty(deviceLoginDomains)){
                DeviceLoginDomain deviceLoginDomain = deviceLoginDomains.get(0);
                if(deviceLoginDomain.getDeviceId().equals(loginUserVo.getDeviceId())){
                    commonResult = phoneLogin(loginUserVo);
                }else{
                    //更换设备
                    commonResult.setFlag(CddConstant.RESULT_CHANGE_DEVICE_CODE);
                    commonResult.setMessage("更换设备登陆，请验证手机号");
                }
            }else{
                DeviceLoginDomain deviceLoginDomain = new DeviceLoginDomain();
                deviceLoginDomain.setPhone(loginUserVo.getPhone());
                deviceLoginDomain.setDeviceId(loginUserVo.getDeviceId());
                deviceLoginDomainMapper.insertSelective(deviceLoginDomain);
                commonResult = phoneLogin(loginUserVo);
            }

        }
        return commonResult;
    }

    public CommonResult<LoginTokenVo> phoneLogin(LoginUserVo loginUserVo){
        CommonResult<LoginTokenVo> commonResult = new CommonResult<>();
        //是否存在该手机号
        UserInfoDomainExample userInfoDomainExample = new UserInfoDomainExample();
        userInfoDomainExample.createCriteria().andPhoneEqualTo(loginUserVo.getPhone()).andStatusEqualTo(1);
        List<UserInfoDomain> userInfoDomainList = userInfoDomainMapper.selectByExample(userInfoDomainExample);
        if(!Strings.isNullOrEmpty(loginUserVo.getPhone())
                && !Strings.isNullOrEmpty(loginUserVo.getVerfication())){
            VerifyPhoneDomainExample verifyPhoneDomainExample = new VerifyPhoneDomainExample();
            verifyPhoneDomainExample.createCriteria().andPhoneEqualTo(loginUserVo.getPhone()).andVerifyCodeEqualTo(loginUserVo.getVerfication());
            List<VerifyPhoneDomain> verifyPhoneDomains = verifyPhoneDomainMapper.selectByExample(verifyPhoneDomainExample);
            if(CollectionUtils.isNotEmpty(verifyPhoneDomains)){

                if(userInfoDomainList != null && userInfoDomainList.size() > 0){
                    //token 待定
                    UserInfoDomain userInfoDomain = userInfoDomainList.get(0);
                    String waitToken = userInfoDomain.getId() + userInfoDomain.getSalt()+System.currentTimeMillis();
                    String token = DigestUtils.md5DigestAsHex(waitToken.getBytes());
                    UserTicketDomain userTicketDomain = new UserTicketDomain();
                    userTicketDomain.setUserId(userInfoDomain.getId());
                    userTicketDomain.setToken(token);
                    userTicketDomainMapper.insert(userTicketDomain);
                    LoginTokenVo loginTokenVo = new LoginTokenVo();
                    loginTokenVo.setUserId(userInfoDomain.getId());
                    loginTokenVo.setUserType(userInfoDomain.getUserType());
                    loginTokenVo.setToken(token);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("登录成功");
                    commonResult.setData(loginTokenVo);
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("登录失败，手机号不存在，请注册");
                }
            }
        }else{
            if(userInfoDomainList != null && userInfoDomainList.size() > 0){
                UserInfoDomain userInfoDomain = userInfoDomainList.get(0);
                String authPasswd = createPassword(loginUserVo.getPassword());
                String[] passwd = authPasswd.split(",");
                if(passwd[0].equals(userInfoDomain.getSalt()) && passwd[1].equals(userInfoDomain.getPassword())){
                    String waitToken = userInfoDomain.getId() + userInfoDomain.getSalt()+System.currentTimeMillis();
                    String token = DigestUtils.md5DigestAsHex(waitToken.getBytes());
                    LoginTokenVo loginTokenVo = new LoginTokenVo();
                    loginTokenVo.setUserId(userInfoDomain.getId());
                    loginTokenVo.setUserType(userInfoDomain.getUserType());
                    loginTokenVo.setToken(token);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("登录成功");
                    commonResult.setData(loginTokenVo);
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("账号或密码不正确");
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("登录失败，手机号不存在，请注册");
            }
        }
        return commonResult;
    }

    @Override
    public CommonResult agreeCompany(Long userId, Long companyId) {
        CommonResult commonResult = new CommonResult();
        try{
            if(userId != null && companyId != null){
                CompanyUserMappingDomain companyUserMappingDomain = new CompanyUserMappingDomain();
                companyUserMappingDomain.setUserId(userId);
                companyUserMappingDomain.setCompanyId(companyId);
                companyUserMappingDomain.setAgree(1);
                companyUserMappingDomain.setUpdateTs(new Date());
                companyUserMappingDomainMapper.updateByPrimaryKeySelective(companyUserMappingDomain);
                UserInfoDomain userInfoDomain = new UserInfoDomain();
                userInfoDomain.setId(userId);
                userInfoDomain.setUserType(2);
                userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("同意加入该公司");
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不为空");
            }

        } catch (Exception e){
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }

        return commonResult;
    }

    @Override
    public CommonResult bindPhone(Long userId, String phone) {
        CommonResult commonResult = new CommonResult();
        if(userId != null && !Strings.isNullOrEmpty(phone)){
            UserInfoDomainExample userInfoDomainExample = new UserInfoDomainExample();
            userInfoDomainExample.createCriteria().andPhoneEqualTo(phone).andStatusEqualTo(1);
            List<UserInfoDomain> userInfoDomainList = userInfoDomainMapper.selectByExample(userInfoDomainExample);
            if(userInfoDomainList != null && userInfoDomainList.size() > 0){
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该手机号已注册");
            }else{
                UserInfoDomain userInfoDomain = new UserInfoDomain();
                userInfoDomain.setId(userId);
                userInfoDomain.setPhone(phone);
                userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("绑定手机号成功");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }

    @Override
    public CommonResult findFollow(FollowConditionVo followConditionVo) {
        CommonResult commonResult = new CommonResult();
        if(followConditionVo != null){
            Integer followType = followConditionVo.getFollowType();
            if(followType != null && followType > 0){
                if(followType == 1 || followType == 2 || followType == 3){
                    List<FollowHouseVo> followHouseVos = followInfoDao.findFollowHouse(followConditionVo);
                    commonResult.setData(followHouseVos);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("查询成功");
                }else if(followType == 4){
                    List<FollowSellParkVo> followSellParkVos = followInfoDao.findFollowSellPark(followConditionVo);
                    commonResult.setData(followSellParkVos);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("查询成功");
                }else if(followType == 5){
                    List<FollowLeaseParkVo> followLeaseParkVos = followInfoDao.findFollowLeasePark(followConditionVo);
                    commonResult.setData(followLeaseParkVos);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("查询成功");
                }else{
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("查询成功，没有对应的关注类型");
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("请输入对应的关注类型");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("请输入对应的参数");
        }
        return commonResult;
    }

    @Override
    public CommonResult followInfo(FollowInfoDomain followInfoDomain) {
        CommonResult commonResult = new CommonResult();
        try {
            FollowInfoDomainExample followInfoDomainExample = new FollowInfoDomainExample();
            followInfoDomainExample.createCriteria().andFollowIdEqualTo(followInfoDomain.getFollowId())
                    .andUserIdEqualTo(followInfoDomain.getUserId()).andFollowTypeEqualTo(followInfoDomain.getFollowType());
            List<FollowInfoDomain> followInfoDomains = followInfoDomainMapper.selectByExample(followInfoDomainExample);
            if(followInfoDomains != null && followInfoDomains.size() > 0){
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("已关注");
            }else{
                followInfoDomainMapper.insertSelective(followInfoDomain);
                JSONObject json = new JSONObject();
                json.put("followId",followInfoDomain.getId());
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("关注成功");
                commonResult.setData(json);
            }

        }catch (Exception e){
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }

        return commonResult;
    }

    @Override
    public CommonResult cancelFollow(FollowInfoVo followInfoVo) {
        CommonResult commonResult = new CommonResult();
        if(followInfoVo != null){
            String followIds = followInfoVo.getFollowIds();
            if(!Strings.isNullOrEmpty(followIds)){
                followInfoDao.deleteFollowInfos(followInfoVo.getUserId(),followIds);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("取消关注成功");
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不能为空");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult isFollow(IsFollowVo isFollowVo) {

        CommonResult commonResult = new CommonResult();
        if(isFollowVo != null){
            List<Long> ids = followInfoDao.isFollow(isFollowVo);
            JSONObject data = new JSONObject();
            if(ids != null && ids.size() > 0){
                data.put("isFollow",true);
                data.put("followId",ids.get(0));
            }else{
                data.put("isFollow",false);
                data.put("followId","");
            }
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("请输入对应的参数");
        }
        return commonResult;
    }

    @Override
    public List<CommonDictDomain> findDictInfo(String dictName) {
        List<CommonDictDomain> commonDictDomainList = new ArrayList<>();
        CommonDictDomainExample commonDictDomainExample = new CommonDictDomainExample();
        if(!Strings.isNullOrEmpty(dictName)){
            String[] dictArr = dictName.split(",");
            List<String> dictList = Arrays.asList(dictArr);
            commonDictDomainExample.createCriteria().andDictNameIn(dictList);
        }
        commonDictDomainList = commonDictDomainMapper.selectByExample(commonDictDomainExample);
        return commonDictDomainList;
    }

    @Override
    public CommonResult authenticationBroker(ApplyBrokerInfoVo applyBrokerInfoVo) {
        CommonResult commonResult = new CommonResult();
        if(applyBrokerInfoVo != null){
            ApplyBrokerInfoDomainExample applyBrokerInfoDomainExample = new ApplyBrokerInfoDomainExample();
            applyBrokerInfoDomainExample.createCriteria().andUserIdEqualTo(applyBrokerInfoVo.getUserId())
                            .andApplyTypeNotEqualTo(3);
            List<ApplyBrokerInfoDomain> applyBrokerInfoDomains = applyBrokerInfoDomainMapper.selectByExample(applyBrokerInfoDomainExample);
            if(applyBrokerInfoDomains != null && applyBrokerInfoDomains.size() > 0){
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该用户已申请");
            }else{
                VerifyPhoneDomainExample verifyPhoneDomainExample = new VerifyPhoneDomainExample();
                verifyPhoneDomainExample.createCriteria()
                        .andPhoneEqualTo(applyBrokerInfoVo.getPhone())
                        .andVerifyCodeEqualTo(applyBrokerInfoVo.getVerfication());
                List<VerifyPhoneDomain> verifyPhoneDomains = verifyPhoneDomainMapper.selectByExample(verifyPhoneDomainExample);
                if(verifyPhoneDomains != null && verifyPhoneDomains.size() > 0){
                    ApplyBrokerInfoDomain applyBrokerInfoDomain = new ApplyBrokerInfoDomain();
                    BeanUtils.copyProperties(applyBrokerInfoVo,applyBrokerInfoDomain);
                    applyBrokerInfoDomainMapper.insertSelective(applyBrokerInfoDomain);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("申请经纪人成功");
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("验证失败");
                }

            }

        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<List<ApplyBrokerInfoDomain>> findBroker(Long userId) {
        CommonResult<List<ApplyBrokerInfoDomain>> commonResult = new CommonResult<List<ApplyBrokerInfoDomain>>();
        ApplyBrokerInfoDomainExample applyBrokerInfoDomainExample = new ApplyBrokerInfoDomainExample();
        applyBrokerInfoDomainExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(1);
        List<ApplyBrokerInfoDomain> applyBrokerInfoDomains = applyBrokerInfoDomainMapper.selectByExample(applyBrokerInfoDomainExample);
        if(applyBrokerInfoDomains != null && applyBrokerInfoDomains.size() > 0){
            ApplyBrokerInfoDomain applyBrokerInfoDomain = applyBrokerInfoDomains.get(0);
            int brokerType = applyBrokerInfoDomain.getBrokerType();
            if(brokerType == CddConstant.MANAGE_BROKER_TYPE){
                ApplyBrokerInfoDomainExample applyBrokerInfoExample = new ApplyBrokerInfoDomainExample();
                applyBrokerInfoDomainExample.createCriteria().andCompanyNameEqualTo(applyBrokerInfoDomain.getCompanyName())
                        .andStatusEqualTo(1).andUserIdNotEqualTo(applyBrokerInfoDomain.getUserId());
                List<ApplyBrokerInfoDomain> applyBrokerDomains = applyBrokerInfoDomainMapper.selectByExample(applyBrokerInfoExample);
                commonResult.setFlag(1);
                commonResult.setMessage("查询成功");
                commonResult.setData(applyBrokerDomains);
            }else{
                commonResult.setFlag(0);
                commonResult.setMessage("查询失败");
            }
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("查询失败");
        }
        return commonResult;
    }

    @Override
    public CommonResult agreeBroker(Long brokerId) {
        CommonResult commonResult = new CommonResult();
        if(brokerId != null){
            ApplyBrokerInfoDomain applyBrokerInfoDomain = new ApplyBrokerInfoDomain();
            applyBrokerInfoDomain.setId(brokerId);
            applyBrokerInfoDomain.setApplyType(2);
            applyBrokerInfoDomainMapper.updateByPrimaryKeySelective(applyBrokerInfoDomain);
            ApplyBrokerInfoDomain applyBroker = applyBrokerInfoDomainMapper.selectByPrimaryKey(brokerId);
            UserInfoDomain userInfoDomain = new UserInfoDomain();
            userInfoDomain.setId(applyBroker.getUserId());
            userInfoDomain.setUserType(2);
            userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("加入成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<String> verifyCode(String phone) {
        CommonResult commonResult = new CommonResult();
        if(!Strings.isNullOrEmpty(phone) &&
                !Strings.isNullOrEmpty(verifyCodeUrl) &&
                !Strings.isNullOrEmpty(verifyCodeKey)){
            String verifyCode = String
                    .valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
            String tplValue = null;
            try {
                tplValue = URLEncoder.encode("#code#="+verifyCode+"&#m#=5","UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            StringBuffer uri = new StringBuffer().append(verifyCodeUrl)
                    .append("?mobile=").append(phone).append("&tpl_id=").append(verifyCodeId)
                    .append("&tpl_value=").append(tplValue).append("&key=").append(verifyCodeKey);
            String response = HttpClientUtils.getInstance().doGetWithJsonResult(uri.toString());
            if(!Strings.isNullOrEmpty(response)){
                JSONObject res = JSONObject.parseObject(response);
                Integer flag = res.getInteger("error_code");
                if(flag == 0){
                    //TODO 把对应的手机号和验证码存入库中
                    VerifyPhoneDomain verifyPhoneDomain = new VerifyPhoneDomain();
                    verifyPhoneDomain.setPhone(phone);
                    verifyPhoneDomain.setVerifyCode(verifyCode);
                    verifyPhoneDomainMapper.insertSelective(verifyPhoneDomain);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("验证码发送成功");
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("验证码发送失败");
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("验证码发送失败");
            }

        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult changeDevice(ChangeDeviceVo changeDeviceVo) {
        CommonResult commonResult = new CommonResult();
        if(changeDeviceVo != null){
            if(changeDeviceVo.getPhone().equals(changeDeviceVo.getLoginPhone())){
                VerifyPhoneDomainExample verifyPhoneDomainExample = new VerifyPhoneDomainExample();
                verifyPhoneDomainExample.createCriteria().andPhoneEqualTo(changeDeviceVo.getPhone()).andVerifyCodeEqualTo(changeDeviceVo.getVerfication());
                List<VerifyPhoneDomain> verifyPhoneDomains = verifyPhoneDomainMapper.selectByExample(verifyPhoneDomainExample);
                if(CollectionUtils.isNotEmpty(verifyPhoneDomains)){
                    DeviceLoginDomain deviceLoginDomain = new DeviceLoginDomain();
                    deviceLoginDomain.setPhone(changeDeviceVo.getPhone());
                    deviceLoginDomain.setDeviceId(changeDeviceVo.getDeviceId());
                    deviceLoginDomainMapper.insertSelective(deviceLoginDomain);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("验证成功请登录");
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("验证码不正确");
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("登录手机号和验证手机号必须相同");
            }
        }
        return commonResult;
    }

    @Override
    public CommonResult<SingleUserInfoVo> findUserInfo(Long userId) {
        CommonResult<SingleUserInfoVo> commonResult = new CommonResult<>();
        if(userId != null){
            SingleUserInfoVo singleUserInfoVo = userInfoDao.findUserInfoById(userId);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(singleUserInfoVo);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult findApplyBroker(Long userId) {
        CommonResult commonResult = new CommonResult();
        if(userId != null){
            ApplyBrokerInfoDomainExample applyBrokerInfoDomainExample = new ApplyBrokerInfoDomainExample();
            applyBrokerInfoDomainExample.createCriteria().andUserIdEqualTo(userId).andApplyTypeNotEqualTo(3);
            List<ApplyBrokerInfoDomain> applyBrokerInfoDomains = applyBrokerInfoDomainMapper.selectByExample(applyBrokerInfoDomainExample);
            if(applyBrokerInfoDomains != null && applyBrokerInfoDomains.size() > 0){
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("申请已存在");
            }else{
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("该用户未申请");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }

    @Override
    public CommonResult companyTeam(Long userId,Integer userType) {
        CommonResult commonResult = new CommonResult();
        List<SingleUserBrokerVo> singleUserBrokerVos = userInfoDao.findUserBrokerByUserId(userId,userType);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(singleUserBrokerVos);
        return commonResult;
    }

    @Override
    public CommonResult updateUser(UserParamVo userParamVo) {
        CommonResult commonResult = new CommonResult();
        if(userParamVo != null && userParamVo.getUserId() != null){
            UserInfoDomain userInfoDomain = new UserInfoDomain();
            userInfoDomain.setId(userParamVo.getUserId());
            userInfoDomain.setUsername(userParamVo.getUsername());
            userInfoDomain.setPortrait(userParamVo.getPortrait());
            userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("修改成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult allBroker(Integer pageNo, Integer pageSize) {
        CommonResult commonResult = new CommonResult();
        int from = (pageNo-1) * pageSize;
        List<UserBrokerVo> userBrokerVos = applyBrokerInfoDao.userBrokerList(from,pageSize);
        List<UserBrokerVo> userBrokerList = new ArrayList<>();
        if(userBrokerVos != null && userBrokerVos.size() > 0){
            for(UserBrokerVo userBrokerVo:userBrokerVos){
                int count = houseInfoDao.selectHouseCountByUserId(userBrokerVo.getUserId());
                userBrokerVo.setHouseCount(count);
                userBrokerList.add(userBrokerVo);
            }
        }
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(userBrokerList);
        return commonResult;
    }

    @Override
    public CommonResult home() {
        logger.info("UserServiceImpl home start");
        CommonResult commonResult = new CommonResult();
        try {
            HomePageVo homePageVo = new HomePageVo();
            //所有房源的数量
            int allHouseCount = houseInfoDao.selectAllHouseCount();
            homePageVo.setHouseCount(allHouseCount);
            int allUserCount = userInfoDao.selectAllUserCount();
            homePageVo.setClientCount(allUserCount);

            List<HouseInfoDomainVo> houseInfoDomainVos = houseInfoDao.selectHouseInfoListByLike();
            homePageVo.setHouseInfoDomainVos(houseInfoDomainVos);

            List<EnterpriseInfoVo> enterpriseInfoVos = enterpriseInfoDao.selectEnterpriseInfoListRand();
            homePageVo.setEnterpriseInfoDomains(enterpriseInfoVos);
            List<ParkInfoVo> parkInfoVos = sellParkDao.selectSellParkInfoRand();
            homePageVo.setParkInfoVos(parkInfoVos);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(homePageVo);
        }catch (Exception e){
            logger.error("UserServiceImpl home exception");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("查询失败");
        }

        return commonResult;
    }

    public String createPassword(String password){
        String salt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw(password, salt);
        return salt+","+hashed;
    }


}
