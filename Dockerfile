FROM openjdk:11
#VOLUME /tmp
MAINTAINER Liping
COPY ./target/mediscreen-patient-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]