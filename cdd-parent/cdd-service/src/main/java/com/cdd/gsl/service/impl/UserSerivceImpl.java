package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.UserService;
import com.cdd.gsl.vo.FollowInfoVo;
import com.cdd.gsl.vo.LoginTokenVo;
import com.cdd.gsl.vo.LoginUserVo;
import com.cdd.gsl.vo.UserInfoVo;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserSerivceImpl implements UserService {


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
                if(true){
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
                if(true){
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

        CommonResult<LoginTokenVo> commonResult = new CommonResult<>();
        if(loginUserVo != null){
            UserInfoDomainExample userInfoDomainExample = new UserInfoDomainExample();
            userInfoDomainExample.createCriteria().andPhoneEqualTo(loginUserVo.getPhone()).andStatusEqualTo(1);
            List<UserInfoDomain> userInfoDomainList = userInfoDomainMapper.selectByExample(userInfoDomainExample);
            if(!Strings.isNullOrEmpty(loginUserVo.getVerfication())){
                //验证码登录没有密码 当手机号不存在 自己注册一个账号
                //校验验证码
                if(true){

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
                    String dataPassword = BCrypt.hashpw(loginUserVo.getPassword(),userInfoDomain.getSalt());
                    if(dataPassword.equals(userInfoDomain.getPassword())){
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
    public CommonResult followInfo(FollowInfoDomain followInfoDomain) {
        CommonResult commonResult = new CommonResult();
        try {
            followInfoDomainMapper.insertSelective(followInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("关注成功");
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
    public List<CommonDictDomain> findDictInfo(String dictName) {
        List<CommonDictDomain> commonDictDomainList = new ArrayList<>();
        CommonDictDomainExample commonDictDomainExample = new CommonDictDomainExample();
        if(!Strings.isNullOrEmpty(dictName)){
            commonDictDomainExample.createCriteria().andDictNameEqualTo(dictName);
        }
        commonDictDomainList = commonDictDomainMapper.selectByExample(commonDictDomainExample);
        return commonDictDomainList;
    }

    @Override
    public CommonResult authenticationBroker(ApplyBrokerInfoDomain applyBrokerInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(applyBrokerInfoDomain != null){
            applyBrokerInfoDomainMapper.insertSelective(applyBrokerInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("申请经纪人成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    public String createPassword(String password){
        String salt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw(password, salt);
        return salt+","+hashed;
    }
}
