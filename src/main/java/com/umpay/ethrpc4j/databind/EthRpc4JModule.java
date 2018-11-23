package com.umpay.ethrpc4j.databind;


import com.fasterxml.jackson.databind.module.SimpleModule;
import com.umpay.ethrpc4j.databind.deserialize.BlockDeserializer;
import com.umpay.ethrpc4j.databind.deserialize.LogDeserializer;
import com.umpay.ethrpc4j.databind.deserialize.SyncingDeserializer;
import com.umpay.ethrpc4j.databind.deserialize.TransactionDeserializer;
import com.umpay.ethrpc4j.types.Block;
import com.umpay.ethrpc4j.types.Log;
import com.umpay.ethrpc4j.types.Syncing;
import com.umpay.ethrpc4j.types.Transaction;

public class EthRpc4JModule extends SimpleModule {

	private static final long serialVersionUID = -4840495639824086987L;

	public EthRpc4JModule() {
		addDeserializer(Block.class, new BlockDeserializer());
		addDeserializer(Transaction.class, new TransactionDeserializer());
		addDeserializer(Syncing.class, new SyncingDeserializer());
		addDeserializer(Log.class, new LogDeserializer());
	}

	@Override
	public String getModuleName() {
		return getClass().getSimpleName();
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}
}
