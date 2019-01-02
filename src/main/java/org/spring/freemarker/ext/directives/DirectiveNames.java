package org.spring.freemarker.ext.directives;

/**
 * 自定义实现的指令标签的统一命名。
 *
 * @date 2018-11-26 16:13:26
 */
public enum DirectiveNames {

    DIRECTIVE_LAYOUT("layout"),
    DIRECTIVE_BODY("body"),
    DIRECTIVE_TITLE("title"),
    DIRECTIVE_META("meta"),
    DIRECTIVE_IMG("img"),
    DIRECTIVE_CACHE("cache"),
    DIRECTIVE_CACHE_BIND("cacheBind"),
    DIRECTIVE_INCLUDEX("includex"),
    DIRECTIVE_PAGINATION("pagination"),
    DIRECTIVE_SCRIPT_JS("js"),
    DIRECTIVE_SCRIPT_BODY("scriptBody"),
    DIRECTIVE_NESTED_SCRIPT("nestedScript"),
    DIRECTIVE_STYLE_CSS("css"),
    DIRECTIVE_STYLE_BODY("styleBody"),
    DIRECTIVE_NESTED_STYLE("nestedStyle");

    private String value;

    private DirectiveNames(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
