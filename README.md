# MoneyTransfer

**Build**<br/>
mvn clean install

**Test**<br/>
mvn clean install

**Run**<br/>
from command line:

java -jar .\\target\\money-transfer-1.0-SNAPSHOT.jar

http://localhost:4567/api/	

create new account

**POST** /accounts/:accountHolder

get all account

**GET**  /accounts

get account by account number

**GET**  /accounts/:accountNumber  

get transactions by account number

**GET**  /transactions/:accountNumber  

get account by account number and filter by period

**GET**  /transactions/:accountNumber/filter?begin=?&&end=?

deposit amount into account

**POST** /transactions/:accountNumber/:balance/deposit 

withdraw amount from account 

**POST** /transactions/:accountNumber/:balance/withdraw  

transfer amount between accounts

**POST** /transactions/:fromAccountNumber/:toAccountNumber/:balance/transfer