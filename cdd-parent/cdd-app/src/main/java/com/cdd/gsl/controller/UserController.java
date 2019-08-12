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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;


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
     * 托管厂房列表
     */
    @RequestMapping("findPlantList")
    public CommonResult findPlantList(){

        return null;
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
     * 托管土地列表
     */
    @RequestMapping("findLandList")
    public CommonResult findLandList(){

        return null;
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

    @RequestMapping("allBroker")
    public CommonResult allBroker(Integer pageNo, Integer pageSize){
        if(pageNo == null){
            pageNo = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }

        return userService.allBroker(pageNo,pageSize);
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

    @RequestMapping("testMq")
    public void testMq(){
        HashMap<String, String> map = new HashMap<String, String>();
        List<HashMap<String, String>> mapList = new ArrayList<>();
        map.put("userAccount", "");
        map.put("siteId", "123456700");
        map.put("siteName", "ssdd");
        map.put("siteMd5", "qw234");
        map.put("source", "ddddd");
        map.put("provinceId", "12");
        map.put("provinceName", "山东");
        map.put("cityId", "112");
        map.put("cityName", "济南");
        map.put("projectCode", "12");
        map.put("projectName", "aa");
        map.put("websiteCode", "12");
        map.put("websiteName", "www.baidu.com");
        map.put("advertiserCode", "12");
        map.put("advertiserName", "今日头条");
        map.put("promoteTypeCode", "12");
        map.put("promoteTypeName", "大灯多");
        map.put("deviceCode", "12");
        map.put("deviceName", "ios");
        map.put("flowCenterCode", "12");
        map.put("flowCenterName", "12");
        map.put("flowCorpCode", "12");
        map.put("flowCorpName", "12");
        map.put("callCorpCode", "12");
        map.put("callCorpName", "12");
        map.put("pmAccount", "12");
        map.put("respAccount", "12");
        map.put("cardTypeCode", "12");
        map.put("cardTypeName", "12");
        map.put("virtualGroupCode", "");
        map.put("virtualGroupName", "");
        map.put("comments", "12");
        map.put("allProvinceFlag", "2");
        map.put("multiRegionInfo", "[{\"cityId\":\"360\",\"cityName\":\"北京\",\"provinceId\":\"2\",\"provinceName\":\"北京\"},{\"cityId\":\"76\",\"cityName\":\"唐山\",\"provinceId\":\"12\",\"provinceName\":\"河北\"},{\"cityId\":\"38\",\"cityName\":\"青岛\",\"provinceId\":\"9\",\"provinceName\":\"山东\"}]\n");
        mapList.add(map);

        String str = "[{\n" +
                "\t\"advertiserCode\": \"360\",\n" +
                "\t\"advertiserName\": \"360搜索\",\n" +
                "\t\"allProvinceFlag\": \"2\",\n" +
                "\t\"callCorpCode\": \"2021075\",\n" +
                "\t\"callCorpName\": \"第九军团\",\n" +
                "\t\"cardTypeCode\": \"ISMOBILE\",\n" +
                "\t\"cardTypeName\": \"手机\",\n" +
                "\t\"cityId\": \"52\",\n" +
                "\t\"cityName\": \"长春\",\n" +
                "\t\"comments\": \"备注\",\n" +
                "\t\"createTime\": \"2019-03-25 14:03:15\",\n" +
                "\t\"deviceCode\": \"pc\",\n" +
                "\t\"deviceName\": \"PC端\",\n" +
                "\t\"flowCenterCode\": \"101\",\n" +
                "\t\"flowCenterName\": \"lizhifeng\",\n" +
                "\t\"flowCorpCode\": \"123\",\n" +
                "\t\"flowCorpName\": \"123\",\n" +
                "\t\"multiRegionInfo\": \"[{\\\"cityId\\\":\\\"52\\\",\\\"cityName\\\":\\\"长春\\\",\\\"provinceId\\\":\\\"6\\\",\\\"provinceName\\\":\\\"吉林\\\"},{\\\"cityId\\\":\\\"38\\\",\\\"cityName\\\":\\\"沈阳\\\",\\\"provinceId\\\":\\\"29\\\",\\\"provinceName\\\":\\\"辽宁\\\"}]\",\n" +
                "\t\"mutilProvinceId\": \"6,29\",\n" +
                "\t\"mutilProvinceName\": \"吉林,辽宁\",\n" +
                "\t\"pmAccount\": \"wangyazhou\",\n" +
                "\t\"projectCode\": \"12\",\n" +
                "\t\"projectName\": \"会计证\",\n" +
                "\t\"promoteTypeCode\": \"wm\",\n" +
                "\t\"promoteTypeName\": \"网盟\",\n" +
                "\t\"propertyCheck\": 1,\n" +
                "\t\"provinceId\": \"6\",\n" +
                "\t\"provinceIds\": \"7,8\",\n" +
                "\t\"provinceName\": \"吉林\",\n" +
                "\t\"quantumId\": \"2030009\",\n" +
                "\t\"respAccount\": \"wangyazhou\",\n" +
                "\t\"siteId\": \"7000050780\",\n" +
                "\t\"siteMd5\": \"18e9f80e6ee11987b480e3fc4d3d8bfd\",\n" +
                "\t\"siteName\": \"测试MQ7\",\n" +
                "\t\"source\": \"360搜索网盟\",\n" +
                "\t\"state\": \"\",\n" +
                "\t\"userAccount\": \"wangzhen06@sunlands.com\",\n" +
                "\t\"virtualGroupCode\": \"2\",\n" +
                "\t\"virtualGroupName\": \"MBA组\",\n" +
                "\t\"websiteCode\": \"sunlandscom\",\n" +
                "\t\"websiteName\": \"sunlandscom\"\n" +
                "}]";
        JSONArray arr = JSONArray.parseArray(str);
        //根据key发送到对应的队列
        rabbitTemplate.convertAndSend("que_cat_key", arr);

    }

}
