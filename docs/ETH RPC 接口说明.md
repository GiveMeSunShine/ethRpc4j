# ETH RPC 接口说明

## eth_accounts：

查询客户端持有的地址列表 

#### eg:

请求：`$ curl -X POST --data '{"jsonrpc":"2.0","method":"eth_accounts","params":[],"id":1}' `

请求结果：

```json
{
  "id":1,
  "jsonrpc": "2.0",
  "result": ["0x407d73d8a49eeb85d32cf465507dd71d507100c1"]
}
```

## eth_getBalance：

获取账户余额

#### 参数

- `DATA` - 20字节，要检查余额的地址
- `QUANTITY|TAG` - 整数块编号，或者字符串"latest", "earliest" 或 "pending"

```josn
params: [
   '0x407d73d8a49eeb85d32cf465507dd71d507100c1',
   'latest'
]
```

#### 返回值

`QUANTITY` - 当前余额，单位：wei

#### eg:

请求：

```json
$ curl -X POST --data '{"jsonrpc":"2.0","method":"eth_getBalance","params":["0x407d73d8a49eeb85d32cf465507dd71d507100c1", "latest"],"id":1}'
```

响应：

```json
{
  "id":1,
  "jsonrpc": "2.0",
  "result": "0x0234c8a3397aab58" // 158972490234375000
}
```

## eth_getTransactionCount :

返回指定地址发生的交易数量。

#### 参数

- `DATA` - 20字节，地址
- `QUANTITY|TAG` - 整数块编号，或字符串"latest"、"earliest"或"pending"

```json
params: [
   '0x407d73d8a49eeb85d32cf465507dd71d507100c1',
   'latest' // state at the latest block
]
```

#### 返回值

`QUANTITY` - 从指定地址发出的交易数量，整数

#### eg:

请求：

```bash
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_getTransactionCount","params":["0x407d73d8a49eeb85d32cf465507dd71d507100c1","latest"],"id":1}'
```

响应：

```json
{
  "id":1,
  "jsonrpc": "2.0",
  "result": "0x1" // 1
}
```

## eth_getBlockTransactionCountByHash: 

使用哈希查询块内的交易数量

#### 参数

`DATA` - 32字节，块哈希

```json
params: [
   '0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238'
]
```

#### 返回值

`QUANTITY` - 指定块内的交易数量，整数

#### eg

请求：

```bash
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_getBlockTransactionCountByHash","params":["0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238"],"id":1}'
```

响应：

```json
{
  "id":1,
  "jsonrpc": "2.0",
  "result": "0xb" // 11
}
```

## eth_getBlockTransactionCountByNumber: 

根据块号获取块中交易数量

#### 参数

`QUANTITY|TAG` - 整数块编号，或字符串"earliest"、"latest"或"pending"

```json
params: [
   '0xe8', // 232
]
```

#### 返回值

`QUANTITY` - 指定块内的交易数量

#### eg

请求：

```bash
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_getBlockTransactionCountByNumber","params":["0xe8"],"id":1}'
```

响应：

```josn
{
  "id":1,
  "jsonrpc": "2.0",
  "result": "0xa" // 10
}
```

## eth_getBlockByHash：

根据块hash获取块信息

#### 参数

- `DATA`, 32字节 - 块哈希
- `Boolean` - 为true时返回完整的交易对象，否则仅返回交易哈希

```json
params: [
   '0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331',
   true
]
```

#### 返回值

`Object` - 匹配的块对象，如果未找到块则返回null，结构如下：

- number: QUANTITY - 块编号，挂起块为null
- hash: DATA, 32 Bytes - 块哈希，挂起块为null
- parentHash: DATA, 32 Bytes - 父块的哈希
- nonce: DATA, 8 Bytes - 生成的pow哈希，挂起块为null
- sha3Uncles: DATA, 32 Bytes - 块中叔伯数据的SHA3哈希
- logsBloom: DATA, 256 Bytes - 快日志的bloom过滤器，挂起块为null
- transactionsRoot: DATA, 32 Bytes - 块中的交易树根节点
- stateRoot: DATA, 32 Bytes - 块最终状态树的根节点
- receiptsRoot: DATA, 32 Bytes - 块交易收据树的根节点
- miner: DATA, 20 Bytes - 挖矿奖励的接收账户
- difficulty: QUANTITY - 块难度，整数
- totalDifficulty: QUANTITY - 截止到本块的链上总难度
- extraData: DATA - 块额外数据
- size: QUANTITY - 本块字节数
- gasLimit: QUANTITY - 本块允许的最大gas用量
- gasUsed: QUANTITY - 本块中所有交易使用的总gas用量
- timestamp: QUANTITY - 块时间戳
- transactions: Array - 交易对象数组，或32字节长的交易哈希数组
- uncles: Array - 叔伯哈希数组

