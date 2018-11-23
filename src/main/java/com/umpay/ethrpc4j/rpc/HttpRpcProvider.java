package com.umpay.ethrpc4j.rpc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.umpay.ethrpc4j.databind.EthRpc4jObjectMapper;

public class HttpRpcProvider implements RpcProvider {

	private static final Logger LOG = LoggerFactory.getLogger(HttpRpcProvider.class);

	private static final String DEFAULT_PATH = "http://10.10.67.217:8545";
	private static final URL DEFAULT_URL;
	static {
		try {
			DEFAULT_URL = new URL(DEFAULT_PATH);
		} catch (MalformedURLException e) {
			throw new IllegalStateException("default URL malformed: " + DEFAULT_PATH);
		}
	}

	private JsonRpcHttpClient client;

	public HttpRpcProvider() {
		this(DEFAULT_URL);
	}

	public HttpRpcProvider(URL serverURL) {
		this(serverURL, new EthRpc4jObjectMapper());
	}

	public HttpRpcProvider(URL serverURL, ObjectMapper mapper) {
		Map<String,String> hearders = new HashMap<String,String>();
		hearders.put("Content-Type", "application/json");
		this.client = new JsonRpcHttpClient(mapper, serverURL, hearders);
	}

	public HttpRpcProvider(JsonRpcHttpClient client) {
		this.client = client;
	}

	@Override
	public Object call(String methodName, Object[] params, Class<?> responseClass) {
		Object rsp = null;
		try {
			rsp = client.invoke(methodName, params, responseClass);
			
		} catch (Throwable e) {
			LOG.error(methodName + " failed", e);
		}
		return rsp;
	}

}
