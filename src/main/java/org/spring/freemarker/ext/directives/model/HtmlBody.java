package org.spring.freemarker.ext.directives.model;

import freemarker.template.TemplateDirectiveBody;
import org.spring.freemarker.ext.directives.impl.LayoutTemplateDirective;

import java.io.Serializable;

/**
 * 存储即将要输出到页面的自定义指令标签<@body/>的内容，通过对isPreloading值的判断内容是否已经被提前加载解析为html；
 * 具体详见类{@link LayoutTemplateDirective}
 *
 * @date 2018-12-2 18:09:20
 */
public class HtmlBody implements Serializable{

    private static final long serialVersionUID = -4349921820410121598L;

    private TemplateDirectiveBody directiveBody;

    private String htmlBodyStr;

    private boolean isPreloading;

    public HtmlBody(TemplateDirectiveBody directiveBody){
        this.directiveBody = directiveBody;
    }

    public HtmlBody(String htmlBodyStr){
        this.isPreloading = true;
        this.htmlBodyStr = htmlBodyStr;
    }

    public TemplateDirectiveBody getDirectiveBody() {
        return directiveBody;
    }

    public void setDirectiveBody(TemplateDirectiveBody directiveBody) {
        this.directiveBody = directiveBody;
    }

    public String getHtmlBodyStr() {
        return htmlBodyStr;
    }

    public void setHtmlBodyStr(String htmlBodyStr) {
        this.htmlBodyStr = htmlBodyStr;
    }

    public boolean isPreloading() {
        return isPreloading;
    }

    public void setPreloading(boolean preloading) {
        isPreloading = preloading;
    }
}
