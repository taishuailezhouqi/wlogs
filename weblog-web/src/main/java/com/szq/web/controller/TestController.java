package com.szq.web.controller;

import com.szq.web.exception.BizException;
import io.jsonwebtoken.JwtParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/admin/test")
public class TestController {


    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    public static void main(String[] args) {
        // 获取当前日期
        Date today = new Date();
        System.out.println("今天的日期: " + today);

        // 创建 Calendar 实例
        Calendar calendar = Calendar.getInstance();

        // 设置当前日期
        calendar.setTime(today);
        System.out.println("calendar = " + calendar);
        // 当前日期加 7 天
        calendar.add(Calendar.DATE, 7);

        // 获取加 7 天后的日期
        Date sevenDaysLater = calendar.getTime();

        System.out.println("7 天后的日期: " + sevenDaysLater);
    }

}
