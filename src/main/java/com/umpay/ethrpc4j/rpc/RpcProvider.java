package com.umpay.ethrpc4j.rpc;

public interface RpcProvider {

	public Object call(String methodName, Object[] params, Class<?> responseClass) throws Exception;

}
