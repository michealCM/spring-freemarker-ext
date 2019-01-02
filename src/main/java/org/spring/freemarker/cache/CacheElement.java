package org.spring.freemarker.cache;

import java.io.Serializable;

/**
 * 自定义缓存元素，针对模板缓存或者页面缓存实现不同的缓存对象。
 *
 * @date 2018-12-12 12:20:57
 * @param <T>
 */
public interface CacheElement<T> extends Serializable {

    T getValue();

}
