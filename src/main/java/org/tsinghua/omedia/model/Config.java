package org.tsinghua.omedia.model;

/**
 * 
 * @author xuhongfeng
 *
 */
public class Config {
    private String ccnUrl;
    private String ccnHost;

    public String getCcnUrl() {
        return ccnUrl;
    }

    public void setCcnUrl(String ccnUrl) {
        this.ccnUrl = ccnUrl;
    }

    public String getCcnHost() {
        return ccnHost;
    }

    public void setCcnHost(String ccnHost) {
        this.ccnHost = ccnHost;
    }

    @Override
    public String toString() {
        return "Config [ccnUrl=" + ccnUrl + ", ccnHost=" + ccnHost + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ccnHost == null) ? 0 : ccnHost.hashCode());
        result = prime * result + ((ccnUrl == null) ? 0 : ccnUrl.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Config other = (Config) obj;
        if (ccnHost == null) {
            if (other.ccnHost != null)
                return false;
        } else if (!ccnHost.equals(other.ccnHost))
            return false;
        if (ccnUrl == null) {
            if (other.ccnUrl != null)
                return false;
        } else if (!ccnUrl.equals(other.ccnUrl))
            return false;
        return true;
    }

}
