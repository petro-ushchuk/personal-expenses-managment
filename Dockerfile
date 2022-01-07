FROM openjdk:11
EXPOSE 8080
RUN mkdir /app
COPY build/libs/*.jar /app/personal-expenses-managment.jar
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/personal-expenses-managment.jar"]