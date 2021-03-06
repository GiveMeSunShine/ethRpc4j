package org.chatch.ethrpc4j.databind.deserialize;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.chatch.ethrpc4j.test.Utils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.umpay.ethrpc4j.databind.EthRpc4jObjectMapper;
import com.umpay.ethrpc4j.types.Block;
import com.umpay.ethrpc4j.types.Transaction;

public class BlockDeserializerTest {

	final static EthRpc4jObjectMapper mapper = new EthRpc4jObjectMapper();

	@Test
	public void testBlock() throws JsonParseException, JsonMappingException, IOException {
		String json = Utils.jsonRspResult("block");
		System.out.println(json);

		final Block block = mapper.readValue(json, Block.class);
		assertThat(block, not(nullValue()));

		assertThat(block.getNumber(), equalTo(935709L));
		assertThat(block.getDifficulty(), equalTo(745828364L));
		assertThat(block.getTotalDifficulty(), equalTo("57706275288431L"));
		assertThat(block.getSize(), equalTo(886L));
		assertThat(block.getGasLimit(), equalTo(4713085L));
		assertThat(block.getGasUsed(), equalTo(63000L));
		assertThat(block.getTimestamp(), equalTo(1463153123L));

		assertThat(block.getHash(), equalTo("0xc8537f462845d63684ebf16eba167b9ce7d48f1bd8392a8ec4d29b97d39bf62d"));
		assertThat(block.getParentHash(),
				equalTo("0x3af12f4705297e10abc436afe703659186cdd1f550f33a36dcbc99c4a0799bd3"));
		assertThat(block.getNonce(), equalTo("0x9c744c11b9c6c576"));
		assertThat(block.getSha3Uncles(),
				equalTo("0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347"));
		assertThat(block.getLogsBloom(), equalTo(
				"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
		assertThat(block.getTransactionsRoot(),
				equalTo("0xdad34026eade29823ff79af9abfd5cbf0168f47d33a43c91014e2ff490e99fa8"));
		assertThat(block.getStateRoot(), equalTo("0x42a7bb86e42dfaeba5a1ce2402ded0fb76b838da5a0e799c6a00ad22f0582426"));
		assertThat(block.getReceiptRoot(),
				equalTo("0x2642c5b6853219edf77348c34f184b4ebabda73b0535f12ef572ecbd22ad4d69"));
		assertThat(block.getMiner(), equalTo("0xdf712c685be75739eb44cb6665f92129e45864e4"));
		assertThat(block.getExtraData(), equalTo("0xd783010500844765746887676f312e352e31856c696e7578"));

		final List<Transaction> txs = block.getTransactions();
		assertThat(txs, hasSize(3));

		final Transaction tx = txs.get(0);
		assertThat(tx.getBlockHash(), equalTo("0xc8537f462845d63684ebf16eba167b9ce7d48f1bd8392a8ec4d29b97d39bf62d"));
		assertThat(tx.getHash(), equalTo("0x7ff4daece69a7986f2932b2ee800135265152022f042ff13b767d7ea63a7e5d6"));
		assertThat(tx.getTo(), equalTo("0x87bd63264b9545391a2b129e856eb8aa79ca9e90"));
		assertThat(tx.getFrom(), equalTo("0xdf712c685be75739eb44cb6665f92129e45864e4"));
		assertThat(tx.getInput(), equalTo("0x"));

		assertThat(tx.getBlockNumber(), equalTo(935709L));
		assertThat(tx.getTransactionIndex(), equalTo(0L));
		assertThat(tx.getNonce(), equalTo(1054078L));
		assertThat(tx.getGas(), equalTo(90000L));
		assertThat(tx.getGasPrice(), equalTo(20000000000L));
		assertThat(tx.getValue(), equalTo("677362649999999800L"));

		final List<String> uncles = block.getUncles();
		assertThat(uncles, hasSize(2));
		assertThat(uncles.get(0), equalTo("0x0000000000"));
		assertThat(uncles.get(1), equalTo("0xffffffffff"));
	}

	@Test
	public void testBlockNoTxNoUncles() throws JsonParseException, JsonMappingException, IOException {
		String json = Utils.jsonRspResult("block_no_tx");
		System.out.println(json);

		final Block block = mapper.readValue(json, Block.class);
		assertThat(block, not(nullValue()));

		assertThat(block.getTransactions(), empty());
		assertThat(block.getUncles(), empty());
	}

}
