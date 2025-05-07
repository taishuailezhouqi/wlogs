package com.szq.web.exception;


import com.szq.web.utils.BaseResponse;
import com.szq.web.utils.Log;
import com.szq.web.utils.ReturnType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


/**
 * @author roc
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public BaseResponse<Object> exceptionHandler(Exception e) {
        Log.sdk.error("error", e);
        return BaseResponse.fail(ReturnType.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = BizException.class)
    public BaseResponse<Object> handleBizException(BizException e) {
        Log.sdk.error("bizException", e);
        return BaseResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public void throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        // 捕获到鉴权失败异常，主动抛出，交给 RestAccessDeniedHandler 去处理
        Log.sdk.info("============= 捕获到 AccessDeniedException");
        throw e;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        Log.sdk.error("参数错误:{}",errorMessage);
        return BaseResponse.fail(errorMessage);
    }
}
