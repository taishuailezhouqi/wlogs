package com.szq.web.exception;

import com.szq.web.utils.ReturnType;
import lombok.Data;


@Data
public class BizException extends RuntimeException {


    private static final long serialVersionUID = 6896228442704198630L;
    private int code;

    public BizException() {
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Throwable ex) {
        super(ex);
    }

    public BizException(String message) {
        super(message);
        this.code = ReturnType.FAILURE.getCode();
    }

    public BizException(String message, int code, Throwable e) {
        super(message, e);
        this.code = code;
    }

    public BizException(String message, Throwable ex) {
        super(message, ex);
        this.code = ReturnType.FAILURE.getCode();
    }

    public BizException(ReturnType type) {
        super(type.getMsg());
        this.code = type.getCode();
    }

    public static BizException fail(String message) {
        return new BizException(ReturnType.FAILURE.getCode(), message);
    }

    public static BizException fail(ReturnType returnType) {
        return new BizException(returnType);
    }
}
