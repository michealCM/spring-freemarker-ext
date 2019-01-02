package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.spring.freemarker.ext.context.RequestModelContextHolder;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;
import org.spring.freemarker.web.setting.CDNSetting;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义图片显示标签，针对部分图片使用系统的默认设置图片进行替换加载显示（配合自定义的scrollshow.js使用）。
 *
 *                  类型      是否必填      描述
 *   src           String       是       具体的图片相对路径
 *   class         String       否       定义的图片样式
 *   style         String       否       直接书写样式内容
 *   width         String       否       img的width属性
 *   height        String       否       img的height属性
 *   lazy_loading  boolean      否       是否懒加载
 *
 *   实例：
 *      <@img src="test.jpg" lazy_loading=true />
 *
 * @date 2018-12-19 22:06:52
 */
public class LazyImageTemplateDirective extends AbstractTemplateDirective {

    //自定义懒加载img标签模板
    private static final String LAZY_IMAGE_TEMPLATE = "<img %s />";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,false,loopVars,true,body,true);
        String src = super.getRequiredStringParam(params,"src");
        String classContent = super.getStringParam(params,"class");
        String styleContent = super.getStringParam(params,"style");
        String width = super.getStringParam(params,"width");
        String height = super.getStringParam(params,"height");
        String alt = super.getStringParam(params,"alt");
        Boolean lazyLoading = (Boolean) super.getRealObject(params,"lazy_loading",false);

        CDNSetting cdnSetting = ApplicationContextHelper.getSpringBean(CDNSetting.class);
        if(StringUtils.isBlank(src)){
            src = cdnSetting.getCDNServer()+cdnSetting.getDefaultImage();
        }

        StringBuffer propertiesBuffer = new StringBuffer();
        if(lazyLoading != null && lazyLoading.booleanValue()){
            propertiesBuffer.append(String.format("src=\"%s?version=%s\" data-src=\"%s?version=%s\" ",
                    cdnSetting.getCDNServer()+cdnSetting.getLoadingImage(),
                    cdnSetting.getVersion(),src,cdnSetting.getVersion()));
        }else{
            propertiesBuffer.append(String.format("src=\"%s?version=%s\" ",src,cdnSetting.getVersion()));
        }

        if(StringUtils.isNotBlank(classContent)){
            propertiesBuffer.append(String.format(" class=\"%s\" ",classContent));
        }

        if(StringUtils.isNotBlank(styleContent)){
            propertiesBuffer.append(String.format(" style=\"%s\" ",styleContent));
        }

        if(StringUtils.isNotBlank(width)){
            propertiesBuffer.append(String.format(" width=\"%s\" ",width));
        }

        if(StringUtils.isNotBlank(height)){
            propertiesBuffer.append(String.format(" height=\"%s\" ",height));
        }

        if(StringUtils.isNotBlank(alt)){
            propertiesBuffer.append(String.format(" alt=\"%s\" ",alt));
        }

        //绑定默认图到model中用于页面图片加载失败使用
        RequestModelContextHolder requestModelContextHolder = ApplicationContextHelper.getSpringBean(RequestModelContextHolder.class);
        requestModelContextHolder.getModel().put("loadingErrorImage",cdnSetting.getCDNServer()+cdnSetting.getLoadingErrorImage());
        env.getOut().write(String.format(LAZY_IMAGE_TEMPLATE,propertiesBuffer.toString()));
    }
}
