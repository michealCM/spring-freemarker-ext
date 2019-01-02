package org.spring.freemarker.ext.directives.impl;

import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.spring.freemarker.ext.context.RequestModelContextHolder;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;
import org.spring.freemarker.common.utils.WebUtils;
import org.spring.freemarker.web.view.DefinedExpansionFreeMarkerViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义导入模板标签<@includex>，当template对应的模板不存的时候，会使用default_template指定的模板进行显示；
 * 包含两个必填属性。
 *                      类型     是否必填     描述
 * template            String      是     模板的相对路径
 * default_template    String      是     默认的替换模板相对路径
 *
 * 实例：
 *   <@includex template="common/common_111" default_template="common/common"/>
 *
 * 可以在后台controller中指定模板：
 *   <@includex template="${template}" default_template="common/common"/>
 *
 * @date 2018-12-1 23:30:48
 */
public class IncludeXTemplateDirective extends AbstractTemplateDirective {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,false,loopVars,true,body,true);
        String template = super.getRequiredStringParam(params,"template");
        String defaultTemplate = super.getRequiredStringParam(params,"default_template");

        DefinedExpansionFreeMarkerViewResolver viewResolver = ApplicationContextHelper.getSpringBean(DefinedExpansionFreeMarkerViewResolver.class);
        TemplateLoader templateLoader = env.getConfiguration().getTemplateLoader();

        //判断模板是否存在
        String fullTemplateName = viewResolver.buildFullTemplateName(template);
        RequestModelContextHolder requestModelContextHolder = ApplicationContextHelper.getSpringBean(RequestModelContextHolder.class);
        if(templateLoader.findTemplateSource(fullTemplateName) != null){
            Template fullTemplate = env.getConfiguration().getTemplate(fullTemplateName,RequestContextUtils.getLocale(WebUtils.getHttpRequest()));
            fullTemplate.process(requestModelContextHolder.getModel(),env.getOut());
        }else{
            Template defTemplate = env.getConfiguration().getTemplate(viewResolver.buildFullTemplateName(defaultTemplate),
                    RequestContextUtils.getLocale(WebUtils.getHttpRequest()));
            defTemplate.process(requestModelContextHolder.getModel(),env.getOut());
        }
    }
}
