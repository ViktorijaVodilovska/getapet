FROM openjdk:11
EXPOSE 9090
COPY target/lab-wp.jar lab-wp.jar
ENTRYPOINT ["java", "-jar", "lab-wp.jar"]