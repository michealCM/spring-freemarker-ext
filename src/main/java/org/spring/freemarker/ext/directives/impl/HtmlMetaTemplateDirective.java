package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.spring.freemarker.common.exception.FreeMarkerDirectiveException;
import org.spring.freemarker.ext.context.RequestModelContextHolder;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.ext.directives.model.HtmlHead;
import org.spring.freemarker.ext.directives.model.HtmlMeta;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义页头的meta标签<@meta>，负责对子页面的meta信息进行解析；之后在框架布局页面进行输出。
 * 包含两个互斥的属性；
 *               类型     是否必填      描述
 * name         String      是      对也html中<meta>的name属性
 * httpEquiv    String      是      对也html中<meta>的http-equiv属性
 *
 * 实例：
 * <@meta name="keywords">home测试，自定义freemarker</@meta>
 * <@meta httpEquiv="content-type">utf-8</@meta>
 *
 * @date 2018-11-30 11:57:20
 */
public class HtmlMetaTemplateDirective extends AbstractTemplateDirective {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,false,loopVars,true,body,false);

        String name = super.getStringParam(params,"name");
        String httpEquiv = super.getStringParam(params,"httpEquiv");
        if((StringUtils.isBlank(name) && StringUtils.isBlank(httpEquiv))
              || (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(httpEquiv)) ){
            throw new FreeMarkerDirectiveException(String.format("%s directive of Directive properties 'name' and 'httpEquiv' not null OR not be null",getClass().getSimpleName()));
        }

        RequestModelContextHolder requestModelContextHolder = ApplicationContextHelper.getSpringBean(RequestModelContextHolder.class);
        String content = super.analysisTemplateContent(body);
        if(StringUtils.isNotBlank(content)){
            HtmlHead htmlHead = (HtmlHead)requestModelContextHolder.getModel().get("htmlHead");
            htmlHead.addHtmlMeta(new HtmlMeta(httpEquiv,name,content));
        }
    }
}
