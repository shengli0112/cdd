package com.cdd.gsl.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.AdminInfoDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.service.ShiroService;
import com.cdd.gsl.vo.AdminVo;
import com.cdd.gsl.vo.ValidateLoginVo;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login")
    public CommonResult login(@RequestBody AdminVo adminVo) throws Exception {
        logger.info("UserController login admin params username:{} password:{}",adminVo.getUsername(),adminVo.getPassword());
        return adminService.doLogin(adminVo.getUsername(),adminVo.getPassword());
    }

    //菜单页
    @RequestMapping(value = "/index")
    public String index(){
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject);
        return "common/index";
    }

    @RequestMapping("/index.jhtml")
    public ModelAndView getIndex(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    /*@RequestMapping("/exceptionForPageJumps.jhtml")
    public ModelAndView exceptionForPageJumps(HttpServletRequest request) throws Exception {
        throw new BusinessException(LuoErrorCode.NULL_OBJ);
    }

    @RequestMapping(value="/businessException.json", method=RequestMethod.POST)
    @ResponseBody
    public String businessException(HttpServletRequest request) {
        throw new BusinessException(LuoErrorCode.NULL_OBJ);
    }*/

    @RequestMapping(value="/otherException.json", method=RequestMethod.POST)
    @ResponseBody
    public String otherException(HttpServletRequest request) throws Exception {
        throw new Exception();
    }

	/*//跳转到登录页面
	@RequestMapping("/login111.jhtml")
	public ModelAndView login() throws Exception {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	*/
    //跳转到登录成功页面
//	@RequestMapping("/loginsuccess.jhtml")
//	public ModelAndView loginsuccess() throws Exception {
//		ModelAndView mav = new ModelAndView("loginsuccess");
//		return mav;
//	}

//	@REQUESTMAPPING("/NEWPAGE.JHTML")
//	PUBLIC MODELANDVIEW NEWPAGE() THROWS EXCEPTION {
//		MODELANDVIEW MAV = NEW MODELANDVIEW("NEWPAGE");
//		RETURN MAV;
//	}
//
//	@REQUESTMAPPING("/NEWPAGENOTADD.JHTML")
//	PUBLIC MODELANDVIEW NEWPAGENOTADD() THROWS EXCEPTION {
//		MODELANDVIEW MAV = NEW MODELANDVIEW("NEWPAGENOTADD");
//		RETURN MAV;
//	}

    /**
     * 验证用户名和密码
     * @param  username,String password
     * @return
     */
    @RequestMapping(value="/checkLogin",method=RequestMethod.POST)
    @ResponseBody
    public String checkLogin(String username,String password) {
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject currentUser = SecurityUtils.getSubject();

            ValidateLoginVo vo = (ValidateLoginVo) currentUser.getPrincipal();
            if(vo != null){
                if(!token.getUsername() .equals(vo.getUserName())){
                    currentUser.login(token);
                }

            }
            if (!currentUser.isAuthenticated()){
                //使用shiro来验证
                // token.setRememberMe(true);
                currentUser.login(token);//验证角色和权限
            }

        }catch(Exception ex){
//            throw new BusinessException(LuoErrorCode.LOGIN_VERIFY_FAILURE);
            ex.printStackTrace();
        }
        result.put("success", true);
        return JSONUtils.toJSONString(result);
    }

    @RequestMapping(value="/info",method=RequestMethod.GET)
    public CommonResult info(String token){
        return adminService.info(token);
    }

    /**
     * 退出登录
     */
    @RequestMapping(value="/logout",method=RequestMethod.POST)
    @ResponseBody
    public String logout() {
    	Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return JSONUtils.toJSONString(result);
    }

    @RequestMapping(value="/createAdmin")
    public CommonResult createAdmin(@RequestBody AdminInfoDomain adminInfoDomain){
        return adminService.createAdmin(adminInfoDomain);
    }

    @RequestMapping(value="/roleList")
    public CommonResult roleList(){
        return adminService.roleList();
    }

    @RequestMapping(value="/adminList")
    public CommonResult adminList(){
        return adminService.adminList();
    }


}
