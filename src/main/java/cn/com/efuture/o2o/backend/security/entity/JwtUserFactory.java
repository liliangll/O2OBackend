package cn.com.efuture.o2o.backend.security.entity;


import cn.com.efuture.o2o.backend.mybatis.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by Eric on 2017-01-24.
 */
public class JwtUserFactory {
    public static JwtUser create(User user){
        return new JwtUser(
                user.getUserName(),
                user.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
        );
    }
}
