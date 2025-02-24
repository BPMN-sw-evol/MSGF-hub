# MSGF case study
# container definition for the applications

# stage 1 : build
FROM maven:3.8.1-openjdk-17-slim AS MAVEN_BUILD

RUN mkdir /sources
COPY ./ /sources

RUN echo "Building app..." \
    && cd /sources \
    && mvn clean package -DskipTests

# el build debió quedar en /sources/target

# stage 2 : package for running
FROM openjdk:17-oracle

WORKDIR /app
COPY --from=MAVEN_BUILD /sources/BPM-Engine/target/*.jar ./BPM-Engine.jar
EXPOSE 9000

CMD ["java", "-jar", "BPM-Engine.jar"]