package org.spring.freemarker.ext.directives.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义实现的存储页面的head的title和meta{@link HtmlMeta}信息；便于后期freemarker模板渲染，统一进行页头<head>部分的数据设置。
 *
 * @date 2018-11-30 11:37:27
 */
public class HtmlHead implements Serializable {

    private static final long serialVersionUID = 7529476449445002106L;

    private String title;

    //页头<meta>的信息列表
    private List<HtmlMeta> htmlMetas = new ArrayList<HtmlMeta>(2);

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HtmlMeta> getHtmlMetas() {
        return htmlMetas;
    }

    public void setHtmlMetas(List<HtmlMeta> htmlMetas) {
        this.htmlMetas = htmlMetas;
    }

    public void addHtmlMeta(HtmlMeta htmlMeta){
        htmlMetas.add(htmlMeta);
    }
}
