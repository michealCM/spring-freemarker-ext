package org.spring.freemarker.ext.directives;

import org.spring.freemarker.ext.directives.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义指令标签统一管理，最后统一由spring容器进行管理。
 *
 * @date 2018-11-29 16:25:13
 */
@Configuration
public class DirectiveConfiguration {

    @Bean
    public HtmlBodyTemplateDirective htmlBodyTemplateDirective(){
        return new HtmlBodyTemplateDirective();
    }

    @Bean
    public LayoutTemplateDirective layoutTemplateDirective(){
        return new LayoutTemplateDirective();
    }

    @Bean
    public HtmlTitleTemplateDirective htmlTitleTemplateDirective(){
        return new HtmlTitleTemplateDirective();
    }

    @Bean
    public HtmlMetaTemplateDirective htmlMetaTemplateDirective(){
        return new HtmlMetaTemplateDirective();
    }

    @Bean
    public ScriptBodyTemplateDirective scriptBodyTemplateDirective(){
        return new ScriptBodyTemplateDirective();
    }

    @Bean
    public NestedScriptTemplateDirective nestedScriptTemplateDirective(){
        return new NestedScriptTemplateDirective();
    }

    @Bean
    public StyleBodyTemplateDirective styleBodyTemplateDirective(){
        return new StyleBodyTemplateDirective();
    }

    @Bean
    public NestedStyleTemplateDirective nestedStyleTemplateDirective(){
        return new NestedStyleTemplateDirective();
    }

    @Bean
    public CacheTemplateDirective cacheTemplateDirective(){
        return new CacheTemplateDirective();
    }

    @Bean
    public CacheBindTemplateDirective cacheBindTemplateDirective(){
        return new CacheBindTemplateDirective();
    }

    @Bean
    public CSSTemplateDirective cssTemplateDirective(){
        return new CSSTemplateDirective();
    }

    @Bean
    public JSTemplateDirective jsTemplateDirective(){
        return new JSTemplateDirective();
    }

    @Bean
    public IncludeXTemplateDirective includeXTemplateDirective(){
        return new IncludeXTemplateDirective();
    }

    @Bean
    public PaginationTemplateDirective paginationTemplateDirective(){
        return new PaginationTemplateDirective();
    }

    @Bean
    public LazyImageTemplateDirective lazyImageTemplateDirective(){
        return new LazyImageTemplateDirective();
    }
}
