FROM openjdk:18
EXPOSE 9090
COPY target/getapet-0.0.1-SNAPSHOT.jar getapet-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "lab-wp.jar"]