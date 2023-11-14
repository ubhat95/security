FROM openjdk:17
EXPOSE 8080
ADD target/springsecurity.jar springsecurity.jar
ENTRYPOINT [ "java", "-jar", "/springsecurity.jar" ]