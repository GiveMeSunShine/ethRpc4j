package com.umpay.ethrpc4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Util {
	public static String readString(File file){
		String str = null;
	    try {
	        FileInputStream in=new FileInputStream(file);
	        int size=in.available();
	        byte[] buffer=new byte[size];
	        in.read(buffer);
	        in.close();
	        str=new String(buffer,"GB2312");
	    } catch (IOException e) {
	        return null;
	    }
	    return str;
	}
	
	public static String ascii2Hex(String str){
		if(str == null || "".equals(str)){
			return "";
		}
		String result = "";
		for(int i = 0; i < str.length(); i++){
			char x = str.charAt(i);
			if(x < 0 || x > 127){
				return "";
			}
			result += Long.toHexString(x);
		}
		return result;
	}
	
	public static void main(String[] args) {
//		File file = new File("D:\\lab\\Auction.sol");
//		System.out.println(readString(file));
		System.out.println(ascii2Hex("transferFrom(address,address,uint)"));
		
		System.out.println(String.format("%0160d", "0xc60ec7c68d47814288bdfdaa71b88ff922d735a7"));
		// 67657442616c616e63652875696e7431363029
		//0x3324dfc7
		//0xc60ec7c68d47814288bdfdaa71b88ff922d735a7
		//00000000000000000000000
		//0x3324dfc7c60ec7c68d47814288bdfdaa71b88ff922d735a7
		
		
		
		
		
		
		//transferFrom
		//0x641ec35e  
	}
	
}
