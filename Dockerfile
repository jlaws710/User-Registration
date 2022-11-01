FROM openjdk:11
COPY /build/libs/Kotlin-0.0.1-SNAPSHOT.jar Kotlin.jar
ENTRYPOINT ["java", "-jar", "Kotlin.jar"]