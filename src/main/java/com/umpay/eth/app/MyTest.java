package com.umpay.eth.app;

import java.math.BigDecimal;
import java.net.URL;

import com.umpay.ethrpc4j.EthereumClient;
import com.umpay.ethrpc4j.methods.EthMethods;
import com.umpay.ethrpc4j.methods.Web3Methods;

public class MyTest {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://10.10.144.11:10070");
		EthereumClient client = EthereumClient.getInstanceByURL(url);
		EthMethods eth = client.eth();
		Web3Methods web = client.web3();
		//System.out.println(web.sha3("0"));
		long bn = 500000L;
		long end = 0;
		long count = 0;
		for(long i = 0L; i < bn; i++ ){
			//System.out.println(Long.toHexString(i));
			long start = System.currentTimeMillis();
			
			if(start - end >= 1000){
				System.out.println("TPS:" + (i-count));
				count = i;
				end = System.currentTimeMillis();
			}
			eth.getBlockByNumber("0x" + Long.toHexString(i), true);
			//System.out.println(eth.getBlockByNumber("0x" + Long.toHexString(i), true));
		}
		
	}
}
