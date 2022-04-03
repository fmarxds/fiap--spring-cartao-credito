FROM gradle:7.4-jdk11

RUN mkdir /app

COPY /gradle /app/gradle
COPY /src /app/src
COPY build.gradle /app/
COPY settings.gradle /app/
COPY gradlew /app/
COPY gradlew.bat /app/

WORKDIR /app

RUN gradle clean build
RUN cp /app/build/libs/*.jar /app/app.jar

EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]