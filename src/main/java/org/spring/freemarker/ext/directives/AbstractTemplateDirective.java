package org.spring.freemarker.ext.directives;

import freemarker.ext.beans.StringModel;
import freemarker.template.*;
import org.spring.freemarker.common.exception.FreeMarkerDirectiveException;
import org.spring.freemarker.common.utils.AssertUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 自定义指令标签父类，负责标签的一些公共功能的函数编写。
 *
 * @date 2018-11-27 10:16:35
 */
public abstract class AbstractTemplateDirective implements TemplateDirectiveModel {

    /**
     * 对指令标签的execute方法参数的统一验证，保证自定义指令标签的合法规范性。
     *
     * @param params
     * @param isAllowParamsNull
     * @param loopVars
     * @param isAllowloopVarsNull
     * @param body
     * @param isAllowBodyNull
     */
    protected void validateTemplateDirectiveParams(Map params, boolean isAllowParamsNull, TemplateModel[] loopVars, boolean isAllowloopVarsNull,
                                                   TemplateDirectiveBody body, boolean isAllowBodyNull){
        //验证标签体重的参数
        if(isAllowParamsNull){
            AssertUtils.notEmptyMap(params,new FreeMarkerDirectiveException(String.format("%s directive of params not contain any conetnt",getClass().getSimpleName())));
        }else{
            AssertUtils.isEmptyMap(params,new FreeMarkerDirectiveException(String.format("%s directive of params should be contain any conetnt",getClass().getSimpleName())));
        }

        //验证循环参数
        if(isAllowloopVarsNull){
            AssertUtils.notNullArray(loopVars,new FreeMarkerDirectiveException(String.format("%s directive of loopVars must be null",getClass().getSimpleName())));
        }else{
            AssertUtils.nullArray(loopVars,new FreeMarkerDirectiveException(String.format("%s directive of loopVars must contain content",getClass().getSimpleName())));
        }

        //验证标签体
        if(isAllowBodyNull){
            AssertUtils.notNull(body,new FreeMarkerDirectiveException(String.format("%s directive of body must be null",getClass().getSimpleName())));
        }else{
            AssertUtils.isNull(body,new FreeMarkerDirectiveException(String.format("%s directive of body is required",getClass().getSimpleName())));
        }
    }

    /**
     * 对指令标签的包涵体中的内容进行提前解析成html处理；便于对包含体中含有的
     * <@nestedStyle>,<@nestedScript>等标签进行提前解析防止该部分内容丢失。
     * @param body
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    protected String analysisTemplateContent(TemplateDirectiveBody body) throws IOException, TemplateException {
        ByteArrayOutputStream bos = null;
        PrintWriter pw = null;
        try {
            bos = new ByteArrayOutputStream(1024);
            pw = new PrintWriter(bos);
            body.render(pw);
            pw.flush();
            return new String(bos.toByteArray());
        } finally {
            if(pw != null) pw.close();
            if(bos != null) bos.close();
        }
    }

    /**
     * 解析参数params获取指定的key的value；且对应的key是必须存在的。
     * @param params
     * @param key
     * @return
     * @throws TemplateModelException
     */
    protected String getRequiredStringParam(Map params, String key) throws TemplateModelException {
        if(params.containsKey(key)){
            Object value = params.get(key);
            if (!(value instanceof SimpleScalar)){
                throw new TemplateModelException(String.format("%s param is required by %s, and must be string", key, getClass().getSimpleName()));
            }
            return ((SimpleScalar) value).getAsString();
        }
        throw new TemplateModelException(String.format("%s param is required by %s", key, getClass().getSimpleName()));
    }

    /**
     * 解析参数params获取指定的key的value。
     * @param params
     * @param key
     * @return
     * @throws TemplateModelException
     */
    protected String getStringParam(Map params, String key) throws TemplateModelException {
        Object value = params.get(key);
        if (value == null) return null;
        if (!(value instanceof SimpleScalar))
            throw new TemplateModelException(String.format("%s param must be string in %s", key, getClass().getSimpleName()));
        return ((SimpleScalar) value).getAsString();
    }

    /**
     * 从params参数中获取对应key的value并转换为value对应的真实类型。
     * @param params
     * @param key
     * @param isRequired
     * @return
     * @throws TemplateModelException
     */
    protected Object getRealObject(Map params, String key, boolean isRequired) throws TemplateModelException {
        Object value = params.get(key);
        if(null == value && isRequired){
            throw new TemplateModelException(String.format("%s directive of params not contain %s",getClass().getSimpleName(),key));
        }

        if(value instanceof  SimpleSequence){
            return ((SimpleSequence)value).toList();
        } else if (value instanceof StringModel) {
            return ((StringModel)value).getWrappedObject();
        } else if(value instanceof SimpleScalar) {
            return ((SimpleScalar)value).getAsString();
        } else if(value instanceof SimpleNumber) {
            return ((SimpleNumber)value).getAsNumber();
        } else if(value instanceof SimpleDate) {
            return ((SimpleDate)value).getAsDate();
        }else if(value instanceof TemplateBooleanModel){
            return ((TemplateBooleanModel)value).getAsBoolean();
        }

        return value;
    }
}
