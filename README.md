# myRetail
myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. 
The goal for this project is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller.

### Technologies

* Java8
* Maven 4.0
* Spring Boot 2.1.4
* Embedded Mongo DB
* EhCache
* Hystrix Circuit Breaker
* Hystrix Dashboard
* Swagger2
* Rx Java

### Technical Description

![Arch](/src/main/resources/static/arch.png)

For both GetProduct and UpdatePrice API, controller will subscribe to Observable in Service. Service in turn invoke Hystrix Command Class for each external layers and provide the call back to Controller.

For GetProductAPI, request will be asynchronously submitted to external target API to get product name and Embedded Mongo DB to get price. Service class will then merge output from each calls and provide appropriate response to controller. incase of failure from any of the external service corresponding code ands error message will be added to the response json. For example if timeout happens while getting product price, reponse will have valid product name and error message for Price.

{
  "productId": 13860428,
  "productName": "The Big Lebowski (Blu-ray)",
  "errors": [
    {
      "code": "404",
      "description": "Product Price not available"
    }
  ]
}

For UpdatePriceAPI is synchronous, Service class will first invoke getPricecommand to check whether the product is available and then invoke updateprice to update the price in database.

EmbeddedMongoDb is used as database and few products will be inserted duriong the server startup through spring configuration. MongoRepository is used to save and fetch Price details.

Hystrix will break the circuit for corresponding API/DB call during timout. Current configuraion is, In 60 seconds , if 50% of the backend API/DB call fails ,  circuit will be open for 10 secs. This can be tested by providing product ids that are not in the target systems.

### Build and Run

1. Clone the Project
2. Open Cmd and navigate to Project location
3. Run mvn spring-boot:run

Or 

3. Run mvn clean install
4. Run java -jar target/MyRetail-0.0.1-SNAPSHOT.jar

### Test Application

Use Swagger Url <http://localhost:8083/swagger-ui.html> to test the application. Provide X-CKIENT-ID as TARGET.
Avaialble Product Ids

13860428 :- has both Product Name and Price
15117729, 16483589, 16696652, 16752456, 15643793:- Will only have price information.

![Hystrix](/src/main/resources/static/swagger.png)

### Monitor Application

Use Actuator Url <http://localhost:8089/monitor> to get various monitoring links including Healcheck

### Hystrix Dashboard

Input browser with <http://localhost:8083/hystrix>, Enter hystrix stream Url as <http://localhost:8089/monitor/hystrix.stream> .Trigger an aPI call. You will be able to see the Hystrix Dashboard as below.

![Hystrix](/src/main/resources/static/hystrix.png)










