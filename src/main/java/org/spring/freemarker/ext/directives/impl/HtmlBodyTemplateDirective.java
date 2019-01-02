package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.spring.freemarker.ext.context.TemplateDirectiveBodyContextHolder;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.ext.directives.model.HtmlBody;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义主体标签<@body>，对自定义框架布局标签<@layout>的内容体的输出；具体参考{@link LayoutTemplateDirective}。
 *
 * 实例：
 * <body>
 *   <#include "head.html"/>
 *     <@body />
 *   <#include "footer.html"/>
 * </body>
 *
 * @date 2018-11-28 09:35:34
 */
public class HtmlBodyTemplateDirective extends AbstractTemplateDirective {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,true,loopVars,true,body,true);
        TemplateDirectiveBodyContextHolder templateDirectiveBodyContextHolder = ApplicationContextHelper.getSpringBean(TemplateDirectiveBodyContextHolder.class);
        HtmlBody htmlBody = templateDirectiveBodyContextHolder.getHtmlBody();
        if(null == htmlBody.getDirectiveBody() && StringUtils.isBlank(htmlBody.getHtmlBodyStr())){
            throw new TemplateModelException(String.format("%s directive of directiveBodyThreadLocal not contain a valid TemplateDirectiveBody",getClass().getSimpleName()));
        }

        //判断是否预加载
        if(htmlBody.isPreloading()){
            env.getOut().write(htmlBody.getHtmlBodyStr());
        }else{
            htmlBody.getDirectiveBody().render(env.getOut());
        }
    }
}
