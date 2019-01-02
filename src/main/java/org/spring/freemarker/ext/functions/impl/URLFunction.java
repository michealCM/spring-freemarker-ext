package org.spring.freemarker.ext.functions.impl;

import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.spring.freemarker.common.exception.FreeMarkerFunctionException;
import org.spring.freemarker.common.utils.AssertUtils;
import org.spring.freemarker.ext.context.RequestModelContextHolder;
import org.spring.freemarker.ext.functions.AbstractFunction;
import org.spring.freemarker.common.utils.WebUtils;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * @date 2018-11-26 22:04:37
 */
public class URLFunction extends AbstractFunction {

    @Inject
    private RequestModelContextHolder requestModelContextHolder;

    @Override
    @SuppressWarnings("all")
    public Object exec(List arguments) throws TemplateModelException {
        AssertUtils.isEmpty(arguments,new FreeMarkerFunctionException("the parameter when using url function must not be empty"));
        String url = String.valueOf(arguments.get(0)).replace("\\", "/");
        if(url.startsWith("/")) {
            String ctx = String.valueOf(requestModelContextHolder.getModel().get("ctx"));
            url = buildUrlParams(arguments, url);
            return String.format("%s%s", ctx, url);
        } else if(url.startsWith("./")) {
            url = url.replaceFirst("\\.\\/", "");
        }
        String servletPath = WebUtils.getHttpRequest().getServletPath();
        StringBuffer retVal = new StringBuffer();
        if (servletPath.startsWith("/")) {
            servletPath = servletPath.replaceFirst("\\/", "");
        }
        int servletLevel = StringUtils.countMatches(servletPath, "/");
        for (int i = 0; i < servletLevel; i++) {
            retVal.append("../");
        }
        url = buildUrlParams(arguments, url);
        retVal.append(url.replace("../", ""));
        return retVal.toString();
    }

    private String buildUrlParams(List arguments, String url) throws TemplateModelException {
        if(arguments.size() > 1) {
            if(url.indexOf("?") != -1) {
                for(int i=1;i<arguments.size();i++) {
                    url += "&" + getRealObject(arguments, i);
                }
            } else {
                for(int i=1;i<arguments.size();i++) {
                    if(i==1)
                        url += "?" + getRealObject(arguments, i);
                    else
                        url += "&" + getRealObject(arguments, i);
                }
            }
        }
        return url;
    }

}
