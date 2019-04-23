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

![Arch](/src/main/resources/static/arch_dia.png)

For both GetProductDetails and UpdatePrice API, controller will subscribe to an observable method in service class. Observable in turn invoke Hystrix Command class for each external calls and provide the call back to controller.

For GetProductDetails, request will be asynchronously submitted to external systems like Target API and Mongo DB to fetch product name and product price respectively. Service class will then merge the output from each external calls and provide consolidated response to controller. Error code and error message will be added to the response json incase of any failure from external services, For example, if GetProductPrice from database gets timedout, reponse json will have valid product name returned from GetProductName and error information for Price.

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

UpdatePriceAPI method is synchronous, service class will first invoke getPricecommand to validate the product availabilty in database and then invoke updatepricecommand to update price details in database.

getPricecommand and updatepricecommand are hystrix command classes which will help in breaking the circuit incase of failure from any of the external API/DB call. Current configuraion of hystrix is set as follows. For an interval of 60 seconds, if 50% of the backend API/DB call fails, circuit will be open for 10 secs. This can be tested by providing product ids that are not available in target system.

EmbeddedMongoDb is used as database. A set of products will be inserted in to the database during application startup through spring configuration. MongoRepository is used to save and fetch Price details. Product Price will be stored to Ehcache during update price and retrived from cache during get price. 

### Build and Run

1. Clone the Project
2. Open Cmd and navigate to Project location
3. Run mvn spring-boot:run

Or 

3. Run mvn clean install
4. Run java -jar target/MyRetail-0.0.1-SNAPSHOT.jar

### Test Application

Use Swagger Url <http://localhost:8083/swagger-ui.html> to test the application. Provide X-CLIENT-ID as TARGET.

Available Product Ids for Test:-

13860428 :- have both Product Name and Price information

15117729, 16483589, 16696652, 16752456, 15643793:- Will only have price information No Product Name.

![Hystrix](/src/main/resources/static/swagger.png)

### Monitor Application

Use Actuator Url <http://localhost:8089/monitor> to get various monitoring links including Healthcheck

### Hystrix Dashboard

Input browser with <http://localhost:8083/hystrix>, Enter hystrix stream Url as <http://localhost:8089/monitor/hystrix.stream> .Trigger a GET or PUT call. You will be able to see the Hystrix Dashboard as below. Try to get Product details that are not available in Target System to see Open Circuit.

![Hystrix](/src/main/resources/static/hystrix.png)










