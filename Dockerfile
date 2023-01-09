FROM openjdk:17-oracle
RUN mvn -f ./fin-svc package
ARG JAR_FILE=./fin-svc/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]