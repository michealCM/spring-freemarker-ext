package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.spring.freemarker.ext.context.RequestModelContextHolder;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.ext.directives.model.HtmlHead;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义<@title>标签，标签体内容会被解析至框架布局的html的<head>中的<title></title>之中。
 *
 * 实例：
 * <@title>我是home页</@title>
 *
 * @date 2018-11-29 21:41:23
 */
public class HtmlTitleTemplateDirective extends AbstractTemplateDirective {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,true,loopVars,true,body,false);
        RequestModelContextHolder requestModelContextHolder = ApplicationContextHelper.getSpringBean(RequestModelContextHolder.class);
        String title = super.analysisTemplateContent(body);
        if(StringUtils.isNoneBlank(title)){
           HtmlHead htmlHead = (HtmlHead)requestModelContextHolder.getModel().get("htmlHead");
           htmlHead.setTitle(title);
        }
    }
}
