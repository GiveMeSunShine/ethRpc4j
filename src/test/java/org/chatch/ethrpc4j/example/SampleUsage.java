package org.chatch.ethrpc4j.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.ethrpc4j.EthereumClient;
import com.umpay.ethrpc4j.methods.EthMethods;
import com.umpay.ethrpc4j.methods.PersonalMethods;
import com.umpay.ethrpc4j.types.Block;

public class SampleUsage {
	private static final Logger LOG = LoggerFactory.getLogger(SampleUsage.class);

	public static void main(String[] args) throws Exception {
		final EthereumClient client = EthereumClient.getDefaultInstance();
		final EthMethods eth = client.eth();
		final PersonalMethods personal = client.personal();
		
		String myAccount = "";

		LOG.info("\nClient:\n");

		LOG.info("clientVersion: " + client.web3().clientVersion());
		LOG.info("protocolVersion: " + eth.protocolVersion());
		LOG.info("accounts: ");
		List<String> accounts = eth.accounts();
		for (String hash : accounts) {
			LOG.info("\t" + hash);
		}
		if(accounts.size() > 0){
			myAccount = accounts.get(0);
		}
		
		LOG.info("mining: " + eth.mining());
		LOG.info("syncing: " + eth.syncing());

		LOG.info("\nNetwork:\n");
		
		LOG.info("blockNumber: " + eth.blockNumber());
		LOG.info("gasPrice: " + eth.gasPrice());
		LOG.info("hashrate: " + eth.hashrate());

		LOG.info("\nCompilers:\n");

		List<String> compilers = eth.getCompilers();
		for (String compiler : compilers) {
			LOG.info("" + compiler);
		}

		LOG.info("\nCurrent Block:\n");
		Block block = eth.getBlockByNumber(eth.blockNumber().toString(), false);
		LOG.info("" + block);
		
		
		LOG.info("\nContract\n");
		String contract = "contract test { function multiply(uint a) returns(uint d) { return a * 7; } }";
		String contractName = "test";
		Map<String,Object> map = eth.compileSolidity(contract);
		String code = ((Map<String,Object>)map.get(contractName)).get("code").toString();
		Map<String,Object> info = (Map<String,Object>)((Map<String,Object>)map.get(contractName)).get("info");
		String abiDefinition = info.get("abiDefinition").toString();
		LOG.info("\nCode:" + code);
		LOG.info("\nInfo:" + info);
		LOG.info("\nabiDefinition:\n" + abiDefinition);
		
////		LOG.info("\nunlockAccount\n");
////		personal.unlockAccount(myAccount, "", "3600");
		
		LOG.info("\nsendTransaction\n");
		Map<String,String> parma = new HashMap<String,String>();
		parma.put("from", myAccount);
		parma.put("gas","0x" + Long.toHexString(200000L));
		parma.put("gasPrice", "0x" +Long.toHexString(300));
		parma.put("data", code);
		String thash = eth.sendTransaction(parma);
		LOG.info("#TransactionHash:"+thash);
		int times = 0;
		long preBlockNumber = eth.blockNumber();
		while(times < 60){
			long bn = eth.blockNumber();
			if(bn > preBlockNumber){
				break;
			}else{
				Thread.sleep(1000);
			}
			times++;
		}
		Thread.sleep(10000);
		LOG.info("\ngetTransactionReceipt\n");
		LOG.info(eth.getTransactionReceipt(thash).toString());
		
		

	}

}
