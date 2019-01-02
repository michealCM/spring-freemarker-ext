package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;
import org.spring.freemarker.common.utils.WebUtils;
import org.spring.freemarker.web.view.DefinedExpansionFreeMarkerViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;
import java.util.Map;

/**
 *
 *
 * @date 2018-12-3 15:43:41
 */
public class PaginationTemplateDirective extends AbstractTemplateDirective {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,false,loopVars,true,body,true);
        String template = super.getRequiredStringParam(params,"template");
        String actionUrl = super.getRequiredStringParam(params,"action-url");

        DefinedExpansionFreeMarkerViewResolver viewResolver = ApplicationContextHelper.getSpringBean(DefinedExpansionFreeMarkerViewResolver.class);
        Template pageTemplate = env.getConfiguration().getTemplate(viewResolver.buildFullTemplateName(template),
                RequestContextUtils.getLocale(WebUtils.getHttpRequest()));

        pageTemplate.process(null,env.getOut());
    }

}
