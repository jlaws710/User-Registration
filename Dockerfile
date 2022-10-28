FROM openjdk:11
COPY /build/libs/Project-V1-0.0.1-SNAPSHOT.jar project-v1.jar
ENTRYPOINT ["java", "-jar", "project-v1.jar"]