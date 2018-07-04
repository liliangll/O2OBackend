package cn.com.efuture.o2o.backend.security.entity;

import java.io.Serializable;

/**
 * Created by Eric on 2017-01-22.
 * 响应
 */
public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 6016596836174000806L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
