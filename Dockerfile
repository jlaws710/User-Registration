#FROM openjdk:11
#LABEL maintainer="project-1"
#ADD /build/libs/Project-1-0.0.1-SNAPSHOT.jar project-1.jar
#ENTRYPOINT ["java", "-jar", "project-1.jar"]

FROM cassandra:3.11
# Fix UTF-8 accents in init scripts
ENV LANG C.UTF-8

# Here, you can add any *.sh or *.cql scripts inside /docker-entrypoint-initdb.d
#  *.sh files will be executed BEFORE launching cassandra
#  *.cql files will be executed with cqlsh -f AFTER cassandra started
# Files are executed in name order (ls * | sort)
#COPY *.cql /docker-entrypoint-initdb.d/
COPY src/main/java/com/Project1/scripts/init.sh /docker-entrypoint-initdb.d/
# this is the script that will patch the already existing entrypoint from cassandra image
COPY src/main/java/com/Project1/scripts/entrypoint.sh /

# Override ENTRYPOINT, keep CMD
ENTRYPOINT ["init.sh","/entrypoint.sh"]
EXPOSE 7000 7001 7199 9042
CMD ["cassandra", "-f"]

FROM openjdk:11
ADD /build/libs/Project-1-0.0.1-SNAPSHOT.jar project-1.jar
ENTRYPOINT ["java", "-jar", "project-1.jar"]