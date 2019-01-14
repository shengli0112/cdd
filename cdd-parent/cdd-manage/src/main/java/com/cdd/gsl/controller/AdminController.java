package com.cdd.gsl.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.cdd.gsl.service.ShiroService;
import com.cdd.gsl.vo.ValidateLoginVo;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private ShiroService shiroService;

    @RequestMapping(value = "/login")
    public String login(String username, String password){
        try{
            shiroService.doLogin(username,password);
        }catch (Exception e){
            return "error";
        }
        return "success";
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

}
