FROM maven:3.8.1-openjdk-17-slim AS MAVEN_BUILD

RUN mkdir /sources
COPY ./ /sources

RUN echo "Building app..." && cd /sources && mvn clean package -DskipTests

FROM openjdk:17-oracle

WORKDIR /app
COPY --from=MAVEN_BUILD /sources/target/MSGF-BPM-Engine-1.0.0-SNAPSHOT.jar /app/MSGF-BPM-Engine-1.0.0-SNAPSHOT.jar
EXPOSE 9000

CMD ["java", "-jar", "MSGF-BPM-Engine-1.0.0-SNAPSHOT.jar"]