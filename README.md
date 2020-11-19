# Wolf management REST AP

#### 1. Application.properties configuration

```
## Active Profile
spring.profiles.active=default
## MicroService name
spring.application.name="wolf_management"
## Port
server.port=8080
## MySQL DataSource
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/wolf_db
spring.datasource.username=##
spring.datasource.password=##
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
## JPA
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
## LOGs
#logging.level.org.springframework=DEBUG
#logging.level.org.hibernate.SQL=DEBUG
#logging.level.org.hibernate.type=TRACE
##
spring.mvc.throw-exception-if-no-handler-found=true
spring.resources.add-mappings=false
```

#### 2. Rest API documentation
    
 http://localhost:8080/swagger-ui/
