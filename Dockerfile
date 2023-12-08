FROM openjdk:21
LABEL authors="Dille"

ARG JAR_FILE=*.jar

COPY . .
COPY target/ccxt-0.0.1-SNAPSHOT.jar app/app.jar
COPY src/main/resources/symbols/symbols.json app/src/main/resources/symbols/symbols.json
COPY src/main/resources/env/application.properties app/src/main/resources/application.properties

WORKDIR /app
ENTRYPOINT ["java","-jar","app.jar"]