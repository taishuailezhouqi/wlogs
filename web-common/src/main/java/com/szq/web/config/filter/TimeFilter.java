package com.szq.web.config.filter;


import com.szq.web.utils.CodecUtils;
import com.szq.web.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author roc
 */
@WebFilter(urlPatterns = "/*")
public class TimeFilter implements Filter {
    private static final String LOG_UUID = "uuid";
    private static final String LOG_NAME = "logName";

    private final Logger logger = LoggerFactory.getLogger("time_logger");

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String uri = request.getRequestURI();
        long begin = System.currentTimeMillis();
        try {
            logInit(request);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            logger.info(uri + " - " + (System.currentTimeMillis() - begin));
            logClear();
        }
    }

    @Override
    public void destroy() {

    }

    private void logInit(HttpServletRequest request) {
        String loginName = MDC.get(LOG_NAME);
        String uuid = MDC.get(LOG_UUID);
        String uri = request.getRequestURI();
        uri = uri.replaceFirst("^/", "");
        Log.sdk.info("loginName - {} - uuid - {} - uri - {}", loginName, uuid, uri);
        if (StringUtils.isEmpty(loginName)) {
            MDC.put(LOG_NAME, uri.replaceAll("/", "_"));
        }
        if (StringUtils.isEmpty(uuid)) {
            MDC.put(LOG_UUID, CodecUtils.getRequestUUID());
        }
        Log.sdk.info("loginName - {} - uuid - {} - uri - {}", MDC.get(LOG_NAME), MDC.get(LOG_UUID), uri);
    }

    private void logClear() {
        MDC.clear();
    }
}
