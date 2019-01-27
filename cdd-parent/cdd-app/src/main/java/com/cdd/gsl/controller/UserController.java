package com.cdd.gsl.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public CommonResult<LoginTokenVo> login(LoginUserVo loginUserVo){
        CommonResult<LoginTokenVo> commonResult = userService.login(loginUserVo);
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

    @RequestMapping("findUserByPhone")
    public CommonResult<UserCompanyInfoVo> findUserByPhone(Long companyId,String phone){
        CommonResult<UserCompanyInfoVo> result = companyService.findUserByPhone(companyId,phone);
        return result;
    }

    @RequestMapping("forgetPassword")
    public CommonResult forgetPassword(UserInfoVo forgetPasswordVo){
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
     * 关注信息
     */
    @RequestMapping("followInfo")
    public CommonResult followInfo(FollowInfoDomain followInfoDomain){
        CommonResult commonResult = userService.followInfo(followInfoDomain);
        return commonResult;
    }

    /**
     * 取消关注信息
     */
    @RequestMapping("cancelFollow")
    public CommonResult cancelFollow(FollowInfoVo followInfoVo){
        CommonResult commonResult = userService.cancelFollow(followInfoVo);
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
    public CommonResult authenticationBroker(ApplyBrokerInfoDomain applyBrokerInfoDomain){
        return userService.authenticationBroker(applyBrokerInfoDomain);
    }

    /**
     * 首页
     */
    @RequestMapping("homePage")
    public CommonResult homePage(){
        return userService.authenticationBroker(applyBrokerInfoDomain);
    }

}
