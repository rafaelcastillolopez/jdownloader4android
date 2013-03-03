package org.jdownloader;

import java.util.HashMap;

import jd.plugins.PluginForHost;
import jd.utils.JDUtilities;

public class DomainInfo implements Comparable<DomainInfo> {
    private static final long CACHE_TIMEOUT = 30000;
    private static final int  WIDTH         = 16;
    private static final int  HEIGHT        = 16;

    private DomainInfo(String tld) {
        this.tld = tld;
    }

    public String toString() {
        return tld;
    }

    private String tld;

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    private static HashMap<String, DomainInfo> CACHE = new HashMap<String, DomainInfo>();

    public static DomainInfo getInstance(String host) {
        if (host == null) return null;
        // WARNING: can be a memleak
        synchronized (CACHE) {
            DomainInfo ret = CACHE.get(host);
            if (ret == null) {
                CACHE.put(host, ret = new DomainInfo(host));
            }
            return ret;
        }
    }    

    public PluginForHost findPlugin() {
        return JDUtilities.getPluginForHost(getTld());
    }

    public int compareTo(DomainInfo o) {
        return getTld().compareTo(o.getTld());
    }
}
