package com.szq.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Log {
    public static Logger sdk = LoggerFactory.getLogger("sdk_logger");

    public static void init(String logName) {
        MDC.put("logName", logName);
        MDC.put("uuid", CodecUtils.getRequestUUID());
    }
}