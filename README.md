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

![alt text](https://github.com/sajivijaysadas/MyRetail/tree/master/src/main/resources/static/images/arch.png "")



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

### Monitor Application

Use Actuator Url <http://localhost:8089/monitor> to get various monitoring links including Healcheck

### Hystrix Dashboard

Input browser with <http://localhost:8083/hystrix>, Enter hystrix stream Url as <http://localhost:8089/monitor/hystrix.stream> .Trigger an aPI call. You will be able to see the Hystrix Dashboard as below

in 60 seconds , if 50% of the backend API/DB call fails , Hystrix will break the circuit for corresponding API/DB call and sleep for 10 secs. You can provide product ids that ar enot in the target system to verify this.

![alt text](https://github.com/sajivijaysadas/MyRetail/tree/master/src/main/resources/static/images/hystrix.png "")











