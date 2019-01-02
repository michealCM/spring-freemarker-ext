package org.spring.freemarker.cache.pageCache;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.*;

/**
 * 重写{@link HttpServletResponseWrapper}实现从HttpServletResponse中读出相应的数据并持久化至缓存中，实现对页面的缓存功能。
 *
 * @date 2018-11-23 11:34:06
 */
public class CacheServletResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);

    private final Map<String, List<Serializable>> headersMap = new TreeMap<String, List<Serializable>>(String.CASE_INSENSITIVE_ORDER);

    private ServletOutputStream defaultServletOutputStream;

    private PrintWriter printWriter;

    private int statusCode = SC_OK;

    private int contentLength;

    private String contentType;

    public CacheServletResponseWrapper(HttpServletResponse response)throws IOException  {
        super(response);
        defaultServletOutputStream = new DefaultServletOutputStream();
        printWriter = new PrintWriter(new OutputStreamWriter(defaultServletOutputStream, "UTF-8"),true);
    }

    public byte[] getContent() {
        return byteArrayOutputStream.toByteArray();
    }

    public int getContentLength() {
        return contentLength;
    }

    public int getStatus() {
        return statusCode;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return defaultServletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

    @Override
    public void setStatus(final int code) {
        statusCode = code;
        super.setStatus(code);
    }

    @Override
    public void sendError(int i, String string) throws IOException {
        statusCode = i;
        super.sendError(i, string);
    }

    @Override
    public void sendError(int i) throws IOException {
        statusCode = i;
        super.sendError(i);
    }

    @Override
    public void sendRedirect(String string) throws IOException {
        statusCode = HttpServletResponse.SC_MOVED_TEMPORARILY;
        super.sendRedirect(string);
    }

    @Override
    public void setContentLength(final int length) {
        this.contentLength = length;
        super.setContentLength(length);
    }

    @Override
    public void setContentType(final String type) {
        this.contentType = type;
        super.setContentType(type);
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void addHeader(String name, String value) {
        List<Serializable> values = this.headersMap.get(name);
        if (values == null) {
            values = new LinkedList<Serializable>();
            this.headersMap.put(name, values);
        }
        values.add(value);

        super.addHeader(name, value);
    }

    @Override
    public void setHeader(String name, String value) {
        final LinkedList<Serializable> values = new LinkedList<Serializable>();
        values.add(value);
        this.headersMap.put(name, values);
        super.setHeader(name, value);
    }

    @Override
    public void addDateHeader(String name, long date) {
        List<Serializable> values = this.headersMap.get(name);
        if (values == null) {
            values = new LinkedList<Serializable>();
            this.headersMap.put(name, values);
        }
        values.add(date);
        super.addDateHeader(name, date);
    }

    @Override
    public void setDateHeader(String name, long date) {
        final LinkedList<Serializable> values = new LinkedList<Serializable>();
        values.add(date);
        this.headersMap.put(name, values);

        super.setDateHeader(name, date);
    }

    @Override
    public void addIntHeader(String name, int value) {
        List<Serializable> values = this.headersMap.get(name);
        if (values == null) {
            values = new LinkedList<Serializable>();
            this.headersMap.put(name, values);
        }
        values.add(value);
        super.addIntHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        final LinkedList<Serializable> values = new LinkedList<Serializable>();
        values.add(value);
        this.headersMap.put(name, values);
        super.setIntHeader(name, value);
    }

    @Override
    public void flushBuffer() throws IOException {
        super.flushBuffer();
        printWriter.flush();
    }

    @Override
    public void reset() {
        super.reset();
        byteArrayOutputStream.reset();
        headersMap.clear();
        statusCode = SC_OK;
        contentType = null;
        contentLength = 0;
    }

    public Collection<Header<? extends Serializable>> getAllHeaders() {
        final List<Header<? extends Serializable>> headers = new LinkedList<Header<? extends Serializable>>();
        for (final Map.Entry<String, List<Serializable>> headerEntry : this.headersMap.entrySet()) {
            final String name = headerEntry.getKey();
            for (final Serializable value : headerEntry.getValue()) {
                final Header.Type type = Header.Type.determineType(value.getClass());
                switch (type) {
                    case STRING:
                        headers.add(new Header<String>(name, (String) value));
                        break;
                    case DATE:
                        headers.add(new Header<Long>(name, (Long) value));
                        break;
                    case INT:
                        headers.add(new Header<Integer>(name, (Integer) value));
                        break;
                    default:
                        throw new IllegalArgumentException("No mapping for Header.Type: " + type);
                }
            }
        }
        return headers;
    }

    public boolean isOk() {
        return statusCode == HttpServletResponse.SC_OK;
    }

    /**
     * 自定义实现的ServletOutputStream,负责将response中的信息全部读取至byteArrayOutputStream中；
     */
    private class DefaultServletOutputStream extends ServletOutputStream{

        @Override
        public void setWriteListener(WriteListener writeListener) {
        }

        @Override
        public void write(int b) throws IOException {
            byteArrayOutputStream.write(b);
        }

        @Override
        public void write(byte b[]) throws IOException {
            byteArrayOutputStream.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            byteArrayOutputStream.write(b, off, len);
        }

        @Override
        public boolean isReady() {
            return false;
        }
    }
}
