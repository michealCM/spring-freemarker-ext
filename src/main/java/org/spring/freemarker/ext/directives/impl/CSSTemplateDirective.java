package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;
import org.spring.freemarker.web.setting.CDNSetting;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义样式解析标签，负责对引入简便的样式进行标准格式扩充；含有一个必填的属性。
 *          类型    是否必填    描述
 * href   String      是      具体的样式相对路径
 *
 * 实例：
 *    <@css href="static/css/common.css,static/css/base.css,static/css/body/header.css"/>
 *
 * @date 2018-12-1 23:26:22
 */
public class CSSTemplateDirective extends AbstractTemplateDirective {

    private static final String DEFAULT_CSS_TEMPLATE  = "<link type=\"text/css\" rel=\"stylesheet\" href=\"%s?version=%s\">";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,false,loopVars,true,body,true);
        String cssHref = super.getRequiredStringParam(params,"href");

        //判断是否包含css源文件路径内容
        if(StringUtils.isNotBlank(cssHref)){
            CDNSetting cdnSetting = ApplicationContextHelper.getSpringBean(CDNSetting.class);
            StringBuffer cssBuffer = new StringBuffer();
            String[] hrefArray = cssHref.split(",");

            for(String href : hrefArray){
                cssBuffer.append(String.format(DEFAULT_CSS_TEMPLATE,href,cdnSetting.getVersion()));
            }

            env.getOut().write(cssBuffer.toString());
        }
    }
}
