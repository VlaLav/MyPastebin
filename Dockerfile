FROM openjdk:17-jdk-alpine
MAINTAINER Saleev Vladyslav
COPY target/MyPastebin-0.0.1-SNAPSHOT.jar pastebox.jar
ENTRYPOINT ["java","-jar","/pastebox.jar"]