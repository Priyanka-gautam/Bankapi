FROM openjdk:8
copy ./target/Bankingapi-0.0.1-SNAPSHOT.jar Bankingapi-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","Bankingapi-0.0.1-SNAPSHOT.jar"]