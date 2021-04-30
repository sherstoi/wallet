# Leo Wallet Microservice

## Getting Started  
  
### Prerequisites  
  
- JDK 11  
- Maven 3.2+   
  
### Install deps  
  
## compile and build jar file  
  
- `mvn clean install spring-boot:repackage`  
  
## Run  
  
- `java -jar target/leo-wallet-1.0-SNAPSHOT.jar`

## Tools  
  
### Swagger UI  
  
- swagger ui must be available via `http://localhost:8080/swagger-ui.html`  
  
## Endpoints

- Get:  `/wallet/player/{id}/balance`
`curl -X GET "http://localhost:8080/wallet/player/12345/balance" -H "accept: */*"`

- Get:  `/wallet/player/{id}/transactions`
`curl -X GET "http://localhost:8080/wallet/player/12345/transaction" -H "accept: */*"`

- Post: `/wallet/player/{id}` 
`curl -X POST "http://localhost:8080/wallet/player/12345" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"amount\": 1001, \"id\": 23457}"`
-----------------------------------------------------------------------------------------------

For testing purposes I'm loading player with id `12345` to H2 so you can check endpoints output.

## Requirements 
- Atomicity was implemented with java `synchronized` mechanism
- For processing concurrent connection H2 database must be switched from `embedded` to `server` mode: http://www.h2database.com/html/tutorial.html#using_server
- Horizontal scaling can be achieved by deploying application to different nodes
- Code was cleaned up with SonarLint plugin
- 
