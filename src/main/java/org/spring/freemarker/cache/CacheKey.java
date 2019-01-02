package org.spring.freemarker.cache;

import java.io.Serializable;

/**
 * 自定义的缓存key；实现对不同的缓存数据进行分组存储。
 *
 *  属性cacheName在ehcache中对应不同的缓存模板；在redis则作为不同缓存模板的统一缓存前缀。
 *
 * @date 2018-12-12 14:58:29
 */
public class CacheKey implements Serializable {

    private static final long serialVersionUID = 84104516428376693L;

    private String key;

    private String cacheName;

    public CacheKey(String cacheName){
        this.cacheName = cacheName;
    }

    public CacheKey(String key, String cacheName){
        this.key = key;
        this.cacheName = cacheName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
