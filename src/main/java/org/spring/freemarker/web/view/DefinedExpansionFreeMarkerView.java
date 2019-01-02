package org.spring.freemarker.web.view;

import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;
import org.spring.freemarker.common.exception.FreeMarkerException;
import org.spring.freemarker.ext.context.RequestModelContextHolder;
import org.spring.freemarker.ext.directives.model.HtmlHead;
import org.spring.freemarker.common.utils.WebUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * 自定义扩展Spring mvc的freemarker视图类(@link FreemarkerView )；实现自定义的视图操作。
 *
 * @date 2018-11-23 17:26:59
 */
public class DefinedExpansionFreeMarkerView extends FreeMarkerView {

    private RequestModelContextHolder requestModelContextHolder;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        super.setExposeRequestAttributes(true);
        ApplicationContext applicationContext = getApplicationContext();
        requestModelContextHolder = applicationContext.getBean(RequestModelContextHolder.class);
    }

    @Override
    public Template getTemplate(String name, Locale locale) throws IOException {
        return super.getTemplate(name,locale);
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model,HttpServletRequest request,HttpServletResponse response)
            throws Exception {
        try {
            requestModelContextHolder.bindModel(model);
            exposeHelpers(model, request);
            doRender(model, request, response);
        } finally {
            requestModelContextHolder.releaseModel();
        }
    }

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        model.put("HttpHeader", WebUtils.getHttpHeaders(request));
        model.put("ctx", request.getContextPath());
        model.put("htmlHead",new HtmlHead());
    }

    @Override
    protected SimpleHash buildTemplateModel(Map<String, Object> model,HttpServletRequest request,HttpServletResponse response) {
        SimpleHash retVal = super.buildTemplateModel(model, request, response);
        Map<String, Object> bindedModel = requestModelContextHolder.getModel();
        try {
            bindedModel.put(FreemarkerServlet.KEY_JSP_TAGLIBS, retVal.get(FreemarkerServlet.KEY_JSP_TAGLIBS));
            bindedModel.put(FreemarkerServlet.KEY_APPLICATION, retVal.get(FreemarkerServlet.KEY_APPLICATION));
        } catch (TemplateModelException e) {
            throw new FreeMarkerException(e.getMessage(), e);
        }
        bindedModel.put(FreemarkerServlet.KEY_REQUEST, new HttpRequestHashModel(request, response, getObjectWrapper()));
        bindedModel.put(FreemarkerServlet.KEY_REQUEST_PARAMETERS, new HttpRequestParametersHashModel(request));
        return retVal;
    }
}
