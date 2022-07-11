FROM openjdk:18.0.1.1
WORKDIR /app
COPY TypeqastMeterAPI*.jar /app
EXPOSE 9042
CMD ["java", "-jar", "TypeqastMeterAPI-0.0.1-SNAPSHOT.jar"]
