package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.HouseAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.DateUtil;
import com.cdd.gsl.common.util.HttpClientUtils;
import com.cdd.gsl.common.util.MailUtil;
import com.cdd.gsl.common.util.PasswordUtil;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.vo.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminInfoDao adminInfoDao;

    @Autowired
    private AdminTicketDomainMapper adminTicketDomainMapper;

    @Autowired
    private ApplyBrokerInfoDao applyBrokerInfoDao;

    @Autowired
    private ApplyBrokerInfoDomainMapper applyBrokerInfoDomainMapper;

    @Autowired
    private UserInfoDomainMapper userInfoDomainMapper;

    @Autowired
    private HouseInfoDao houseInfoDao;

    @Autowired
    private RoleInfoDomainMapper roleInfoDomainMapper;

    @Autowired
    private AdminInfoDomainMapper adminInfoDomainMapper;

    @Autowired
    private InformHouseRecordDomainMapper informHouseRecordDomainMapper;

    @Autowired
    private InformRecordDao informRecordDao;

    @Autowired
    private ConsumeRecordDomainMapper consumeRecordDomainMapper;

    @Value("${verify.code.url}")
    private String verifyCodeUrl;

    @Value("${verify.code.key}")
    private String verifyCodeKey;

    @Value("${pass.broker.code.id}")
    private String passBrokerCodeId;

    @Override
    public CommonResult doLogin(String username, String password) throws Exception {
        CommonResult commonResult = new CommonResult();
        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            List<AdminInfoDomain> adminInfoDomains = adminInfoDao.selectAdminByUsernameAndPassword(username,password);
            if(adminInfoDomains != null && adminInfoDomains.size() > 0){
                AdminInfoDomain adminInfoDomain = adminInfoDomains.get(0);
                String waitToken = adminInfoDomain.getId() + adminInfoDomain.getSlat()+System.currentTimeMillis();
                String token = DigestUtils.md5DigestAsHex(waitToken.getBytes());
                AdminTicketDomain userTicketDomain = new AdminTicketDomain();
                userTicketDomain.setAdminId(adminInfoDomain.getId());
                userTicketDomain.setToken(token);
                adminTicketDomainMapper.insert(userTicketDomain);
                JSONObject tokenJson = new JSONObject();
                tokenJson.put("token",token);
                commonResult.setFlag(1);
                commonResult.setMessage("登录成功");
                commonResult.setData(tokenJson);
            }else{
                commonResult.setFlag(0);
                commonResult.setMessage("没有对应的用户");
            }
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("参数不正确");
        }

        return commonResult;
    }

    @Override
    public String getPasswordByUserName(String username) {
        return adminInfoDao.selectPasswordByUsername(username);
    }

    @Override
    public List<MenuInfoVo> getPermissionByUserName(String username) {
        return adminInfoDao.selectMenuListByUsername(username);
    }

    @Override
    public AdminInfoDomain getAdminByUsernameAndPassword(String username, String password) {
        List<AdminInfoDomain> adminInfoDomains = adminInfoDao.selectAdminByUsernameAndPassword(username,password);
        AdminInfoDomain adminInfoDomain = new AdminInfoDomain();
        if(adminInfoDomains != null && adminInfoDomains.size() > 0){
            adminInfoDomain = adminInfoDomains.get(0);
        }
        return adminInfoDomain;
    }

    @Override
    public CommonResult info(String token) {
        CommonResult commonResult = new CommonResult();
        if(!StringUtils.isEmpty(token)){
            AdminTicketDomainExample adminTicketDomainExample = new AdminTicketDomainExample();
            adminTicketDomainExample.createCriteria().andTokenEqualTo(token);
            List<AdminTicketDomain> adminTicketDomainList = adminTicketDomainMapper.selectByExample(adminTicketDomainExample);

            if(adminTicketDomainList != null && adminTicketDomainList.size() > 0){
                AdminTicketDomain adminTicketDomain = adminTicketDomainList.get(0);
                AdminRoleVo adminRoleVo = adminInfoDao.selectUserInfoByUserId(adminTicketDomain.getAdminId());
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("查询成功");
                commonResult.setData(adminRoleVo);
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该token不合法");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("token不能为空");
        }


        return commonResult;
    }

    @Override
    public CommonResult createAdmin(AdminInfoDomain adminInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(adminInfoDomain != null){
            boolean isEmail = MailUtil.isEmail(adminInfoDomain.getAccount());
            if(isEmail){
                String password = PasswordUtil.randomPassword();
                String pwdSalt = createPassword(password);
                String[] str = pwdSalt.split(",");
                adminInfoDomain.setPassword(str[0]);
                adminInfoDomain.setSlat(str[1]);
                adminInfoDomainMapper.insertSelective(adminInfoDomain);
                try {
                    MailUtil.sendMail(adminInfoDomain.getAccount(),"您的密码为："+password);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("添加成功");
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("请输入正确的邮箱");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult roleList() {
        CommonResult commonResult = new CommonResult();
        RoleInfoDomainExample roleInfoDomainExample = new RoleInfoDomainExample();
        List<RoleInfoDomain> roleInfoDomainList = roleInfoDomainMapper.selectByExample(roleInfoDomainExample);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(roleInfoDomainList);
        return commonResult;
    }

    @Override
    public CommonResult adminList() {
        CommonResult commonResult = new CommonResult();
        List<AdminRoleVo> adminRoleVoList = adminInfoDao.selectAdminInfo();
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(adminRoleVoList);
        return commonResult;
    }

    @Override
    public CommonResult brokerList(ApplyBrokerConditionVo applyBrokerConditionVo) {
        CommonResult commonResult = new CommonResult();
        if(applyBrokerConditionVo != null){
            int count = applyBrokerInfoDao.managerBrokerCount();
            List<ApplyBrokerInfoVo> applyBrokerInfoVos = applyBrokerInfoDao.managerBrokerList(applyBrokerConditionVo);
            JSONObject data = new JSONObject();
            data.put("total",count);
            data.put("items",applyBrokerInfoVos);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不完整");
        }
        return commonResult;
    }

    @Override
    public CommonResult companyBrokerList(String companyName) {
        CommonResult commonResult = new CommonResult();
        if(!StringUtils.isEmpty(companyName)){
            List<ApplyBrokerInfoVo> applyBrokerInfoVos = applyBrokerInfoDao.companyBrokerList(companyName);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(applyBrokerInfoVos);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不完整");
        }
        return commonResult;
    }

    @Override
    public CommonResult companyUserList(String companyName) {
        CommonResult commonResult = new CommonResult();
        if(!StringUtils.isEmpty(companyName)){
            List<UserInfoDemainVo> userInfoDemainVoList = applyBrokerInfoDao.companyUserList(companyName);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(userInfoDemainVoList);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不完整");
        }
        return commonResult;
    }

    @Override
    public CommonResult passAudit(ApplyBrokerInfoDomain applyBrokerInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if( applyBrokerInfoDomain != null ){
            applyBrokerInfoDomainMapper.updateByPrimaryKeySelective(applyBrokerInfoDomain);
            UserInfoDomain userInfoDomain = new UserInfoDomain();
            userInfoDomain.setId(applyBrokerInfoDomain.getUserId());
            userInfoDomain.setUserType(3);
            userInfoDomain.setUpdateTs(new Date());
            userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
            StringBuffer uri = new StringBuffer().append(verifyCodeUrl)
                    .append("?mobile=").append(applyBrokerInfoDomain.getPhone()).append("&tpl_id=").append(passBrokerCodeId)
                    .append("&tpl_value=").append("").append("&key=").append(verifyCodeKey);
            HttpClientUtils.getInstance().doGetWithJsonResult(uri.toString());
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("审核通过");
        }else{
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("参数不完整");
        }

        return commonResult;
    }

    @Override
    public CommonResult findHouseList(HouseAdminConditionVo houseConditionVo) {
        CommonResult commonResult = new CommonResult();
        if(houseConditionVo != null){
            int count = houseInfoDao.countAdminHouseInfo(houseConditionVo);
            List<HouseInfoDomainVo> houseInfoDomainVoList = houseInfoDao.selectAdminHouseInfoList(houseConditionVo);
            logger.info("AdminServiceImpl findHouseList count -{},houseInfoDomainVoList-{}",count,houseInfoDomainVoList.toString());
            JSONObject data = new JSONObject();
            data.put("total",count);
            data.put("houseInfoList",houseInfoDomainVoList);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult findInformList(AdminInformInfoConditionVo adminInformInfoConditionVo) {
        int count = informRecordDao.informHouseCount(adminInformInfoConditionVo);
        List<InformHouseRecordDomain> informHouseRecordDomainList = informRecordDao.informHouseList(adminInformInfoConditionVo);
        JSONObject json = new JSONObject();
        json.put("total",count);
        json.put("informList",informHouseRecordDomainList);
        CommonResult commonResult = new CommonResult();
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }

    @Override
    public void handlerInform(InformHouseRecordDomain informHouseRecordDomain) {
        informHouseRecordDomainMapper.updateByPrimaryKeySelective(informHouseRecordDomain);
        InformHouseRecordDomain informHouseRecord = informHouseRecordDomainMapper.selectByPrimaryKey(informHouseRecordDomain.getId());
        HouseInfoDetailVo houseInfoDetailVo = houseInfoDao.selectHouseInfoById(informHouseRecord.getHouseId());
        ConsumeRecordDomain consumeRecordDomain = new ConsumeRecordDomain();
        consumeRecordDomain.setTitle(houseInfoDetailVo.getTitle());
        consumeRecordDomain.setUserId(houseInfoDetailVo.getUserId());
        consumeRecordDomain.setAction(CddConstant.CONSUME_RECORD_CONSUME);
        consumeRecordDomain.setIntegral(CddConstant.INFORM_INTEGRAL_NUM);
        consumeRecordDomain.setType(CddConstant.CONSUME_RECORD_TYPE_INFORM);
        consumeRecordDomainMapper.insertSelective(consumeRecordDomain);
    }

    public String createPassword(String password){
        String salt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw(password, salt);
        return salt+","+hashed;
    }

}
