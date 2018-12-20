# Iroha ScalaDSL

mock up code, heavily inspired by Akka graphs DSL

```scala
import org.hyperledger.iroha.scaladsl

val time = System.currentTimeMillis
val authorAccount = Iroha.Account("sender@account")
val statementsAccount = Iroha.Account("statements@bank")
val authorityAccount = Iroha.Account("authority@bank")

val transaction = Transaction.create(authorAccount, time) { implicit builder =>
  import Builder.Implicits._
  import Iroha._
  
  add CreateAccount(Account("account1", "domain"), defaultKeypair.publicKey)
  add TransferAsset(Account("account@domain", "account1@domain"), Asset("usd#domain"), Description("description"), BigDecimal(5))
  
  // Option A
  needs authorAccount signature
  needs statementsAccount signature
  needs authorityAccount signature
  // Option B
  quorum(3 signatures)
}

val payload = Payload.createFromSeq(Seq(transaction))

val authorKeypair = Iroha.Keypair.fromBytes(supersecret§)
val statementsKeypair = Iroha.Keypair.fromBytes(supersecret2)

grpc.torii(
  payload
    .sign(authorAccount, authorKeypair)
    .sign(statementsAccount, statementsKeypair)
    .build
)
```
