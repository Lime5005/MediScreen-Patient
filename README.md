# MediScreenApp
OpenClassrooms DA JAVA Projet 9
- To run the project, add in `application.properties`:
```properties
spring.jpa.database=mysql
spring.jpa.database-platform=mysql
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/mediscreenapp?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&characterEncoding=utf8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=ur_username
spring.datasource.password=ur_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.error.whitelabel.enabled=false
```
