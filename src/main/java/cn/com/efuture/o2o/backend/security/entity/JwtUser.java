package cn.com.efuture.o2o.backend.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
/**
 * 实现UserDetails接口，用户实体即为springSecurity所使用的用户。
 * 处理用户效验逻辑
 * Created by Eric on 2017-01-22.
 *
 */
public class JwtUser implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;//权限

    private static boolean enabled = true;
    private static boolean accountNonExpired = true;
    private static boolean credentialsNonExpired = true;
    private static boolean accountNonLocked = true;

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    //    public JwtUser(String username, String password, boolean enabled, boolean accountNonExpired,
//                          boolean credentialsNonExpired, boolean accountNonLocked,
//                          Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    }

//    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        this(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {//账户是否过期
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {//账户是否锁定
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {//用户密码是否过期
        return false;
    }

    @Override
    public boolean isEnabled() {//账户是否可用
        return false;
    }
}