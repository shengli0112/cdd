package com.cdd.gsl.service.impl;

import com.cdd.gsl.dao.AdminInfoDao;
import com.cdd.gsl.domain.AdminInfoDomain;
import com.cdd.gsl.service.ShiroService;
import com.cdd.gsl.vo.MenuInfoVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private AdminInfoDao adminInfoDao;

    @Override
    public void doLogin(String username, String password) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token =
                    new UsernamePasswordToken(username,password);
            token.setRememberMe(true);
            try {
                 currentUser.login(token);//执行登录
            } catch (UnknownAccountException uae) {
                 throw new Exception("账户或密码不正确");
            } catch (IncorrectCredentialsException ice) {
                 throw new Exception("账户或密码不能为空");
            } catch (LockedAccountException lae) {
                 throw new Exception("用户被锁定了 ");
            } catch (AuthenticationException ae) {
                 ae.printStackTrace();
                 throw new Exception("未知错误");
            }

        }
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
}
