package com.shsxt.crm.exceptions;

/**
 * Created by xlf on 2018/7/19.
 */
public class AuthException extends RuntimeException {
    private Integer code = 400;
    private String msg = "用户未登录";

    public AuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public AuthException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AuthException(Integer code) {
        super("用户未登录");
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
