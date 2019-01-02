package org.spring.freemarker.cache;

/**
 * 定义框架支持的缓存类型，
 *
 * @date 2018-12-4 14:25:41
 */
public enum CacheType {

    CACHE_TYPE_EHCACHE("ehcache"),
    CACHE_TYPE_REDIS("redis");

    private String value;

    private CacheType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
