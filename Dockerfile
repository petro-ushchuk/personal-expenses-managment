FROM openjdk:11
ADD target/java-jenkins-docker.jar java-jenkins-docker.jar
ENTRYPOINT ["java", "-jar","personal-expenses-managment.jar"]
EXPOSE 8080