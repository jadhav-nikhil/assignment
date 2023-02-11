From openjdk:8
EXPOSE 9000
ADD target/assignment-example-0.0.1-SNAPSHOT.jar assignment-example-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/assignment-example-0.0.1-SNAPSHOT.jar"]