FROM openjdk:11
ADD /build/libs/Project-1-0.0.1-SNAPSHOT.jar project-1.jar
ENTRYPOINT ["java", "-jar", "project-1.jar"]