FROM openjdk:11-jre-slim

WORKDIR /usr/app

COPY ./target/beerquest-0.0.1-SNAPSHOT.jar /usr/app

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "beerquest-0.0.1-SNAPSHOT.jar"]

