package org.rb.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import com.restfb.DefaultWebRequestor;

public class ProxyWebRequestor extends DefaultWebRequestor {
	
	protected HttpURLConnection openConnection(URL url) throws IOException {
		if (System.getProperty("http.proxySet").equals("true")) {
			String host = System.getProperty("http.proxyHost");
			Integer port = Integer.parseInt(System.getProperty("http.proxyPort"));
			InetSocketAddress proxyLocation = new InetSocketAddress(host, port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyLocation);
			return (HttpURLConnection) url.openConnection(proxy);
		} else {
			return (HttpURLConnection) url.openConnection();
		}
	}
	
}