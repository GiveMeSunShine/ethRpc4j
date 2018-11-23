package com.umpay.eth.app;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.ethrpc4j.EthereumClient;
import com.umpay.ethrpc4j.methods.EthMethods;
import com.umpay.ethrpc4j.methods.Web3Methods;
import com.umpay.ethrpc4j.util.Util;
import com.umpay.util.ToolBox;

/**
 * @author chenzheng
 * ADDRESS:0x65be3f739ca4eabc47a59576695c66cb69b7987b
 *
 */
public class WeChatContract {
	static URL url = null;
	static{
		try {
			 url = new URL("http://10.10.144.11:10070");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	final EthereumClient client = EthereumClient.getInstanceByURL(url);
	final EthMethods eth = client.eth();
	final Web3Methods web = client.web3();
	public static final String ADDRESS = "0x699454b2603eb84670764a23b8d441912defa365";
	public static final String ACCOUNT = "0xc60ec7c68d47814288bdfdaa71b88ff922d735a7";
	private final static String ADD_METHOD="setString(uint256,string)";
	private final static String GET_METHOD="getString(uint256,uint256)";
	private final static String LENGTH_METHOD="getStringLength(uint256)";
	private final static Logger _log = LoggerFactory.getLogger(WeChatContract.class);
	
	public List<String> queryMsg(String mobileId) throws Exception {
		_log.info("#Query Msg# param , mobileId={},address={},account={}",mobileId,ADDRESS,ACCOUNT);
		//queryLength
		String queryLengthAscii = Util.ascii2Hex(LENGTH_METHOD);
		String queryLengthSha3 = web.sha3(queryLengthAscii);
		if(queryLengthSha3==null || queryLengthSha3.length() < 10){
			return null;
		}
		String queryLengthMethodId = queryLengthSha3.substring(0, 10);
		
		String ascii = Util.ascii2Hex(GET_METHOD);
		String sha3  = web.sha3(ascii);
		if (sha3 == null || sha3.length() < 10) {
			return null;
		}
		String methodId = sha3.substring(0, 10);
		String queryLengthData = queryLengthMethodId + String.format("%064X", Long.valueOf(mobileId));
		
		Map<String,String> req = new HashMap<String, String>();
		req.put("to", ADDRESS);
		req.put("data", queryLengthData);
		req.put("from", ACCOUNT);
		String result = eth.call(req).substring(2);
		System.out.println("message Array size: " + result);
		long[] longArr = new long[4];
		for(int i = 0;i < 4;i++){
			longArr[i] = Long.valueOf(result.substring(i*16,i*16+16),16);
		}
		long size = longArr[longArr.length - 1];
		_log.info("#Query Msg# , size={}",size);
		//Query Message
		List<String> list = new ArrayList<String>(); 
		for(long m = 0; m < size; m++){
			String data = methodId + String.format("%064X", Long.valueOf(mobileId)) + String.format("%064X", Long.valueOf(m));
			req.put("data", data);
			
			list.add(trim(new String(ToolBox.hex2b(eth.call(req).substring(130)), Charset.forName("UTF-8"))));
		}
		_log.info("#Query Msg# , result={}",list);
		return list;
	}
	
	/**
	 * @param mobileId
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public String addMsg(String mobileId,String msg) throws Exception {
		_log.info("#Add Msg# param , mobileId={},msg={},address={},account={}",mobileId,msg,ADDRESS,ACCOUNT);
		if(!ToolBox.checkMobileNo(mobileId)){
			throw new IllegalArgumentException("mobileId is not Illegal");
		}
		if(msg == null){
			throw new IllegalArgumentException("msg can not null");
		}
		String ascii = Util.ascii2Hex(ADD_METHOD);
		String sha3  = web.sha3(ascii);
		if (sha3 == null || sha3.length() < 10) {
			return null;
		}
		byte[] bytes = msg.getBytes(Charset.forName("UTF-8"));
		int length = (bytes.length/32 + 1) * 32;
		byte[] message = new byte[length];
		System.arraycopy(bytes, 0, message, 0, bytes.length);
		String methodId = sha3.substring(0, 10);
		String data = methodId + String.format("%064X", Long.valueOf(mobileId))
				+ String.format("%064X", Long.valueOf(64))
				+ String.format("%064X", bytes.length) + ToolBox.b2HEX(message);
		Map<String,String> req = new HashMap<String, String>();
		req.put("to", ADDRESS);
		req.put("data", data);
		req.put("from", ACCOUNT);
		String result = eth.sendTransaction(req);
		_log.info("#Query Msg# , result={}",result);
		return result;
	}
	
	
	
	public static String trim(String string){
		if(string == null){
			return "";
		}
		return string.trim();
	}
	
	
   public static void main(String[] args) throws Exception {
	   String mobileId = "15810808258";
	   WeChatContract wcc = new WeChatContract();
	   wcc.queryMsg("15810808258");
	   //wcc.addMsg(mobileId, "hehe");
	   //wcc.queryMsg("15810808258");
	   String s = "00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000005";
	   System.out.println();
   }

}
