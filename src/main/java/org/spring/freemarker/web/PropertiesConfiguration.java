package org.spring.freemarker.web;

import org.spring.freemarker.common.exception.FreeMarkerException;
import org.spring.freemarker.common.utils.AssertUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * 框架的基本属性配置，定义的项目版本号（主要用户项目中的js，css和图片的版本防止更新之后的浏览器缓存）和支持的缓存等。
 * *
 * @date 2018-12-4 13:38:14
 */
public class PropertiesConfiguration implements InitializingBean {

    //系统的版本号
    private String version;

    //图片服务器地址
    private String CDNServer;

    //默认图片
    private String defaultImage;

    //加载导入图片
    private String loadingImage;

    //加载失败图片
    private String loadingErrorImage;

    //支持的缓存
    private String cacheProvidor;

    //模板缓存
    private String templateCache;

    //页面缓存
    private String pageCache;

    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.isBlank(version,new FreeMarkerException("version not null"));
        AssertUtils.isBlank(cacheProvidor,new FreeMarkerException("cacheProvidor not null"));
    }

    //基本属性的get/set方法
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCDNServer() {
        return CDNServer;
    }

    public void setCDNServer(String CDNServer) {
        this.CDNServer = CDNServer;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getLoadingImage() {
        return loadingImage;
    }

    public void setLoadingImage(String loadingImage) {
        this.loadingImage = loadingImage;
    }

    public String getLoadingErrorImage() {
        return loadingErrorImage;
    }

    public void setLoadingErrorImage(String loadingErrorImage) {
        this.loadingErrorImage = loadingErrorImage;
    }

    public String getCacheProvidor() {
        return cacheProvidor;
    }

    public void setCacheProvidor(String cacheProvidor) {
        this.cacheProvidor = cacheProvidor;
    }

    public String getTemplateCache() {
        return templateCache;
    }

    public void setTemplateCache(String templateCache) {
        this.templateCache = templateCache;
    }

    public String getPageCache() {
        return pageCache;
    }

    public void setPageCache(String pageCache) {
        this.pageCache = pageCache;
    }
}
