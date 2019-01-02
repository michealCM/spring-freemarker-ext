package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.spring.freemarker.ext.context.RequestModelContextHolder;
import org.spring.freemarker.ext.context.TemplateDirectiveBodyContextHolder;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.ext.directives.model.HtmlBody;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;
import org.spring.freemarker.common.utils.WebUtils;
import org.spring.freemarker.web.view.DefinedExpansionFreeMarkerViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义模板框架导入指令标签，需要包含页体并且含有两个属性；
 *               类型      是否必填        描述
 *  template    String       是        具体的框架布局页面
 *  preloading  Boolean      否        是否对标签包含体中的页面信息进行提前加载
 *
 *  实例：
 *   <@layout template="layout/main_layout">
 *     <div>
 *       我是home页体内容common：${numberFormat(name,"0.00")}<BR/>
 *       <a href="index.html">跳转至indexqq</a><BR/>
 *       <a href="${url('home.html')}">跳转至home</a>
 *     </div>
 *   </@layout>
 *
 *   当主体包含有<@nestedStyle>,<@nestedScript>等标签时就需要设置preloading=true进行提前加载，
 *   否则这些标签中的内容将不会被解析出现丢失。
 *
 * @date 2018-11-28 09:51:00
 */
public class LayoutTemplateDirective extends AbstractTemplateDirective {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,false,loopVars,true,body,false);
        String template = super.getRequiredStringParam(params, "template");
        Boolean preloading = (Boolean) super.getRealObject(params,"preloading",false);

        TemplateDirectiveBodyContextHolder templateDirectiveBodyContextHolder = ApplicationContextHelper.getSpringBean(TemplateDirectiveBodyContextHolder.class);
        if(preloading != null && preloading.booleanValue()){
            templateDirectiveBodyContextHolder.setHtmlBody(new HtmlBody(super.analysisTemplateContent(body)));
        }else{
            templateDirectiveBodyContextHolder.setHtmlBody(new HtmlBody(body));
        }

        DefinedExpansionFreeMarkerViewResolver viewResolver = ApplicationContextHelper.getSpringBean(DefinedExpansionFreeMarkerViewResolver.class);
        Template layoutTemplate = env.getConfiguration().getTemplate(viewResolver.buildFullTemplateName(template),RequestContextUtils.getLocale(WebUtils.getHttpRequest()));

        RequestModelContextHolder requestModelContextHolder = ApplicationContextHelper.getSpringBean(RequestModelContextHolder.class);
        layoutTemplate.process(requestModelContextHolder.getModel(),env.getOut());
    }
}
