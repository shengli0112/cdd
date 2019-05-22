package com.cdd.gsl.service.impl;

import com.cdd.gsl.dao.AdminInfoDao;
import com.cdd.gsl.domain.AdminInfoDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.vo.MenuInfoVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminInfoDao adminInfoDao;

    @Override
    public void doLogin(String username, String password) throws Exception {

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
