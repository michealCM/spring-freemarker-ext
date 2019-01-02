package org.spring.freemarker.web.setting;

import java.io.Serializable;

/**
 *
 * @date 2018-12-25 11:15:48
 */
public class CDNSetting implements Serializable {

    private static final long serialVersionUID = -2567413372854698241L;

    private String version;

    private String CDNServer;

    private String defaultImage;

    private String loadingImage;

    private String loadingErrorImage;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCDNServer() {
        return CDNServer;
    }

    public void setCDNServer(String CDNServer) {
        this.CDNServer = CDNServer;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getLoadingImage() {
        return loadingImage;
    }

    public void setLoadingImage(String loadingImage) {
        this.loadingImage = loadingImage;
    }

    public String getLoadingErrorImage() {
        return loadingErrorImage;
    }

    public void setLoadingErrorImage(String loadingErrorImage) {
        this.loadingErrorImage = loadingErrorImage;
    }
}
