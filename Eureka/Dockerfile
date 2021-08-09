FROM openjdk:11
ADD target/docker-eureka.jar docker-eureka.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar","docker-eureka.jar"]