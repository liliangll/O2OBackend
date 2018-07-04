package cn.com.efuture.o2o.backend.config;


import cn.com.efuture.o2o.backend.security.CustomAuthenticationProvider;
import cn.com.efuture.o2o.backend.security.RestAuthenticationEntryPoint;
import cn.com.efuture.o2o.backend.security.service.JwtAuthenticationTokenFilter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Eric on 2017-01-22.
 *  WebSecurityConfig 中注入这个filter，并且配置到 HttpSecurity
 */
@Configuration
//@EnableAutoConfiguration
@EnableWebSecurity
//@ComponentScan("cn.com.efuture.o2o.backend.security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final   RestAuthenticationEntryPoint restauthenticationEntryPoint;

    private final CustomAuthenticationProvider customAuthenticationProvider;

    public WebSecurityConfig(RestAuthenticationEntryPoint restauthenticationEntryPoint, CustomAuthenticationProvider customAuthenticationProvider) {
        this.restauthenticationEntryPoint = restauthenticationEntryPoint;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean(){
        return new JwtAuthenticationTokenFilter();
    }



    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/images/**", "/swagger-resources/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(restauthenticationEntryPoint).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                .antMatchers("/auth").permitAll()
////                .antMatchers("/api/**").authenticated()
//                .anyRequest().authenticated()
//                .and()
////                .formLogin().disable();
//                .httpBasic();
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(restauthenticationEntryPoint).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers("/api/auth").permitAll()
                .antMatchers("/api/sessions").permitAll()
                .antMatchers("/api/sessionId").permitAll()
                .antMatchers("/api/verifyCode").permitAll()
                .antMatchers("/api/o2oGoodsFilter/exportO2oGoodsFilterSheetDetail").permitAll()
                .antMatchers("/api/o2oGoodsFilter/importO2oGoodsFilterDetail").permitAll()
                .antMatchers("/api/image/**").permitAll()
                .antMatchers("/api/file/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .antMatchers("/api/**").authenticated();
//                .and()
//                .formLogin().disable();

        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth){

        auth.authenticationProvider(customAuthenticationProvider);
//        auth.userDetailsService(userDetailsService);
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
    }
}
