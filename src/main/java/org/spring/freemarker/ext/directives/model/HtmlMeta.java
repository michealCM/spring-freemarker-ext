package org.spring.freemarker.ext.directives.model;

import java.io.Serializable;

/**
 * 自定义存储html页面的<meta>标签的内容；便于后期模板渲染的时候获取进行页头的meta信息统一设置{@link HtmlHead}。
 * 类型的属性分别对应 html<meta>标签的属性，具体参考网页<meta>属性说明。
 *
 * @date 2018-11-30 11:41:23
 */
public class HtmlMeta implements Serializable {

    private static final long serialVersionUID = -8696864208958351179L;

    private String httpEquiv;

    private String name;

    private String content;

    public HtmlMeta(String httpEquiv, String name, String content){
        this.httpEquiv = httpEquiv;
        this.name = name;
        this.content = content;
    }

    public String getHttpEquiv() {
        return httpEquiv;
    }

    public void setHttpEquiv(String httpEquiv) {
        this.httpEquiv = httpEquiv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
