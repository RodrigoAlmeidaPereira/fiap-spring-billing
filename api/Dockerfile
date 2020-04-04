FROM library/openjdk:9-slim
EXPOSE $PORT
MAINTAINER Rodrigo Almeida - rodrigoalmeida.as@gmail.com
COPY target/api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT exec java -Xms724m -Xmx724m -jar /app.jar