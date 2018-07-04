package cn.com.efuture.o2o.backend.system;

import java.io.Serializable;

/**
 * Created by Eric on 2017-02-17.
 */
public class JsonResponse<T> implements Serializable {

    public static final Integer ERR = 5000;
    public static final JsonResponse PARAM_MISSING = JsonResponse.notOk(4000, "参数缺失");
    public static final JsonResponse AUTH_FAIL = JsonResponse.notOk(4001, "权限验证失败");
    public static final JsonResponse NO_PERMISSION = JsonResponse.notOk(4002, "无访问权限");
    public static final JsonResponse TOKEN_TIMEOUT = JsonResponse.notOk(4003, "Token 失效");
    public static final JsonResponse SERVER_ERR = JsonResponse.notOk(ERR, "服务器异常");
    private static final long serialVersionUID = 3580043264590808190L;
    private static final Integer OK = 200;
    private static final Integer REDIRECT = 302;
    private Integer status;

    private Long total;

    private T err;

    private T data;

    public static JsonResponse ok() {
        JsonResponse r = new JsonResponse();
        r.status = OK;
        return r;
    }

    public static JsonResponse ok(Object data) {
        JsonResponse r = new JsonResponse();
        r.status = OK;
        r.data = data;
        return r;
    }

    public static JsonResponse ok(Long total, Object data) {
        JsonResponse r = new JsonResponse();
        r.status = OK;
        r.total = total;
        r.data = data;
        return r;
    }

    public static JsonResponse notOk(Object err) {
        JsonResponse r = new JsonResponse();
        r.status = ERR;
        r.err = err;
        return r;
    }

    public static JsonResponse notOk(Integer status, Object err) {
        JsonResponse r = new JsonResponse();
        r.status = status;
        r.err = err;
        return r;
    }

    public static JsonResponse redirect(String url) {
        JsonResponse r = new JsonResponse();
        r.status = REDIRECT;
        r.data = url;
        return r;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getErr() {
        return err;
    }

    public void setErr(T err) {
        this.err = err;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "status=" + status +
                ", err=" + err +
                ", data=" + data +
                '}';
    }

}
