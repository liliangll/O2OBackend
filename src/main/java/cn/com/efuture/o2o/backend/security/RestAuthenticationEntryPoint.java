package cn.com.efuture.o2o.backend.security;

import cn.com.efuture.o2o.backend.system.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Eric on 2017-01-22.
 * 用户认证入口
 */
@Component
public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint implements Serializable {

    private static final long serialVersionUID = 6586203731266663495L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Unauthorized !");
        JsonResponse r = JsonResponse.TOKEN_TIMEOUT;
        ObjectMapper mapper = new ObjectMapper();
        httpServletResponse.getWriter().println(mapper.writeValueAsString(r));
        httpServletResponse.getWriter().flush();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("MY_REALM");
        super.afterPropertiesSet();
    }
}
