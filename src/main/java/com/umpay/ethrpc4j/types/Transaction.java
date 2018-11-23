package com.umpay.ethrpc4j.types;

public class Transaction {

	private String hash;
	private Long nonce;
	private String blockHash;
	private Long blockNumber;
	private Long transactionIndex;

	private String from;
	private String to;

	private Long gas;
	private Long gasPrice;
	private String value;
	private String input;
	private String v;
	private String r;
	private String s;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getNonce() {
		return nonce;
	}

	public void setNonce(Long nonce) {
		this.nonce = nonce;
	}

	public String getBlockHash() {
		return blockHash;
	}

	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}

	public Long getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(Long blockNumber) {
		this.blockNumber = blockNumber;
	}

	public Long getTransactionIndex() {
		return transactionIndex;
	}

	public void setTransactionIndex(Long transactionIndex) {
		this.transactionIndex = transactionIndex;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Long getGas() {
		return gas;
	}

	public void setGas(Long gas) {
		this.gas = gas;
	}

	public Long getGasPrice() {
		return gasPrice;
	}

	public void setGasPrice(Long gasPrice) {
		this.gasPrice = gasPrice;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return "Transaction [hash=" + hash + ", nonce=" + nonce + ", blockHash=" + blockHash + ", blockNumber="
				+ blockNumber + ", transactionIndex=" + transactionIndex + ", from=" + from + ", to=" + to + ", gas="
				+ gas + ", gasPrice=" + gasPrice + ", value=" + value + ", input=" + input + "]";
	}

}
