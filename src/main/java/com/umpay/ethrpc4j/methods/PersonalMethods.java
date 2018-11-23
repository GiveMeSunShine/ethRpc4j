package com.umpay.ethrpc4j.methods;

import com.umpay.ethrpc4j.rpc.RpcProvider;

public class PersonalMethods extends MethodsBase {
	private static final String METHOD_PREFIX = "personal";

	public PersonalMethods(RpcProvider provider) {
		super(METHOD_PREFIX, provider);
	}

	public String newAccount(String seed) {
		return null;
	}

	public String unlockAccount(String addr, String pass, String duration) throws Exception {
		return callString("unlockAccount",  new Object[] { addr,pass,duration });
	}

	public String[] lockAccount(String addr) {
		return null;
	}

}
