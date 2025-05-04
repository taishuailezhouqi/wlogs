package com.szq.web.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;

@Data
public class BaseResponse<T> {

    private Integer code;

    private String message;

    private T data;

    public BaseResponse() {

    }

    public BaseResponse(ReturnType returnType) {
        this.code = returnType.getCode();
        this.message = returnType.getMsg();
    }


    public BaseResponse(ReturnType returnType, T t) {
        this.code = returnType.getCode();
        this.message = returnType.getMsg();
        this.data = t;
    }

    private BaseResponse(int code, String msg, T result) {
        this.code = code;
        this.message = msg;
        this.data = result;
    }


    /**
     * 结果
     *
     * @param code    编码
     * @param message 消息
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> result(int code, String message) {
        return new BaseResponse<>(code, message, null);
    }

    /**
     * 结果
     *
     * @param code    编码
     * @param message 消息
     * @param data    数据
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> result(int code, String message, T data) {
        return new BaseResponse<>(code, message, data);
    }


    /**
     * 成功
     *
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> success() {
        return result(ReturnType.SUCCESS.getCode(), ReturnType.SUCCESS.getMsg(), null);
    }


    /**
     * 成功
     *
     * @param data 数据
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> success(T data) {
        return result(ReturnType.SUCCESS.getCode(), ReturnType.SUCCESS.getMsg(), data);
    }

    /**
     * 成功
     *
     * @param message 消息
     * @param data    数据
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> success(String message, T data) {
        return result(ReturnType.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败
     *
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> fail() {
        return result(ReturnType.FAILURE.getCode(), ReturnType.FAILURE.getMsg(), null);
    }

    /**
     * 失败
     *
     * @param message 消息
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> fail(String message) {
        return result(ReturnType.FAILURE.getCode(), message, null);
    }

    /**
     * 失败
     *
     * @param returnType 返回类型
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> fail(ReturnType returnType) {
        return result(returnType.getCode(), returnType.getMsg(), null);
    }

    /**
     * 失败
     *
     * @param message 消息
     * @param data    数据
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> fail(String message, T data) {
        return result(ReturnType.FAILURE.getCode(), message, data);
    }

    /**
     * 失败
     *
     * @param code    编码
     * @param message 消息
     * @return {@link BaseResponse}<{@link T}>
     */
    public static <T> BaseResponse<T> fail(Integer code, String message) {
        return result(code, message);
    }

    /**
     * 失败
     *
     * @param data       数据
     * @param returnType 返回类型
     */
    public static <T> BaseResponse<T> fail(ReturnType returnType, T data) {
        return result(returnType.getCode(), returnType.getMsg(), data);
    }

    /**
     * 判断响应是否成功
     *
     * @return boolean
     */
    public boolean isSuccessful() {
        return ReturnType.SUCCESS.getCode() == code;
    }

    /**
     * 判断响应是否失败
     *
     * @return boolean
     */
    public boolean isFailed() {
        return !isSuccessful();
    }

    /**
     * 成功响参
     */
    public static void ok(HttpServletResponse response, BaseResponse<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * 失败响参
     * @param response
     * @param result
     * @throws IOException
     */
    public static void fail(HttpServletResponse response, BaseResponse<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * 失败响参
     * @param response
     * @param status 可指定响应码，如 401 等
     * @param result
     * @throws IOException
     */
    public static void fail(HttpServletResponse response, int status, BaseResponse<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
