package com.itauge.blog.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * ShiroFilterFactoryBean綁定SecurityManager
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //設置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /*
         *  anon: 无需认证就可访问
         *  authc：必须认证了，才可以访问
         *  user：必须拥有了‘记住我’功能才能用
         *  perms：拥有对某个资源的权限才能访问
         *  role：拥有某个角色权限才可以访问
         * */

        Map<String,String> map = new LinkedHashMap<>();
        map.put("/admin/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        //設置登錄請求，被攔截了就發送這個請求
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");
        return shiroFilterFactoryBean;
    }

    /**
     * DefaultWebSecurityManager 綁定Realm
     * @param accountRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("accountRealm") AccountRealm accountRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //關聯realm
        defaultWebSecurityManager.setRealm(accountRealm);
        return defaultWebSecurityManager;
    }

    /**
     * Realm對象，自定義
     * @param
     * @return
     */
    @Bean
    public AccountRealm accountRealm(){
        AccountRealm accountRealm = new AccountRealm();
        return accountRealm;
    }

    /**
     * 額外的解密，沒有用到
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法，這裏使用md5
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //散列的次數，比如散列兩次，相當於md5(md5(""))
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

    /**
     * 整合shiroDialect 用于整合thymeleaf和shiro
     * 要引入pom
     * */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
