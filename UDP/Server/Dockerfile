FROM openjdk:23-jdk-slim

RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxtst6 \
    libxi6 \
    libxrender1 \
    xauth \
    libfreetype6 \
    fontconfig \
    && rm -rf /var/lib/apt/lists/*

COPY ./out/artifacts/Server_jar/Server.jar /app/Server.jar
COPY ./Mappa-dei-monumenti-in-Italia.csv /app/Mappa-dei-monumenti-in-Italia.csv

WORKDIR /app

EXPOSE 1050

CMD ["java", "-jar", "Server.jar"]