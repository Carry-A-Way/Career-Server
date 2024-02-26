# x86
FROM openjdk:17-alpine

# arm64
#FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar
#
#RUN mkdir src/main/resources
#
#RUN cp /var/jenkins/gradle/*es gradle/wrapper
#
#RUN cp /var/jenkins/app/*es src/main/resources

ENTRYPOINT ["java","-jar","/app.jar"]