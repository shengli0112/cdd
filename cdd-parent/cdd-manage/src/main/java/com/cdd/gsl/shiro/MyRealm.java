package com.cdd.gsl.shiro;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.domain.AdminInfoDomain;
import com.cdd.gsl.service.ShiroService;
import com.cdd.gsl.vo.MenuInfoVo;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private ShiroService shiroService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("MyRealm doGetAuthorizationInfo principals-{}",principals.toString());
        //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()

        String username = (String)super.getAvailablePrincipal(principals);

        if (username != null) {
            List<MenuInfoVo> perms = shiroService.getPermissionByUserName(username);
            if (perms != null && perms.size() > 0) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                List<MenuInfoVo> parentList = new ArrayList<>();
                for(MenuInfoVo perm:perms){
                    if(perm.getParentId() == 0){
                        parentList.add(perm);
                    }
                }
                perms.removeAll(parentList);
                for(MenuInfoVo parent:parentList){
                    List<MenuInfoVo> childMenuList = new ArrayList<>();
                    for(MenuInfoVo perm:perms){
                        if(parent.getMenuId() == perm.getParentId()){
                            childMenuList.add(perm);
                        }
                    }
                    parent.setMenuInfoList(childMenuList);
                    perms.removeAll(childMenuList);
                }
                if(perms != null && perms.size() > 0){
                    for(MenuInfoVo parent:parentList){
                        List<MenuInfoVo> menuInfoVos = parent.getMenuInfoList();
                        if(menuInfoVos != null && menuInfoVos.size() > 0){
                            for(MenuInfoVo child:menuInfoVos){
                                List<MenuInfoVo> childChildMenuList = new ArrayList<>();
                                for(MenuInfoVo perm:perms){
                                    if(perm.getParentId() == child.getMenuId()){
                                        childChildMenuList.add(perm);
                                    }
                                }

                                child.setMenuInfoList(childChildMenuList);
                                perms.removeAll(childChildMenuList);
                            }
                        }
                    }
                }


                for (MenuInfoVo each : parentList) {
                    //将权限资源添加到用户信息中
                    info.addObjectPermission((Permission) each);
                 }
                return info;
            }
        }
                return null;


//      //为当前用户设置角色和权限

//      SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();

//      simpleAuthorInfo.addRoles(roleList);

//      simpleAuthorInfo.addStringPermissions(permissionList);

        /*SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();

        //实际中可能会像上面注释的那样从数据库取得

        if(null!=currentUsername && "mike".equals(currentUsername)){

            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色

            simpleAuthorInfo.addRole("admin");

            //添加权限

            simpleAuthorInfo.addStringPermission("admin:manage");

            System.out.println("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");

            return simpleAuthorInfo;

        }*/

        //若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址

        //详见applicationContext.xml中的<bean id="shiroFilter">的配置

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        //获取基于用户名和密码的令牌

        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的

        //两个token的引用都是一样的

        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;

        //System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

//      User user = userService.getByUsername(token.getUsername());

//      if(null != user){

//          AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), user.getNickname());

//          this.setSession("currentUser", user);

//          return authcInfo;

//      }else{

//          return null;

//      }

        //此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息

        //说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码(可以是从数据库中取到的,本例中为了演示就硬编码了)

        //这样一来,在随后的登录页面上就只有这里指定的用户和密码才能通过验证
        String username = token.getUsername();
        String password = new String(token.getPassword());
        if(Strings.isNotEmpty(username) && Strings.isNotEmpty(password)){

            AdminInfoDomain adminInfoDomain = shiroService.getAdminByUsernameAndPassword(username,password);
            if(adminInfoDomain != null){
                return new SimpleAuthenticationInfo(username,password,getName());
            }else {
                throw new UnknownAccountException();
            }



        }else{
            throw new IncorrectCredentialsException();
        }

        //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常

//        return null;
    }

    /**

     * 将一些数据放到ShiroSession中,以便于其它地方使用


     */

    private void setSession(Object key, Object value){

        Subject currentUser = SecurityUtils.getSubject();

        if(null != currentUser){

            Session session = currentUser.getSession();

            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");

            if(null != session){

                session.setAttribute(key, value);

            }

        }

    }
}
