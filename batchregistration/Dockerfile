FROM library/openjdk:11-slim
EXPOSE $PORT
MAINTAINER Rodrigo Almeida - rodrigoalmeida.as@gmail.com
COPY target/batchregistration-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT exec java -Xms724m -Xmx724m -jar $JAVA_OPTS /app.jar