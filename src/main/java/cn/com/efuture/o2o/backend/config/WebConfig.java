package cn.com.efuture.o2o.backend.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Eric on 2017-01-24.
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebConfig implements Filter {
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @Value("${jwt.token.expiration}")
    private Long expiration;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("----------init------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("----------doFilter------------");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //Enable the CROS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", expiration.toString());
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " + tokenHeader);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.debug("----------destroy------------");
    }
}
