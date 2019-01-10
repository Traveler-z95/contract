package com.jinhui.contract.security.auth;


import com.jinhui.contract.service.UserService;
import com.jinhui.contract.util.Md5Util;
import com.jinhui.contract.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * CustomAuthenticationProvider
 */
@Configuration
@EnableWebSecurity
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Override
    public Authentication authenticate(Authentication authentication) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userName = token.getName();
        //从数据库找到的用户
        User user = null;
        if (userName != null) {
            user = userService.loadUserByUserName(userName);
        }
        //
        if (user == null) {
            throw new UsernameNotFoundException("用户名/密码无效");
        } else if (user.isEnabled()) {
            throw new DisabledException("用户已被禁用");
        } else if (user.isAccountNonExpired()) {
            throw new AccountExpiredException("账号已过期");
        } else if (user.isAccountNonLocked()) {
            throw new LockedException("账号已被锁定");
        } else if (user.isCredentialsNonExpired()) {
            throw new LockedException("凭证已过期");
        }
        //数据库用户的密码
        String password = user.getPassWord();
        String pwdDigest = Md5Util.pwdDigest(token.getCredentials().toString());
        //与authentication里面的credentials相比较
        if (!password.equals(pwdDigest)) {

            throw new BadCredentialsException("Invalid UserName/PassWord");
        }
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        //授权
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}


