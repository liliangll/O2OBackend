package cn.com.efuture.o2o.backend.security.entity;

import java.io.Serializable;

/**
 * Created by Eric on 2017-01-22.
 * 请求
 */
public class JwtAuthenticationRequest implements Serializable {
    private static final long serialVersionUID = -8347328083939863819L;


    private String username;
    private String password;
    private String verifyCode;
    private String sessionId;



    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
