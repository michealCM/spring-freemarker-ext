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
 * 自定义JS解析标签，负责对引入简便的JS文件进行标准格式扩充；含有一个必填的属性。
 *        类型    是否必填    描述
 * src   String      是      具体的JS相对路径
 *
 * 实例：
 *    <@js src="static/js/common.js,static/js/base.js"/>
 *
 * @date 2018-12-1 23:25:25
 */
public class JSTemplateDirective extends AbstractTemplateDirective {

    private static final String DEFAULT_JS_TEMPLATE = "<script charset=\"UTF-8\" type=\"text/javascript\" src=\"%s?version=%s\"></script>";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,false,loopVars,true,body,true);
        String jsSrc = super.getRequiredStringParam(params,"src");

        //判断是否包含JS源文件内容
        if(StringUtils.isNotBlank(jsSrc)){
            CDNSetting cdnSetting = ApplicationContextHelper.getSpringBean(CDNSetting.class);
            StringBuffer jsBuffer = new StringBuffer();
            String[] srcArray = jsSrc.split(",");
            for(String src : srcArray){
                jsBuffer.append(String.format(DEFAULT_JS_TEMPLATE,src,cdnSetting.getVersion()));
            }

            env.getOut().write(jsBuffer.toString());
        }
    }
}
