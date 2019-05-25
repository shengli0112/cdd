package com.cdd.gsl.service;

import com.cdd.gsl.domain.AdminInfoDomain;
import com.cdd.gsl.vo.MenuInfoVo;

import java.util.List;

public interface ShiroService {
    public void doLogin(String username, String password) throws Exception;

    public String getPasswordByUserName(String username);

    public List<MenuInfoVo> getPermissionByUserName(String username);

    AdminInfoDomain getAdminByUsernameAndPassword(String username,String password);
}
