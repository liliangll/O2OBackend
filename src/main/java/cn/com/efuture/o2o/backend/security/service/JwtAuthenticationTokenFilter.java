package cn.com.efuture.o2o.backend.security.service;


import cn.com.efuture.o2o.backend.security.JwtTokenUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Eric on 2017-01-22.
 * 集成JWT和Spring Security
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private  UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil tokenUtils;
    @Value("${jwt.token.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("-------JwtAuthenticationTokenFilter: doFilter begin---------");
        String authToken = request.getHeader(tokenHeader);
        String username = tokenUtils.getUsernameFromToken(authToken);
        request.getSession().setAttribute("username", username);

        logger.debug("Created: " + tokenUtils.getCreatedDateFromToken(authToken) + "    Expiration: " + tokenUtils.getExpirationDateFromToken(authToken));
        if (username != null) {     //用户登陆校验
            final String ORIGIN_LOCAL = "local";
            String orgin = tokenUtils.getOriginFromToken(authToken);
            UserDetails currentUser = null;
            logger.debug("-------JwtAuthenticationTokenFilter: doFilter middle---------");
            if (ORIGIN_LOCAL.equals(orgin))
                currentUser = userDetailsService.loadUserByUsername(username);
            if (tokenUtils.validateToken(authToken, currentUser)) {
                String commaSprAuthorities = tokenUtils.getAuthoritiesFromToken(authToken);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(commaSprAuthorities));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (ServletException e) {
            logger.error("error :" + e.getMessage());
        }
    }
}
