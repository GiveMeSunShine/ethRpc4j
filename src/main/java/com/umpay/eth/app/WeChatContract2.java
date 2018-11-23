package com.umpay.eth.app;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class WeChatContract2 {

	final EthereumClient client = EthereumClient.getDefaultInstance();
	final EthMethods eth = client.eth();
	final Web3Methods web = client.web3();
	//public static final String ADDRESS = "0x4b9336a5f509684f937c61a673af3fcb091efe47";
	public static final String ADDRESS = "0xdbb8d6ed1b936cf6801050b2d70089fd7fe3d82d";
	public static final String ACCOUNT = "0xc60ec7c68d47814288bdfdaa71b88ff922d735a7";

	private final static String LENGTH_METHOD="getStringLength(uint256)";
	private final static String REGISTER_METHOD="register(uint256)";
	private final static String TRANSFER_METHOD="transfer(uint256,uint256,uint256,string)";
	private final static String DETAIL_METHOD="getDetail(uint256,uint256)";
	private final static String GETBLANCE_METHOD="getBlance(uint256)";
	
	private final static Logger _log = LoggerFactory.getLogger(WeChatContract.class);
	
	public List<String> queryDetail(String mobileId) throws Exception{
		_log.info("#Query Detail# param , mobileId={},address={},account={}",mobileId,ADDRESS,ACCOUNT);
		String queryLengthAscii = Util.ascii2Hex(LENGTH_METHOD);
		String queryLengthSha3 = web.sha3(queryLengthAscii);
		if(queryLengthSha3==null || queryLengthSha3.length() < 10){
			return null;
		}
		String queryLengthMethodId = queryLengthSha3.substring(0, 10);
		
		String ascii = Util.ascii2Hex(DETAIL_METHOD);
		String sha3  = web.sha3(ascii);
		if (sha3 == null || sha3.length() < 10) {
			return null;
		}
		String methodId = sha3.substring(0, 10);
		//methodId = "0xc08adb01";
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
		_log.info("#Query Detail# , size={}",size);
		//Query Message
		List<String> list = new ArrayList<String>(); 
		for(long m = 0; m < size; m++){
			String data = methodId + String.format("%064X", Long.valueOf(mobileId)) + String.format("%064X", Long.valueOf(m));
			req.put("data", data);
			
			list.add(trim(new String(ToolBox.hex2b(eth.call(req).substring(130)), Charset.forName("UTF-8"))));
		}
		_log.info("#Query Detail# , result={}",list);
		return list;
	}
	
	/**
	 * @param mobileId
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public String register(String mobileId) throws Exception {
		_log.info("#register# param , mobileId={},,address={},account={}",mobileId,ADDRESS,ACCOUNT);
		if(!ToolBox.checkMobileNo(mobileId)){
			throw new IllegalArgumentException("mobileId is not Illegal");
		}

		String ascii = Util.ascii2Hex(REGISTER_METHOD);
		String sha3  = web.sha3(ascii);
		if (sha3 == null || sha3.length() < 10) {
			return null;
		}
		String methodId = sha3.substring(0, 10);
		String data = methodId + String.format("%064X", Long.valueOf(mobileId));
		Map<String,String> req = new HashMap<String, String>();
		req.put("to", ADDRESS);
		req.put("data", data);
		req.put("from", ACCOUNT);
		req.put("gas", "0x" + Long.toHexString(200000));
		String result = eth.sendTransaction(req);
		_log.info("#register Msg# , result={}",result);
		return result;
	}
	
	public int getBlance(String mobileId) throws Exception {
		_log.info("#getBlance# param , mobileId={},,address={},account={}",mobileId,ADDRESS,ACCOUNT);
		if(!ToolBox.checkMobileNo(mobileId)){
			throw new IllegalArgumentException("mobileId is not Illegal");
		}

		String ascii = Util.ascii2Hex(GETBLANCE_METHOD);
		String sha3  = web.sha3(ascii);
		if (sha3 == null || sha3.length() < 10) {
			return 0;
		}
		String methodId = sha3.substring(0, 10);
		String data = methodId + String.format("%064X", Long.valueOf(mobileId));
		Map<String,String> req = new HashMap<String, String>();
		req.put("to", ADDRESS);
		req.put("data", data);
		req.put("from", ACCOUNT);
		String result = eth.call(req);
		_log.info("#register Msg# , result={}",result);
		if(result.length() < 60){
			return 0;
		}
		return Long.valueOf(result.substring(60), 16).intValue();
	}
	
	public String transfer(String srcMobileId,String targetMobileId,int amount,long time,String extra) throws Exception{
		_log.info("#transfer# param , srcMobileId={},targetMobileId={},address={},account={}",srcMobileId,targetMobileId,ADDRESS,ACCOUNT);
		if(!ToolBox.checkMobileNo(srcMobileId) || !ToolBox.checkMobileNo(targetMobileId)){
			throw new IllegalArgumentException("mobileId is not Illegal");
		}

		String ascii = Util.ascii2Hex(TRANSFER_METHOD);
		String sha3  = web.sha3(ascii);
		if (sha3 == null || sha3.length() < 10) {
			return null;
		}
		String msg = String.valueOf(amount) + ":" + String.valueOf(time) + ":" + extra;
		byte[] bytes = msg.getBytes(Charset.forName("UTF-8"));
		int length = (bytes.length/32 + 1) * 32;
		byte[] message = new byte[length];
		System.arraycopy(bytes, 0, message, 0, bytes.length);
		String methodId = sha3.substring(0, 10);
		String data = methodId
				+ String.format("%064X", Long.valueOf(srcMobileId))
				+ String.format("%064X", Long.valueOf(targetMobileId))
				+ String.format("%064X", Long.valueOf(amount))
				+ String.format("%064X", Long.valueOf(128))
				+ String.format("%064X", bytes.length) + ToolBox.b2HEX(message); 
		Map<String,String> req = new HashMap<String, String>();
		req.put("to", ADDRESS);
		req.put("data", data);
		req.put("from", ACCOUNT);
		req.put("gas", "0x" + Long.toHexString(300000));
		String result = eth.sendTransaction(req);
		_log.info("#transfer Msg# , result={}",result);
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
		long time = System.currentTimeMillis();
		Date d = new Date(time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String extra = "测试";
		 WeChatContract2 wcc = new WeChatContract2();
//		 String s = "0000000000000000000000000000000000000520";
//		 System.out.println(Integer.valueOf(s));
		// wcc.register("15810808257");
		// wcc.register("15810808254");
		// wcc.transfer("15810808254", "15810808257", 1,time,extra);
		// System.out.println("###### Blance:" + wcc.getBlance("15810808258"));
		 System.out.println("###### Blance:" + wcc.getBlance("15810808254"));
		 System.out.println(wcc.queryDetail("15810808254"));
//		 System.out.println(df.format(d).toString());
	}

}
