# MoneyTransfer

**Build**<br/>
mvn clean install

**Test**<br/>
mvn clean install

**Run**<br/>
from command line:

java -jar .\\target\\money-transfer-1.0-SNAPSHOT.jar

http://localhost:4567/api/	

<br/>
create new account

**POST** /accounts/:accountHolder

<br/>
get all account

**GET**  /accounts

<br/>
get account by account number

**GET**  /accounts/:accountNumber  

<br/>
get transactions by account number

**GET**  /transactions/:accountNumber  

<br/>
get account by account number and filter by period

**GET**  /transactions/:accountNumber/filter?begin=?&&end=?

<br/>
deposit amount into account

**POST** /transactions/:accountNumber/:balance/deposit 

<br/>
withdraw amount from account 

**POST** /transactions/:accountNumber/:balance/withdraw  

<br/>
transfer amount between accounts

**POST** /transactions/:fromAccountNumber/:toAccountNumber/:balance/transfer