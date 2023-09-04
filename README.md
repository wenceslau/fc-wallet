# RADME FIRST....

## Wallet Core - Full Cycle


Os dois projetos estão construido em Java

## fc-wallet-core

Replica do projeto exposto em GOLANG, portanto contruido em Java
Seguindo a mesma distribuição dos pacotes e arquitetura

## fc-wallet-report

Projeto com consumidor Kafka conforme solicitado

## fc-utils

Classes de evento

# Containers

Executar o docker compose para inicar todos os container.
Validar se todos os container subiram

* db-mysql
* wallet-core
* wallet-report
* zookeeper
* kafka
* control-center

Abrir o control Center do Kafka http://localhost:9021/clusters
e criar os topicos

* transaction
* balances


# Criar uma transação

````
POST http://localhost:3002/transaction
Content-Type: application/json

{
  "accountIdFrom":  "18cd7316-d3f1-4842-b4fc-2f5d16333254",
  "accountIdTo" : "af0a5741-2d2d-42f6-b35c-73fd156063c3",
  "amount": 33
}
````

# Cosultar o Saldo

````
###
GET http://localhost:3003/balances/18cd7316-d3f1-4842-b4fc-2f5d16333254

````
