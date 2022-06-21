# Distributed Database

This application is a distributed database built without using any existing DBMS in market. For better understanding of database and distributed database, file (txt) is used as a storage.

Two instances are setup to act as a distributed database with vertical fragmentation. Upon a query or DML command our system will refer to global metadata to decide which instance does it expect operation (or both). Local metadata of each instance will be referred (using Map Abstract data structures) to locate the table name involved in the query. The data will be loaded in data structures and will be written in a temporary file. Once the user commits the temporary file will transfer changes permanently to file (.txt) maintaining atomicity and consistency. The two instances will perform bi-directional communication using server socket.


## Architecture
![Screen Output.](/images/1.jpg "1.jpg")

## Architectural Diagram of Distribution Layer

![Screen Output.](/images/2.jpg "1.jpg")

## Tools and Languages used

* Java
* Maven
