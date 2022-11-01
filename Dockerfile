FROM openjdk:11
COPY /build/libs/Project-1-0.0.1-SNAPSHOT.jar project-1.jar
CMD ["java", "-jar", "project-1.jar"]