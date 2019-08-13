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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

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
    private MessageInfoDomainMapper messageInfoDomainMapper;

    @Autowired
    private MessageInfoDao messageInfoDao;

    @Autowired
    private EntrustInfoDao entrustInfoDao;

    @Autowired
    private CheckPhoneDomainMapper checkPhoneDomainMapper;

    @Autowired
    private CurrencyInfoDomainMapper currencyInfoDomainMapper;

    @Autowired
    private  UserCurrencyMappingDomainMapper userCurrencyMappingDomainMapper;

    @Autowired
	private SlideInfoDao slideInfoDao;

    @Autowired
    private EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    private ConsumeRecordDomainMapper consumeRecordDomainMapper;

    @Autowired
    private LeagueInfoDomainMapper leagueInfoDomainMapper;

    @Autowired
    private SearchCityUserInfoMapper searchCityUserInfoMapper;

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
                    userInfoDomain.setIntegral(CddConstant.AWARD_INTEGRAL);
                    userInfoDomainMapper.insertSelective(userInfoDomain);
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

                    JSONObject loginToken = (JSONObject)JSONObject.toJSON(loginTokenVo);
                    loginToken.put("flag",1);
                    logger.info("UserServiceImpl register success -{}",loginToken.toString());
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("注册成功");
                    commonResult.setData(loginToken);
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
        logger.info("UserSerivceImpl login loginUserVo deviceId--{} passwod --{} phone--{}",
                loginUserVo.getDeviceId(),loginUserVo.getPassword(),loginUserVo.getPhone());
        CommonResult<LoginTokenVo> commonResult = new CommonResult<>();
        if(loginUserVo != null){

            //该手机号是否登录过
            /*List<DeviceLoginDomain> deviceLoginDomains = deviceLoginDao.selectDeviceLoginByPhone(loginUserVo.getPhone());
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
            }*/
            commonResult = phoneLogin(loginUserVo);

        }
        return commonResult;
    }

    @Override
    public CommonResult logout(String phone) {
        CommonResult commonResult = new CommonResult();
        if(!Strings.isNullOrEmpty(phone)){
            DeviceLoginDomainExample deviceLoginDomainExample = new DeviceLoginDomainExample();
            deviceLoginDomainExample.createCriteria().andPhoneEqualTo(phone);
            deviceLoginDomainMapper.deleteByExample(deviceLoginDomainExample);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("退出成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }

    public CommonResult phoneLogin(LoginUserVo loginUserVo){
        CommonResult commonResult = new CommonResult<>();
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

                UserInfoDomain userInfoDomain = new UserInfoDomain();
                int flag = 0;
                if(CollectionUtils.isEmpty(userInfoDomainList)){

                    userInfoDomain.setPhone(loginUserVo.getPhone());
                    userInfoDomain.setUsername(loginUserVo.getPhone());
                    userInfoDomain.setUserType(1);
                    String saltPassword = createPassword("123456");
                    String[] str = saltPassword.split(",");
                    userInfoDomain.setSalt(str[0]);
                    userInfoDomain.setPassword(str[1]);
                    userInfoDomain.setIntegral(CddConstant.AWARD_INTEGRAL);
                    userInfoDomainMapper.insertSelective(userInfoDomain);
                    flag = 1;
                }else{
                    userInfoDomain = userInfoDomainList.get(0);
                }

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
                JSONObject loginToken = (JSONObject)JSONObject.toJSON(loginTokenVo);
                if(flag == 1){
                    loginToken.put("flag",flag);
                }
                logger.info("UserServiceImpl phone register token -{}",loginToken.toString());
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("登录成功");
                commonResult.setData(loginToken);
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("验证码不正确");
            }
        }else{
            if(userInfoDomainList != null && userInfoDomainList.size() > 0){
                UserInfoDomain userInfoDomain = userInfoDomainList.get(0);
                String hashed = BCrypt.hashpw(loginUserVo.getPassword(), userInfoDomain.getSalt());
                if(hashed.equals(userInfoDomain.getPassword())){
                    String waitToken = userInfoDomain.getId() + userInfoDomain.getSalt()+System.currentTimeMillis();
                    String token = DigestUtils.md5DigestAsHex(waitToken.getBytes());
                    LoginTokenVo loginTokenVo = new LoginTokenVo();
                    loginTokenVo.setUserId(userInfoDomain.getId());
                    loginTokenVo.setUserType(userInfoDomain.getUserType());
                    loginTokenVo.setToken(token);
                    UserTicketDomain userTicketDomain = new UserTicketDomain();
                    userTicketDomain.setUserId(userInfoDomain.getId());
                    userTicketDomain.setToken(token);
                    userTicketDomainMapper.insert(userTicketDomain);
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
    public CommonResult findBrokerDetail(Long userId) {
        CommonResult commonResult = new CommonResult();
        ApplyBrokerInfoDomainExample applyBrokerInfoDomainExample = new ApplyBrokerInfoDomainExample();
        applyBrokerInfoDomainExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(1).andApplyTypeEqualTo(1);
        applyBrokerInfoDomainExample.setOrderByClause("create_ts desc");
        List<ApplyBrokerInfoDomain> applyBrokerInfoDomains = applyBrokerInfoDomainMapper.selectByExample(applyBrokerInfoDomainExample);
        ApplyBrokerInfoDomain applyBrokerInfoDomain = null;
        if(applyBrokerInfoDomains != null && applyBrokerInfoDomains.size() > 0){
            applyBrokerInfoDomain = applyBrokerInfoDomains.get(0);
        }

        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(applyBrokerInfoDomain);
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
            userInfoDomain.setServiceArea(userParamVo.getServiceArea());
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
    public CommonResult allBroker(Integer pageNo, Integer pageSize,String param) {
        CommonResult commonResult = new CommonResult();
        int from = (pageNo-1) * pageSize;
        List<UserBrokerVo> userBrokerVos = applyBrokerInfoDao.userBrokerList(from,pageSize,param);
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

    @Override
    public CommonResult brokerDetail(Long userId) {
        logger.info("UserSerivceImpl brokerDetail userId:{}",userId);
        CommonResult commonResult = new CommonResult();
        if(userId != null){
            SingleUserInfoVo singleUserInfoVo = userInfoDao.findUserInfoById(userId);
            BrokerHouseInfoVo brokerHouseInfoVo = new BrokerHouseInfoVo();
            BeanUtils.copyProperties(singleUserInfoVo,brokerHouseInfoVo);
            //厂房数量
            int plantCount = houseInfoDao.selectHouseCountByUserIdAndHouseUseType(userId,1);
            //仓库数量
            int storageCount = houseInfoDao.selectHouseCountByUserIdAndHouseUseType(userId,2);
            //土地数量
            int landCount = houseInfoDao.selectHouseCountByUserIdAndHouseUseType(userId,3);
            brokerHouseInfoVo.setPlantCount(plantCount);
            brokerHouseInfoVo.setStorageCount(storageCount);
            brokerHouseInfoVo.setLandCount(landCount);
            //TODO 等设计图
            List<HouseInfoDomainVo> houseInfoDomainVos = houseInfoDao.selectHouseInfoListByRecommend(userId);
            brokerHouseInfoVo.setHouseList(houseInfoDomainVos);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(brokerHouseInfoVo);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("查询成功");
        }


        return commonResult;
    }

    @Override
    public CommonResult updateBroker(ApplyBrokerInfoDomain applyBrokerInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(applyBrokerInfoDomain != null){
            applyBrokerInfoDomainMapper.updateByPrimaryKeySelective(applyBrokerInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("修改成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult messageList(MessageConditionVo messageConditionVo) {
        CommonResult commonResult = new CommonResult();
        if(messageConditionVo.getUserId() != null){
            List<MessageVo> messageInfoDomains = messageInfoDao.messageList(messageConditionVo);
            if(CollectionUtils.isNotEmpty(messageInfoDomains)){
                for(MessageVo messageVo:messageInfoDomains){
                    if(messageVo.getMessageType().equals("chat")){
                        SingleUserInfoVo singleUserInfoVo = userInfoDao.findUserInfoById(messageVo.getSendUserId());
                        messageVo.setSendUser(singleUserInfoVo);
                    }
                }
            }
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(messageInfoDomains);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult messageDetail(Long messageId) {

        CommonResult commonResult = new CommonResult();
        if(messageId != null){
            MessageInfoDomain messageInfoDomain = messageInfoDomainMapper.selectByPrimaryKey(messageId);
            MessageInfoVo messageInfoVo = new MessageInfoVo();

            HouseInfoDomainVo houseInfoDemainVo = houseInfoDao.selectHouseInfoListById(messageInfoDomain.getHouseId());
            List<EntrustInfoVo> entrustInfoVos = entrustInfoDao.findEntrustInfoById(messageInfoDomain.getEntrustId());
            messageInfoVo.setHouseInfo(houseInfoDemainVo);
            messageInfoVo.setEntrustInfo(entrustInfoVos.get(0));
            if(messageInfoDomain.getIsRead() == 0){
                messageInfoDao.updateMessageRead(messageInfoDomain.getId());
            }

            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(messageInfoVo);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult messageUnreadCount(Long userId) {
        CommonResult commonResult = new CommonResult();
        if(userId != null){
            int unreadCount = messageInfoDao.countUnReadMessageCount(userId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("unreadCount",unreadCount);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(jsonObject);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult checkMobile(CheckPhoneDomain checkPhoneDomain) {
        CommonResult commonResult = new CommonResult();
        if(checkPhoneDomain != null){
            checkPhoneDomainMapper.insertSelective(checkPhoneDomain);
            JSONObject jsonObject = new JSONObject();
            String phone = "";
            if(checkPhoneDomain.getType().equals("house")){
                HouseInfoDetailVo houseInfoDetailVo = houseInfoDao.selectHouseInfoById(checkPhoneDomain.getInfoId());
                phone = houseInfoDetailVo.getPhone();
            }else if(checkPhoneDomain.getType().equals("entrust")){
                List<EntrustInfoVo> entrustInfoVos = entrustInfoDao.findEntrustInfoById(checkPhoneDomain.getInfoId());
                if(entrustInfoVos != null && entrustInfoVos.size() > 0){
                    phone = entrustInfoVos.get(0).getPhone();
                }
            }

            jsonObject.put("phone",phone);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
            commonResult.setData(jsonObject);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("添加失败");
        }
        return commonResult;
    }

    @Override
    public CommonResult buyCurrency(UserCurrencyMappingDomain userCurrencyMappingDomain) {
        CommonResult commonResult = new CommonResult();
        if(userCurrencyMappingDomain != null){
            UserInfoDomainExample userInfoDomainExample = new UserInfoDomainExample();
            userInfoDomainExample.createCriteria().andStatusEqualTo(1).andIdEqualTo(userCurrencyMappingDomain.getUserId());
            List<UserInfoDomain> userInfoDomainList = userInfoDomainMapper.selectByExample(userInfoDomainExample);
            CurrencyInfoDomainExample currencyInfoDomainExample = new CurrencyInfoDomainExample();
            currencyInfoDomainExample.createCriteria().andIdEqualTo(userCurrencyMappingDomain.getCurrencyId()).andStatusEqualTo(1);
            List<CurrencyInfoDomain> currencyInfoDomainList = currencyInfoDomainMapper.selectByExample(currencyInfoDomainExample);
            if(CollectionUtils.isNotEmpty(userInfoDomainList) && CollectionUtils.isNotEmpty(currencyInfoDomainList)){
                UserInfoDomain userInfoDomain = userInfoDomainList.get(0);
                CurrencyInfoDomain currencyInfoDomain = currencyInfoDomainList.get(0);
                if(userInfoDomain.getIntegral() < currencyInfoDomain.getIntegral()){
                    commonResult.setFlag(2);
                    commonResult.setMessage("您的币余额不足，请充值");
                }else{
                    UserInfoDomain userInfo = new UserInfoDomain();
                    userInfo.setId(userInfoDomain.getId());
                    userInfo.setIntegral(userInfoDomain.getIntegral()-currencyInfoDomain.getIntegral());
                    userInfoDomainMapper.updateByPrimaryKeySelective(userInfo);
                    userCurrencyMappingDomainMapper.insertSelective(userCurrencyMappingDomain);
                    ConsumeRecordDomain consumeRecordDomain = new ConsumeRecordDomain();
                    consumeRecordDomain.setTitle(userInfoDomain.getUsername());
                    consumeRecordDomain.setUserId(userInfoDomain.getId());
                    consumeRecordDomain.setAction(CddConstant.CONSUME_RECORD_CONSUME);
                    consumeRecordDomain.setIntegral(currencyInfoDomain.getIntegral());
                    consumeRecordDomain.setType(CddConstant.CONSUME_RECORD_TYPE_MEMBER);
                    consumeRecordDomainMapper.insertSelective(consumeRecordDomain);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("购买成功");
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("没有对应的数据");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
  	public CommonResult slideList(String city) {
        CommonResult commonResult = new CommonResult();
        List<SlideInfoDomain> slideInfoDomainList = slideInfoDao.slideInfoList(city);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(slideInfoDomainList);
        return commonResult;
    }

    @Override
    public CommonResult createLeague(LeagueInfoParamVo leagueInfoParamVo) {
        logger.info("UserServerImpl createLeague leagueInfoParamVo - {}",leagueInfoParamVo.toString());
        CommonResult commonResult = new CommonResult();
        try {
            LeagueInfoDomain leagueInfoDomain = new LeagueInfoDomain();
            leagueInfoDomain.setCity(leagueInfoParamVo.getCity());
            leagueInfoDomain.setName(leagueInfoParamVo.getName());
            leagueInfoDomain.setPhone(leagueInfoParamVo.getPhone());
            leagueInfoDomainMapper.insertSelective(leagueInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        } catch (Exception e){
            logger.error("UserServerImpl createLeague error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult userList(UserAdminConditionVo userConditionVo) {
        CommonResult commonResult = new CommonResult();
        JSONObject json = new JSONObject();
        int count = userInfoDao.userCount(userConditionVo);
        List<UserInfoDemainVo> userInfoDomainList = userInfoDao.userList(userConditionVo);
        json.put("total",count);
        json.put("userList",userInfoDomainList);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }

    @Override
    public CommonResult deleteUser(Long userId) {
        CommonResult commonResult = new CommonResult();
        if(userId != null){
            UserInfoDomain userInfoDomain = new UserInfoDomain();
            userInfoDomain.setId(userId);
            userInfoDomain.setStatus(0);
            userInfoDomain.setUpdateTs(new Date());
            userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("删除成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult recoverUser(Long userId) {
        CommonResult commonResult = new CommonResult();
        if(userId != null){
            UserInfoDomain userInfoDomain = new UserInfoDomain();
            userInfoDomain.setId(userId);
            userInfoDomain.setStatus(1);
            userInfoDomain.setUpdateTs(new Date());
            userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
       
            commonResult.setMessage("恢复成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult addUser(UserInfoDomain userInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(userInfoDomain != null){
            userInfoDomainMapper.insertSelective(userInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateCurrency(UserInfoDomain userInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(userInfoDomain != null){
            userInfoDao.updateUserintegralById(userInfoDomain.getId(),userInfoDomain.getIntegral());
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }


    @Override
    public CommonResult createSearchCity(SearchCityUserInfo searchCityUserInfo) {
        CommonResult commonResult = new CommonResult();
        logger.info("UserServiceImpl createSearchCity userId-{},cityName-{}",searchCityUserInfo.getUserId(),searchCityUserInfo.getCityName());
        try{
            if(searchCityUserInfo != null && searchCityUserInfo.getUserId() != null
                    && StringUtils.isEmpty(searchCityUserInfo.getCityName())){
                SearchCityUserInfoExample searchCityUserInfoExample = new SearchCityUserInfoExample();
                searchCityUserInfoExample.createCriteria().andCityNameEqualTo(searchCityUserInfo.getCityName())
                        .andUserIdEqualTo(searchCityUserInfo.getUserId()).andStatusEqualTo(1);
                List<SearchCityUserInfo> searchCityUserInfoList = searchCityUserInfoMapper.selectByExample(searchCityUserInfoExample);
                if(CollectionUtils.isEmpty(searchCityUserInfoList)){
                    searchCityUserInfoMapper.insertSelective(searchCityUserInfo);
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("添加成功");
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("已存在");
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不完整");
            }
        }catch (Exception e){
            logger.error("UserServiceImpl createSearchCity error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }

        return commonResult;
    }
}