#### eg

请求：

```bash
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_getBlockByHash","params":["0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331", true],"id":1}'
```

响应：

```json
{
"id":1,
"jsonrpc":"2.0",
"result": {
    "number": "0x1b4", // 436
    "hash": "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
    "parentHash": "0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5",
    "nonce": "0xe04d296d2460cfb8472af2c5fd05b5a214109c25688d3704aed5484f9a7792f2",
    "sha3Uncles": "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
    "logsBloom": "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
    "transactionsRoot": "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
    "stateRoot": "0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff",
    "miner": "0x4e65fda2159562a496f9f3522f89122a3088497a",
    "difficulty": "0x027f07", // 163591
    "totalDifficulty":  "0x027f07", // 163591
    "extraData": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "size":  "0x027f07", // 163591
    "gasLimit": "0x9f759", // 653145
    "gasUsed": "0x9f759", // 653145
    "timestamp": "0x54e34e8e" // 1424182926
    "transactions": [{...},{ ... }] 
    "uncles": ["0x1606e5...", "0xd5145a9..."]
  }
}
```

## eth_getBlockByNumber：

根据块号查询块信息

#### 参数

- `QUANTITY|TAG` - 整数块编号，或字符串"earliest"、"latest" 或"pending"
- `Boolean` - 为true时返回完整的交易对象，否则仅返回交易哈希

```json
params: [
   '0x1b4', // 436
   true
]
```

#### 返回值

参考`eth_getBlockByHash`的返回值。

#### eg

请求：

```bash
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_getBlockByNumber","params":["0x1b4", true],"id":1}'
```

响应：

```json
{
"id":1,
"jsonrpc":"2.0",
"result": {
    "number": "0x1b4", // 436
    "hash": "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
    "parentHash": "0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5",
    "nonce": "0xe04d296d2460cfb8472af2c5fd05b5a214109c25688d3704aed5484f9a7792f2",
    "sha3Uncles": "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
    "logsBloom": "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
    "transactionsRoot": "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
    "stateRoot": "0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff",
    "miner": "0x4e65fda2159562a496f9f3522f89122a3088497a",
    "difficulty": "0x027f07", // 163591
    "totalDifficulty":  "0x027f07", // 163591
    "extraData": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "size":  "0x027f07", // 163591
    "gasLimit": "0x9f759", // 653145
    "gasUsed": "0x9f759", // 653145
    "timestamp": "0x54e34e8e" // 1424182926
    "transactions": [{...},{ ... }] 
    "uncles": ["0x1606e5...", "0xd5145a9..."]
  }
}
```

# eth_getTransactionByHash

返回指定哈希对应的交易。

#### 参数

`DATA`, 32 字节 - 交易哈希

```json
params: [
   "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238"
]
```

#### 返回值

`Object` - 交易对象，如果没有找到匹配的交易则返回null。结构如下：

- hash: DATA, 32字节 - 交易哈希
- nonce: QUANTITY - 本次交易之前发送方已经生成的交易数量
- blockHash: DATA, 32字节 - 交易所在块的哈希，对于挂起块，该值为null
- blockNumber: QUANTITY - 交易所在块的编号，对于挂起块，该值为null
- transactionIndex: QUANTITY - 交易在块中的索引位置，挂起块该值为null
- from: DATA, 20字节 - 交易发送方地址
- to: DATA, 20字节 - 交易接收方地址，对于合约创建交易，该值为null
- value: QUANTITY - 发送的以太数量，单位：wei
- gasPrice: QUANTITY - 发送方提供的gas价格，单位：wei
- gas: QUANTITY - 发送方提供的gas可用量
- input: DATA - 随交易发送的数据

#### eg

请求：

```bash
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_getTransactionByHash","params":["0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238"],"id":1}'
```

响应：

```json
{
"id":1,
"jsonrpc":"2.0",
"result": {
    "hash":"0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b",
    "nonce":"0x",
    "blockHash": "0xbeab0aa2411b7ab17f30a99d3cb9c6ef2fc5426d6ad6fd9e2a26a6aed1d1055b",
    "blockNumber": "0x15df", // 5599
    "transactionIndex":  "0x1", // 1
    "from":"0x407d73d8a49eeb85d32cf465507dd71d507100c1",
    "to":"0x85h43d8a49eeb85d32cf465507dd71d507100c1",
    "value":"0x7f110", // 520464
    "gas": "0x7f110", // 520464
    "gasPrice":"0x09184e72a000",
    "input":"0x603880600c6000396000f300603880600c6000396000f3603880600c6000396000f360",
  }
}
```