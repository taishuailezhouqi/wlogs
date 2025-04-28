package com.szq.web.exception;


import com.szq.web.utils.BaseResponse;
import com.szq.web.utils.Log;
import com.szq.web.utils.ReturnType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

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
}
