FROM maven:3.8.3-openjdk-17 AS MAVEN_BUILD
WORKDIR /build/
COPY settings.xml /usr/share/maven/conf/settings.xml
COPY pom.xml /build/
COPY src /build/src/
# RUN mvn install -Dmaven.test.skip=true
# COPY ./src/main/resources/app.yml /build/src/main/resources/application.yml
RUN mvn package -Dmaven.test.skip=true
FROM openjdk:11
WORKDIR /root
EXPOSE 80
COPY --from=MAVEN_BUILD /build/target/orientation-system.jar /root/orientation-system.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar", "orientation-system.jar"]