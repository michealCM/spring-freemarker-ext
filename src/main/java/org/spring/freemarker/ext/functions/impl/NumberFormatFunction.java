package org.spring.freemarker.ext.functions.impl;

import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;
import org.spring.freemarker.ext.functions.AbstractFunction;
import org.springframework.util.Assert;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 数字格式化函数，
 *
 * @date 2018-11-26 17:24:47
 */
public class NumberFormatFunction extends AbstractFunction {

    private static DecimalFormat numFormatter = new DecimalFormat();

    private static final String DEFAULT_NUMBER_FORMAT = "#.##";

    @Override
    @SuppressWarnings("all")
    public Object exec(List arguments) throws TemplateModelException {
        Assert.notEmpty(arguments,"the augument when using numberFormat function is not allowed to be empty");
        SimpleNumber num = (SimpleNumber)arguments.get(0);
        if(num == null) return "";
        String numberFormat = DEFAULT_NUMBER_FORMAT;
        if(arguments.size() > 1) {
            SimpleScalar format = (SimpleScalar)arguments.get(1);
            if(format != null) {
                numberFormat = format.getAsString();
            }
        }
        numFormatter.applyPattern(numberFormat);
        return numFormatter.format(num.getAsNumber());
    }
}
