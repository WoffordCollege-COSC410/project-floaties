@echo off
geth --datadir ethereum/node0 init ethereum/genesis.json
geth --allow-insecure-unlock --datadir ethereum/node0 --rpc --rpcapi 'personal,db,eth,net,web3,txpool,miner' --gasprice 0 -unlock 0 --password ethereum/node0/password.txt --mine