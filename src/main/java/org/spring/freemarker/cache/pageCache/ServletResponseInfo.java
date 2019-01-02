package org.spring.freemarker.cache.pageCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 解析HttpServletResponse，对response的数据（header,contentType,statusCode和responseBody）进行存储；
 * 便于之后对该实体的数据进行缓存处理。
 * 具体参见{@link CacheServletResponseWrapper}和{@link AbstractCacheServletResponseFilter}。
 *
 * @date 2018-11-23 14:13:01
 */
public class ServletResponseInfo implements Serializable {

    private static final long serialVersionUID = 5117345581320775665L;

    private final ArrayList<Header<? extends Serializable>> responseHeaders = new ArrayList<Header<? extends Serializable>>(10);
    private String contentType;
    private byte[] responseBody;
    private int statusCode;

    public ServletResponseInfo(int statusCode, String contentType,
                               byte[] responseBody,
                               Collection<Header<? extends Serializable>> headers) {
        if (headers != null) {
            this.responseHeaders.addAll(headers);
        }
        this.contentType = contentType;
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public final String getContentType() {
        return contentType;
    }

    public List<Header<? extends Serializable>> getHeaders() {
        return this.responseHeaders;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public final byte[] getResponseBody() {
        return responseBody;
    }
}
