package org.spring.freemarker.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @date 2018-11-27 11:09:49
 */
public class WebUtils {

    public static HttpServletRequest getHttpRequest() {
        return  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static Map<String, String> getHttpHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }
}
