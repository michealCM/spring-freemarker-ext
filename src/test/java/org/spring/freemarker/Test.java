package org.spring.freemarker;

import org.spring.freemarker.common.exception.FreeMarkerException;
import org.spring.freemarker.common.utils.AssertUtils;
import org.spring.freemarker.ext.context.RequestModelContextHolder;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String args[]){
      new Test().check(null);
    }


    public void check(String param){
        AssertUtils.isBlank(param,new FreeMarkerException("asdasda"));
    }

}
