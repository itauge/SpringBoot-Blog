package com.itauge.blog.config;

import com.itauge.blog.entity.User;
import com.itauge.blog.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


public class AccountRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    //授權
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //認證
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.checkByName(token.getUsername());
        if (user == null){
            return null;
        }
        //使用ByteSource.Util.bytes計算鹽值
        ByteSource salt = ByteSource.Util.bytes(token.getUsername());

        return new SimpleAuthenticationInfo(user,user.getPassword(),salt,getName());
    }
}
