package org.spring.freemarker.ext.context;

import freemarker.template.TemplateDirectiveBody;
import org.spring.freemarker.common.exception.FreeMarkerDirectiveException;
import org.spring.freemarker.common.utils.AssertUtils;
import org.spring.freemarker.ext.directives.impl.*;
import org.spring.freemarker.ext.directives.model.HtmlBody;

import java.util.ArrayList;
import java.util.List;

/**
 *  针对每一次request请求，存储自定义指令标签的标签体内容；
 *  包括需要输出至主体的标签<@body> {@link HtmlBodyTemplateDirective} 只有一个TemplateDirectiveBody；
 *  输出至css主体标签<@styleBody> {@link StyleBodyTemplateDirective} 可以包含多个<@nestedStyle> {@link NestedStyleTemplateDirective} 的TemplateDirectiveBody；
 *  输出至js主体标签<@scriptBody> {@link ScriptBodyTemplateDirective} 可以包含多个<@nestedScript> {@link NestedScriptTemplateDirective} 的TemplateDirectiveBody；
 * @date 2018-12-1 19:10:39
 */
public class TemplateDirectiveBodyContextHolder {

    private HtmlBody htmlBody;

    private List<TemplateDirectiveBody> nestedStyleBodys = new ArrayList<TemplateDirectiveBody>();

    private List<TemplateDirectiveBody> nestedScriptBodys = new ArrayList<TemplateDirectiveBody>();

    public HtmlBody getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(HtmlBody htmlBody) {
        this.htmlBody = htmlBody;
    }

    public List<TemplateDirectiveBody> getNestedStyleBodys() {
        return nestedStyleBodys;
    }

    public void setNestedStyleBodys(List<TemplateDirectiveBody> nestedStyleBodys) {
        this.nestedStyleBodys = nestedStyleBodys;
    }

    public List<TemplateDirectiveBody> getNestedScriptBodys() {
        return nestedScriptBodys;
    }

    public void setNestedScriptBodys(List<TemplateDirectiveBody> nestedScriptBodys) {
        this.nestedScriptBodys = nestedScriptBodys;
    }

    public void addNestedStyleBody(TemplateDirectiveBody templateDirectiveBody){
        AssertUtils.isNull(templateDirectiveBody,new FreeMarkerDirectiveException("add NestStyle param of TemplateDirectiveBody is null"));
        this.nestedStyleBodys.add(templateDirectiveBody);
    }

    public void addNestedScriptBody(TemplateDirectiveBody templateDirectiveBody){
        AssertUtils.isNull(templateDirectiveBody,new FreeMarkerDirectiveException("add NestScript param of TemplateDirectiveBody is null"));
        this.nestedScriptBodys.add(templateDirectiveBody);
    }
}
