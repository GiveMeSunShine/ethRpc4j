package com.umpay.ethrpc4j;

import com.umpay.ethrpc4j.methods.EthMethods;
import com.umpay.ethrpc4j.methods.Web3Methods;
import com.umpay.ethrpc4j.util.Util;

public class CallContract {
	private static String address = "";
	private static String initabi = "setString(uint256,string)";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		final EthereumClient client = EthereumClient.getDefaultInstance();
		final EthMethods eth = client.eth();
		final Web3Methods web = client.web3();
		String ascii = Util.ascii2Hex(initabi);
		String sha3 = web.sha3(ascii);
		if(sha3==null || sha3.length() < 10){
			return;
		}
		String methodId = sha3.substring(0, 10);
		System.out.println("methodId:" + methodId);
		System.out.println(web.sha3(ascii));
	}

}
