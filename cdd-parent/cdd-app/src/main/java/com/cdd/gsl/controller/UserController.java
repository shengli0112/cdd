package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.RecordInfoDomainMapper;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.*;
import com.cdd.gsl.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private LandService landService;

    @Autowired
    private PlantService plantService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private RecordInfoService recordInfoService;


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);



    @RequestMapping("register")
    public CommonResult register(@RequestBody UserInfoVo userParamVo){
        CommonResult commonResult = userService.register(userParamVo);
        return commonResult;
    }

    @RequestMapping("login")
    public CommonResult<LoginTokenVo> login(@RequestBody LoginUserVo loginUserVo){
        CommonResult<LoginTokenVo> commonResult = userService.login(loginUserVo);
        return commonResult;

    }

    @RequestMapping("logout")
    public CommonResult logout(String phone){
        CommonResult commonResult = userService.logout(phone);
        return commonResult;

    }

    /**
     * 三方登录
     * @param thirdUserInfoDomain
     * @return
     */
    @RequestMapping("thirdLogin")
    public CommonResult thirdLogin(@RequestBody ThirdUserInfoDomain thirdUserInfoDomain){
        logger.info("UserController thirdLogin ThirdUserVo -{}",thirdUserInfoDomain);
        CommonResult commonResult = userService.thirdLogin(thirdUserInfoDomain);
        return commonResult;
    }

    @RequestMapping("updateUser")
    public CommonResult updateUser(@RequestBody UserParamVo userParamVo){
        return userService.updateUser(userParamVo);
    }

    /**
     * 创建公司
     * @param companyVo
     * @return
     */
    @RequestMapping("createCompany")
    public CommonResult createCompany(@RequestBody CompanyInfoDomain companyVo){
        logger.info("UserController createCompany ThirdUserVo -{}",companyVo);
        CommonResult result = companyService.createCompany(companyVo);
        return result;
    }

    /**
     * 邀请员工加入公司
     * @param companyId
     * @param userId
     * @return
     */
    @RequestMapping("inviteUserToCompany")
    public CommonResult inviteUserToCompany(Long companyId,Long userId){
        logger.info("UserController inviteUserToCompany companyId -{}，userId - {}",companyId,userId);
        CommonResult result = companyService.inviteUserToCompany(companyId,userId);
        return result;
    }

    /**
     * 公司团队
     */
    @RequestMapping("companyTeam")
    public CommonResult companyTeam(@RequestParam("userId") Long userId,@RequestParam("userType") Integer userType){
        return userService.companyTeam(userId,userType);
    }

    @RequestMapping("findUserByPhone")
    public CommonResult<UserCompanyInfoVo> findUserByPhone(Long companyId,String phone){
        CommonResult<UserCompanyInfoVo> result = companyService.findUserByPhone(companyId,phone);
        return result;
    }

    @RequestMapping("findUserInfo")
    public CommonResult<SingleUserInfoVo> findUserInfo(Long userId){
        CommonResult<SingleUserInfoVo> result = userService.findUserInfo(userId);
        return result;
    }

    @RequestMapping("forgetPassword")
    public CommonResult forgetPassword(@RequestBody UserInfoVo forgetPasswordVo){
        CommonResult result = userService.forgetPassword(forgetPasswordVo);
        return result;
    }

    @RequestMapping("checkPhone")
    public CommonResult checkPhone(String phone){
        CommonResult result = userService.checkPhone(phone);
        return result;
    }

    /**
     * 同意加入公司
     */
    @RequestMapping("agreeCompany")
    public CommonResult agreeCompany(Long userId,Long companyId){
        CommonResult commonResult = userService.agreeCompany(userId,companyId);

        return commonResult;
    }

    /**
     * 绑定手机号
     * @param phone
     * @return
     */
    @RequestMapping("bindPhone")
    public CommonResult bindPhone(Long userId,String phone){
        CommonResult commonResult = userService.bindPhone(userId,phone);
        return commonResult;
    }

    /**
     * 个人托管厂房创建
     */
    @RequestMapping("createPlant")
    public CommonResult createPlant(PlantInfoDomain plantInfoDomain){
        CommonResult commonResult = plantService.createPlant(plantInfoDomain);
        return commonResult;
    }


    /**
     * 个人托管仓库创建
     */
    @RequestMapping("createStorage")
    public CommonResult createStorage(StorageInfoDomain storageInfoDomain){
        CommonResult commonResult = storageService.createStorage(storageInfoDomain);
        return commonResult;
    }

    /**
     * 托管仓库列表
     */
    @RequestMapping("findStorageList")
    public CommonResult findStorageList(){

        return null;
    }

    /**
     * 个人托管土地创建
     */
    @RequestMapping("createLand")
    public CommonResult createLand(LandInfoDomain landInfoDomain){
        CommonResult commonResult = landService.createLand(landInfoDomain);
        return commonResult;
    }


    /**
     * 我的关注
     */
    @RequestMapping("findFollow")
    public CommonResult findFollow(FollowConditionVo followConditionVo){
        CommonResult commonResult = userService.findFollow(followConditionVo);
        return commonResult;
    }

    /**
     * 关注信息
     */
    @RequestMapping("createFollow")
    public CommonResult followInfo(@RequestBody FollowInfoDomain followInfoDomain){
        CommonResult commonResult = userService.followInfo(followInfoDomain);
        return commonResult;
    }

    /**
     * 取消关注信息
     */
    @RequestMapping("cancelFollow")
    public CommonResult cancelFollow(@RequestBody FollowInfoVo followInfoVo){
        CommonResult commonResult = userService.cancelFollow(followInfoVo);
        return commonResult;
    }

    /**
     * 是否关注
     */
    @RequestMapping("isFollow")
    public CommonResult isFollow(IsFollowVo isFollowVo){
        CommonResult commonResult = userService.isFollow(isFollowVo);
        return commonResult;
    }

    /**
     *  获取字典信息
     */
    @RequestMapping("findDictInfo")
    public CommonResult<List<CommonDictVo>> findDictInfo(String dictName){
        logger.info("UserController findDictInfo dictName-{}",dictName);
        CommonResult<List<CommonDictVo>> commonResult = new CommonResult<>();
        List<CommonDictDomain> commonDictDomainList = userService.findDictInfo(dictName);
        List<CommonDictVo> commonDictVos = new ArrayList<>();
        if(commonDictDomainList != null && commonDictDomainList.size() > 0){
            commonDictDomainList.forEach(commonDictDomain -> {
                CommonDictVo commonDictVo = new CommonDictVo();
                BeanUtils.copyProperties(commonDictDomain,commonDictVo);
                commonDictVos.add(commonDictVo);
            });
        }
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(commonDictVos);
        return commonResult;
    }

    /**
     * 创建跟进信息
     */
    @RequestMapping("createRecordInfo")
    public CommonResult createRecordInfo(RecordInfoDomain recordInfoDomain){
        CommonResult commonResult = recordInfoService.createRecordInfo(recordInfoDomain);
        return commonResult;
    }

    /**
     * 跟进列表
     */
    @RequestMapping("findRecordInfoList")
    public CommonResult<List<RecordInfoVo>> findRecordInfoList(Long houseId){
        CommonResult<List<RecordInfoVo>> commonResult = new CommonResult<>();
        if(houseId != null){
            List<RecordInfoVo> recordInfoVos = recordInfoService.findRecordInfoList(houseId);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(recordInfoVos);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    /**
     * 认证经纪人
     */
    @RequestMapping("authenticationBroker")
    public CommonResult authenticationBroker(@RequestBody ApplyBrokerInfoVo applyBrokerInfoDomain){
        return userService.authenticationBroker(applyBrokerInfoDomain);
    }

    @RequestMapping("updateBroker")
    public CommonResult updateBroker(@RequestBody ApplyBrokerInfoDomain applyBrokerInfoDomain){
        return userService.updateBroker(applyBrokerInfoDomain);
    }

    @RequestMapping("findBrokerDetail")
    public CommonResult findBrokerDetail(Long userId){
        return userService.findBrokerDetail(userId);
    }

    @RequestMapping("findBrokerInfo")
    public CommonResult findBrokerInfo(Long userId){
        return userService.findBrokerInfo(userId);
    }

    @RequestMapping("allBroker")
    public CommonResult allBroker(Integer pageNo, Integer pageSize,String param){
        if(pageNo == null){
            pageNo = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }

        return userService.allBroker(pageNo,pageSize,param);
    }

    @RequestMapping("findApplyBroker")
    public CommonResult findApplyBroker(Long userId){
        return userService.findApplyBroker(userId);
    }

    /**
     * 主管查询所有待添加的经纪人
     */
    @RequestMapping("findBroker")
    public CommonResult findBroker(Long userId){
        return userService.findBroker(userId);
    }

    /**
     *
     * @param loginUserId 登录用户ID
     * @param deleteUserId 删除用户ID
     * @param transferUserId 房源转入用户ID
     * @return
     */
    public CommonResult deleteBroker(Long loginUserId,Long deleteUserId,Long transferUserId){
        return userService.deleteBroker(loginUserId,deleteUserId,transferUserId);
    }

    /**
     * 同意加入该公司
     */
    @RequestMapping("agreeBroker")
    public CommonResult agreeBroker(Long brokerId){
        return userService.agreeBroker(brokerId);
    }

    /**
     * 获取短信验证码
     */
    @RequestMapping("verifyCode")
    public CommonResult<String> verifyCode(String phone){
        return userService.verifyCode(phone);
    }

    /**
     * 更换设备登陆验证
     */
    @RequestMapping("changeDevice")
    public CommonResult changeDevice(ChangeDeviceVo changeDeviceVo){
        return userService.changeDevice(changeDeviceVo);
    }

    /**
     * 首页
     */
    @RequestMapping("homePage")
    public CommonResult homePage(){
        return userService.home();
    }

    /**
     * 经纪人详情页
     * @return
     */
    @RequestMapping("brokerDetail")
    public CommonResult brokerDetail(Long userId){
        return userService.brokerDetail(userId);
    }

    /**
     * 消息列表
     * @return
     */
    @RequestMapping("messageList")
    public CommonResult messageList(MessageConditionVo messageConditionVo){
        return userService.messageList(messageConditionVo);
    }

    /**
     * 消息详情
     * @return
     */
    @RequestMapping("messageDetail")
    public CommonResult messageDetail(Long messageId){
        return userService.messageDetail(messageId);
    }

    /**
     * 消息未读数
     * @return
     */
    @RequestMapping("messageUnreadCount")
    public CommonResult messageUnreadCount(Long userId){
        return userService.messageUnreadCount(userId);
    }

    /**
     * 添加足迹
     * @return
     */
    @RequestMapping("addFootprint")
    public CommonResult addFootprint(@RequestBody BrowseRecordDomain browseRecordDomain){
        return null;
    }

    /**
     * 查看号码
     */
    @RequestMapping("checkMobile")
    public CommonResult checkMobile(@RequestBody CheckPhoneDomain checkPhoneDomain){
        return userService.checkMobile(checkPhoneDomain);
    }

    /**
     * 买币
     */
    @RequestMapping("buyCurrency")
    public CommonResult buyCurrency(@RequestBody UserCurrencyMappingDomain userCurrencyMappingDomain){
        return userService.buyCurrency(userCurrencyMappingDomain);
    }

    /**
     * 轮播图
     */
    @RequestMapping("slideList")
    public CommonResult slideList(@RequestParam("city")String city){
        return userService.slideList(city);
    }

    /**
     * 提交加盟信息
     */
    @RequestMapping("createLeague")
    public CommonResult createLeague(@RequestBody LeagueInfoParamVo leagueInfoParamVo){
        return userService.createLeague(leagueInfoParamVo);
    }

    /**
     * 用户定位城市存储
     */
    @RequestMapping("createSearchCity")
    public CommonResult createSearchCity(@RequestBody SearchCityUserInfo searchCityUserInfo){
        return userService.createSearchCity(searchCityUserInfo);
    }


}
