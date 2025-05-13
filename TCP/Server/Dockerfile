FROM openjdk:21-jdk-slim

COPY ./out/artifacts/Server_jar/Server.jar /app/Server.jar
COPY ./Mappa-dei-monumenti-in-Italia.csv /app/Mappa-dei-monumenti-in-Italia.csv

WORKDIR /app

EXPOSE 1050

CMD ["java", "-jar", "Server.jar"]