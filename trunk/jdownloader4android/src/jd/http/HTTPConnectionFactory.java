package jd.http;

import java.net.URL;

public class HTTPConnectionFactory {

    public static URLConnectionAdapter createHTTPConnection(final URL url) {
        return new URLConnectionAdapterDirectImpl(url);        
    }
}
