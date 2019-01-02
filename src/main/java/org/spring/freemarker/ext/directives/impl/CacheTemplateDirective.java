package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.spring.freemarker.cache.CacheKey;
import org.spring.freemarker.cache.ICacheRepository;
import org.spring.freemarker.common.utils.TimeLength;
import org.spring.freemarker.ext.context.RequestModelContextHolder;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.ext.directives.model.TemplateBodyCacheElement;
import org.spring.freemarker.ext.directives.model.TemplateContentCacheElement;
import org.spring.freemarker.web.FreemarkerConfiguration;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;
import org.spring.freemarker.common.utils.WebUtils;
import org.spring.freemarker.web.setting.CacheSetting;
import org.spring.freemarker.web.view.DefinedExpansionFreeMarkerViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 自定义缓存标签<@cache>，含有两个属性都是必填项；
 *               类型     是否必填        描述
 * template     String       是     需要进行缓存的模板路径
 * time         int          是     模板缓存的时间长度（单位：秒）
 *
 * 实例：<@cache template="common/common" time=60/>
 * 注意：当缓存配置为ehcache的时候，time时间无效；缓存的有效时间由ehcache.xml中配置决定。
 * 缓存的配置参见{@link FreemarkerConfiguration}
 *
 * 当template指定的模板中含有<@nestedStyle>或者<@nestedScript>等自定义标签需要和自定义标签<@cacheBind>联合使用；
 * 否则模板中的该部分自定义标签的内容将出现丢失，具体参见{@link CacheBindTemplateDirective}
 *
 * @date 2018-12-2 12:10:35
 */
public class CacheTemplateDirective extends AbstractTemplateDirective {

    private static final String  CACHE_TEMPLATE_KEY_PREFIXX = "cache_template:";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,false,loopVars,true,body,true);
        String template = super.getRequiredStringParam(params,"template");
        Number time = (Number)super.getRealObject(params,"time",true); //缓存的时间 单位：秒

        ICacheRepository cacheRepository = ApplicationContextHelper.getSpringBean(ICacheRepository.class);
        CacheSetting cacheSetting = ApplicationContextHelper.getSpringBean(CacheSetting.class);

        CacheKey temCacheKey = new CacheKey(CACHE_TEMPLATE_KEY_PREFIXX+template,cacheSetting.getTemplateCache());
        TemplateContentCacheElement contentCacheElement = (TemplateContentCacheElement)cacheRepository.loadCache(temCacheKey,TimeLength.seconds(time.longValue()));

        String templateContent = contentCacheElement == null?"":contentCacheElement.getValue();
        if(StringUtils.isBlank(templateContent)){
            DefinedExpansionFreeMarkerViewResolver viewResolver = ApplicationContextHelper.getSpringBean(DefinedExpansionFreeMarkerViewResolver.class);
            Template cacheTemplate = env.getConfiguration().getTemplate(viewResolver.buildFullTemplateName(template),RequestContextUtils.getLocale(WebUtils.getHttpRequest()));

            RequestModelContextHolder requestModelContextHolder = ApplicationContextHelper.getSpringBean(RequestModelContextHolder.class);
            templateContent = this.analysisCacheTemplate(cacheTemplate,requestModelContextHolder.getModel());
            if(StringUtils.isNotBlank(templateContent)){
                cacheRepository.setCache(temCacheKey,new TemplateContentCacheElement(templateContent),TimeLength.seconds(time.longValue()));
            }
        }

        CacheKey bindCacheKey = new CacheKey(CacheBindTemplateDirective.CACHE_BIND_KEY_PREFIXX+template,cacheSetting.getTemplateCache());
        TemplateContentCacheElement cacheBindValue = (TemplateContentCacheElement)cacheRepository.loadCache(bindCacheKey,TimeLength.seconds(time.longValue()));
        if(null != cacheBindValue){
            env.getOut().write(cacheBindValue.getValue());
        }

        env.getOut().write(templateContent);
    }

    /**
     * 解析需要缓存的模板内容并返回。
     * @param cacheTemplate
     * @param dataModel
     * @return
     * @throws TemplateException
     * @throws IOException
     */
    private String analysisCacheTemplate(Template cacheTemplate,Map dataModel)throws TemplateException, IOException{
        ByteArrayOutputStream bos = null;
        PrintWriter pw = null;
        try {
            bos = new ByteArrayOutputStream(1024);
            pw = new PrintWriter(bos);
            cacheTemplate.process(dataModel,pw);
            pw.flush();
            return new String(bos.toByteArray());
        } finally {
            if(pw != null) pw.close();
            if(bos != null) bos.close();
        }
    }
}
