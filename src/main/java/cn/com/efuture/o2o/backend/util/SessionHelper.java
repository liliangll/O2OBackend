package cn.com.efuture.o2o.backend.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.com.efuture.o2o.backend.mybatis.entity.User;

/**
 * @author Baij
 * 操作session的服务
 */
public class SessionHelper {

	/**
	 * 获取session属性
	 * @param key
	 * @return
	 */
	static public Object getValue(String key) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		if(session == null) return null;

		return session.getAttribute(key);
	}

	static public String getUserName() {
		Object o = getValue("username");
		if(o==null) {
			throw new ValidationException("无法获取用户信息，请重新登录");
		}else {
			return o.toString();
		}
	}

	static public String getVerifyCode() {
		Object o = getValue("verifyCode");
		if(o==null) {
			throw new ValidationException("无法获取验证码信息，请重新登录");
		}else {
			return o.toString();
		}
	}

	/**
	 * @deprecated
	 * @return
	 */
	static public User getUser() {
		return null;
	}
}
