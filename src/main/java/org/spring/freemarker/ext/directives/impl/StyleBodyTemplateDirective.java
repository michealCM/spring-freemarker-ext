package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.spring.freemarker.ext.context.TemplateDirectiveBodyContextHolder;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 自定义的CSS样式输出主体标签<@styleBody/>，负责输出解析<@nestedStyle>标签体中的内容；
 * {@link NestedStyleTemplateDirective}
 *
 * 实例：<@styleBody/>
 *
 * @date 2018-11-30 15:27:36
 */
public class StyleBodyTemplateDirective extends AbstractTemplateDirective {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,true,loopVars,true,body,true);
        TemplateDirectiveBodyContextHolder templateDirectiveBodyContextHolder = ApplicationContextHelper.getSpringBean(TemplateDirectiveBodyContextHolder.class);
        List<TemplateDirectiveBody> directiveBodies = templateDirectiveBodyContextHolder.getNestedStyleBodys();

        if(!CollectionUtils.isEmpty(directiveBodies)){
            StringBuffer styleStringBuffer = new StringBuffer();
            for(TemplateDirectiveBody directiveBody : directiveBodies){
                styleStringBuffer.append(this.analysisTemplateContent(directiveBody));
            }
            env.getOut().write(styleStringBuffer.toString());
        }
    }
}
