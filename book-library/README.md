# Book Library Case Study
This project is created with Spring Boot version 2.7.7

#### Steps to run the project

1. Checkout the project.
2. Run `mvn clean compile` on each sub-modules.
3. Once it done, follow the sequence to boot up the services.
   1. first start the eureka-server(will run on 8761).
   2. start book & subscription services(will run on 8081 & 8082 respectively).
   3. start service gateway(will run on 8989).
4. Import postman json to communicate with services.


#### Covered Topics
```
Spring Boot - 2.7.7
MySQL DB
RestTemplate
Feign Client / OpenFeign - 3.1.2
Netflix Eureka - 3.1.2
Spring Cloud Gateway - 3.1.2
Basic Logging
```

