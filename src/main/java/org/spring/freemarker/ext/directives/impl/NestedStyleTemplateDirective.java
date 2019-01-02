package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.spring.freemarker.ext.context.TemplateDirectiveBodyContextHolder;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义样式嵌入标签，<@nestedStyle>负责解析标签包含体中的内容；最终在<@styleBody/>标签进行内容的输出。
 *
 * 实例：
 *  <@nestedStyle>
 *    <style>
 *       .header{
 *           .......
 *       }
 *    </style>
 * </@nestedStyle>
 *
 * @date 2018-11-30 21:33:27
 */
public class NestedStyleTemplateDirective extends AbstractTemplateDirective {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,true,loopVars,true,body,false);
        TemplateDirectiveBodyContextHolder templateDirectiveBodyContextHolder = ApplicationContextHelper.getSpringBean(TemplateDirectiveBodyContextHolder.class);
        templateDirectiveBodyContextHolder.addNestedStyleBody(body);
    }
}
