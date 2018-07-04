package cn.com.efuture.o2o.backend.security.service;

import cn.com.efuture.o2o.backend.mybatis.entity.User;
import cn.com.efuture.o2o.backend.mybatis.service.UserServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017-01-22.
 * UserDetailsService处理用户信息获取逻辑
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserServiceImpl userServiceImpl;

    public UserDetailsServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("--------------loadUserByUsername-----------------");
        logger.debug("-----------------------login: " + username);
        final User user = userServiceImpl.getUserByMail(username);
            if (user == null){
                throw new UsernameNotFoundException("no user found");
            }
            final List<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority("USER"));
            user.setAuthorities("1");
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
}
