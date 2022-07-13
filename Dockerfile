FROM docker-spring-boot-postgres:latest
EXPOSE 9042
WORKDIR /app
ARG JAR_FILE=typeqast-meter-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
COPY typeqast-meter-api-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java","-jar","typeqast-meter-api-0.0.1-SNAPSHOT.jar"]
RUN ls -la
