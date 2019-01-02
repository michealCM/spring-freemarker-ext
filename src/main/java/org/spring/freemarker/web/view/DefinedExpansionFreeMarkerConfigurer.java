package org.spring.freemarker.web.view;

import freemarker.template.TemplateException;
import org.spring.freemarker.ext.directives.DirectiveConfiguration;
import org.spring.freemarker.ext.directives.DirectiveNames;
import org.spring.freemarker.ext.functions.FunctionConfiguration;
import org.spring.freemarker.ext.functions.FunctionNames;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 对freemarker模板路径配置，包括加载自定义指令标签和函数到freemarkera的rootModel中。
 *
 * @date 2018-11-23 16:39:04
 */
public class DefinedExpansionFreeMarkerConfigurer extends FreeMarkerConfigurer {

    private boolean isLoaderFreemarkerVariables;

    @Inject
    private DirectiveConfiguration directiveConfiguration;

    @Inject
    private FunctionConfiguration functionConfiguration;

    @Override
    public void setFreemarkerVariables(Map<String, Object> variables) {
        registerFunctions(variables);
        registerDirectives(variables);
        super.setFreemarkerVariables(variables);
        this.isLoaderFreemarkerVariables = true;
    }

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        //判断是否已经初始化加载自定义函数和标签
        if(!isLoaderFreemarkerVariables){
            Map<String, Object> variables = new HashMap<String, Object>();
            this.setFreemarkerVariables(variables);
        }
        super.afterPropertiesSet();
    }

    /**
     * 加载注入自定义函数。
     * @param variables
     */
    private void registerFunctions(Map<String, Object> variables){
        variables.put(FunctionNames.FUNCTION_URL.getValue(),functionConfiguration.urlFunction());
        variables.put(FunctionNames.FUNCTION_NUMBER_FORMAT.getValue(),functionConfiguration.numberFormatFunction());
    }

    /**
     *加载注入自定义指令标签。
     * @param variables
     */
    private void registerDirectives(Map<String, Object> variables){
        variables.put(DirectiveNames.DIRECTIVE_LAYOUT.getValue(),directiveConfiguration.layoutTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_BODY.getValue(),directiveConfiguration.htmlBodyTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_TITLE.getValue(),directiveConfiguration.htmlTitleTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_META.getValue(),directiveConfiguration.htmlMetaTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_IMG.getValue(),directiveConfiguration.lazyImageTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_SCRIPT_BODY.getValue(),directiveConfiguration.scriptBodyTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_NESTED_SCRIPT.getValue(),directiveConfiguration.nestedScriptTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_STYLE_BODY.getValue(),directiveConfiguration.styleBodyTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_NESTED_STYLE.getValue(),directiveConfiguration.nestedStyleTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_CACHE.getValue(),directiveConfiguration.cacheTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_CACHE_BIND.getValue(),directiveConfiguration.cacheBindTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_STYLE_CSS.getValue(),directiveConfiguration.cssTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_SCRIPT_JS.getValue(),directiveConfiguration.jsTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_INCLUDEX.getValue(),directiveConfiguration.includeXTemplateDirective());
        variables.put(DirectiveNames.DIRECTIVE_PAGINATION.getValue(),directiveConfiguration.paginationTemplateDirective());
    }
}
