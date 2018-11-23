package com.umpay.ethrpc4j.databind;

import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.databind.JsonNode;

public class Converters {

	
	public static String string(JsonNode node, String fieldName) {
		String str = node.get(fieldName).asText();
		if (str != null && str.equals("null")) // null is "null" in the json
			str = null;
		return str;
	}

	

	public static Long quantity(JsonNode node, String fieldName) {
		return hexToQuantity(string(node, fieldName));
	}

	public static Long hexToQuantity(String hexNumber) {
		if (hexNumber == null || hexNumber.equals("null"))
			return null;
		if (hexNumber.startsWith("0x"))
			hexNumber = hexNumber.substring(2);
		return Long.valueOf(hexNumber, 16);
	}

	public static String quantityToHex(Long hexNumber) {
		return "0x" + Long.toHexString(hexNumber);
	}

	

	public static byte[] data(JsonNode node, String fieldName) {
		return stringToBytes(string(node, fieldName));
	}

	public static byte[] stringToBytes(String dataStr) {
		return DatatypeConverter.parseHexBinary(dataStr);
	}

}
