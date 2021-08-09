FROM openjdk:11
ADD target/docker-Zuul.jar docker-Zuul.jar
EXPOSE 3333
ENTRYPOINT ["java", "-jar","docker-Zuul.jar"]