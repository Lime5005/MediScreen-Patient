# MediScreenApp
OpenClassrooms DA JAVA Projet 9
- To run the project, add in `application.properties`:
```properties
server.port=8081
spring.application.name=mediscreen-patient
patient.serviceUrl=http://localhost:${server.port}
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/mediscreenapp
spring.datasource.username=${MYSQL_USER:ur_username}
spring.datasource.password=${MYSQL_PASSWORD:ur_password}
server.error.whitelabel.enabled=false
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
```
