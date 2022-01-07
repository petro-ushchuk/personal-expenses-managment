FROM openjdk:11
ARG JAR_FILE
EXPOSE 8080
COPY ${JAR_FILE} personal-expenses-managment.jar
ENTRYPOINT ["java", "-jar","/personal-expenses-managment.jar"]