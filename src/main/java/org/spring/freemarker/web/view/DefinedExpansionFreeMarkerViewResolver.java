package org.spring.freemarker.web.view;

import org.spring.freemarker.common.utils.AssertUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 负责针对freemarker模板视图的解析处理，配置模板的后缀名（suffix）,文档类型（contentType）。
 *
 * @date 2018-11-23 16:34:56
 */
public class DefinedExpansionFreeMarkerViewResolver extends FreeMarkerViewResolver {

    /**
     * 对模板名称进行配置，前后缀组装生产具体的模板名称。
     * @param templateName
     * @return
     * @throws IllegalArgumentException 模板名称不存在（为空）
     */
    public String buildFullTemplateName(String templateName) throws IllegalArgumentException {
        AssertUtils.isBlank(templateName,new IllegalArgumentException("DefinedExpansionFreeMarkerViewResolver function buildFullTemplateName is error ==>'templateName is required!'"));
        return String.format("%s%s%s", getPrefix(), templateName, getSuffix());
    }
}
