package org.spring.freemarker.cache;

import org.spring.freemarker.common.utils.TimeLength;

/**
 * 定义的缓存持久化接口，提供缓存的相关操作。
 *
 * @date 2018-12-2 18:41:06
 */
public interface ICacheRepository{

    boolean setCache(CacheKey key, CacheElement value);

    boolean setCache(CacheKey key, CacheElement value, TimeLength expire);

    CacheElement loadCache(CacheKey key, TimeLength expire);

    boolean deleteCache(CacheKey key);

    boolean clear(CacheKey key);
}
