package org.spring.freemarker.ext.directives.impl;

import freemarker.core.Environment;
import freemarker.core.TemplateElement;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.spring.freemarker.cache.CacheKey;
import org.spring.freemarker.cache.ICacheRepository;
import org.spring.freemarker.common.utils.TimeLength;
import org.spring.freemarker.ext.directives.AbstractTemplateDirective;
import org.spring.freemarker.ext.directives.model.TemplateBodyCacheElement;
import org.spring.freemarker.ext.directives.model.TemplateContentCacheElement;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;
import org.spring.freemarker.web.setting.CacheSetting;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义缓存绑定标签<@cacheBind>，含有一个必填的属性
 *        类型     是否必填        描述
 * key   String       是        绑定的模板名称含有路径信息（和<@cache>标签的template的内容一致）
 * time   int         是        模板缓存的时间长度（单位：秒）
 *
 * 该标签指令主要用于和<@cache>标签一起使用对部分页面信息进行绑定；然后重新解析，解决缓存页面针对之定义标签内容丢失的问题。
 * 注意该标签内的@nestedScript和@nestedStyle会导致内容丢失；缓存页不页面不推荐使用该两个标签。
 * 实例：
 * <@cacheBind key="common/common" time=60>
 *
 *      <@css href="static/css/common.css"/>
 *      <style>

 *      </style>
 *
 *      <script type="text/javascript">
 *      </script>
 *
 * </@cacheBind>
 *
 * @date 2018-12-6 20:52:35
 */
public class CacheBindTemplateDirective extends AbstractTemplateDirective {

    public static final String  CACHE_BIND_KEY_PREFIXX = "cache_bind:";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        this.validateTemplateDirectiveParams(params,false,loopVars,true,body,false);
        String cacheKey = super.getRequiredStringParam(params,"key"); //缓存key
        Number time = (Number)super.getRealObject(params,"time",true); //缓存的时间 单位：秒

        ICacheRepository cacheRepository = ApplicationContextHelper.getSpringBean(ICacheRepository.class);
        CacheSetting cacheSetting = ApplicationContextHelper.getSpringBean(CacheSetting.class);
        cacheRepository.setCache(new CacheKey(CACHE_BIND_KEY_PREFIXX+cacheKey,cacheSetting.getTemplateCache()),
                new TemplateContentCacheElement(super.analysisTemplateContent(body)),TimeLength.seconds(time.longValue()));
    }
}
