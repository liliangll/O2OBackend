package cn.com.efuture.o2o.backend.security;


import cn.com.efuture.o2o.backend.mybatis.entity.User;
import cn.com.efuture.o2o.backend.mybatis.service.UserServiceImpl;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Eric on 2017-01-23.
 * 用户认证
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDetailsService userDetailsService;

    private final UserServiceImpl userServiceImpl;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, UserServiceImpl userServiceImpl) {
        this.userDetailsService = userDetailsService;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("-------CustomAuthenticationProvider---------");

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Base64 base64 = new Base64();
        try {
            password = new String(base64.decode(password), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BadCredentialsException("登陆账号或密码不正确.");
        }
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);

        User checkedUser = userServiceImpl.getUserByNameAndPassword(user);
        if (checkedUser == null){
            throw new BadCredentialsException("登陆账号或密码不正确.");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null){
            throw new BadCredentialsException("登陆账号不存在.");
        }else{
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<GrantedAuthority>());
        }
//        if (username.equals("admin") && password.equals("system")){
//            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
//        }else{
//            return null;
//        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
