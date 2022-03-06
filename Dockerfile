FROM maven:3.8.3-openjdk-17 AS MAVEN_BUILD
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src/
COPY src/main/resources/prod.yaml /build/src/main/resources/application.yaml
RUN mvn package -Dmaven.test.skip=true
FROM openjdk:11
WORKDIR /root
EXPOSE 80
COPY --from=MAVEN_BUILD /build/target/orientation-system.jar /root/orientation-system.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar", "orientation-system.jar"]