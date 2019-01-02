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
 *  自定义JS嵌入标签，<@nestedScript>负责解析标签包含体中的内容；最终在<@scriptBody/>标签进行内容的输出。
 *
 *  实例：
 *  <@nestedScript>
 *   <script type="text/javascript">
 *       function login(){
 *           ........
 *       }
 *   </script>
 *  </@nestedScript>
        *
        * @date 2018-11-30 21:32:38
        */
public class NestedScriptTemplateDirective extends AbstractTemplateDirective {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,true,loopVars,true,body,false);
        TemplateDirectiveBodyContextHolder templateDirectiveBodyContextHolder = ApplicationContextHelper.getSpringBean(TemplateDirectiveBodyContextHolder.class);
        templateDirectiveBodyContextHolder.addNestedScriptBody(body);
    }
}
