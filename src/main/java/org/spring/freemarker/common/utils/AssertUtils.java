package org.spring.freemarker.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 断言工具。
 *
 * @date 2018-11-28 11:31:47
 */
public class AssertUtils {

    public static void isBlank(String str,RuntimeException runtimeException){
        if(str == null || str.trim().length() == 0){
            throwException(runtimeException);
        }
    }

    public static void isNull(Object obj,RuntimeException runtimeException){
        if(obj == null){
            throwException(runtimeException);
        }
    }

    public static void notNull(Object obj,RuntimeException runtimeException){
        if(obj != null){
            throwException(runtimeException);
        }
    }

    public static void isEmpty(Collection<?> collection,RuntimeException runtimeException){
        if(collection == null || collection.size() == 0){
            throwException(runtimeException);
        }
    }

    public static void hasLength(Collection<?> target,int specifiedLength,RuntimeException runtimeException) {
        isEmpty(target,runtimeException);
        if(target.size() < specifiedLength){
            throwException(runtimeException);
        }
    }

    public static void nullArray(Object[] arrayObj,RuntimeException runtimeException){
        if(null == arrayObj || arrayObj.length == 0){
            throwException(runtimeException);
        }
    }

    public static void notNullArray(Object[] arrayObj,RuntimeException runtimeException){
        if(null != arrayObj && arrayObj.length > 0){
            throwException(runtimeException);
        }
    }

    public static void isEmptyMap(Map paramMap, RuntimeException runtimeException){
        isNull(paramMap,runtimeException);
        if(paramMap.isEmpty()){
            throwException(runtimeException);
        }
    }

    public static void notEmptyMap(Map paramMap, RuntimeException runtimeException){
        isNull(paramMap,runtimeException);
        if(!paramMap.isEmpty()){
            throwException(runtimeException);
        }
    }

    private static void throwException(RuntimeException runtimeException){
        throw runtimeException;
    }
}
