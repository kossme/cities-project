FROM openjdk:11-slim
ADD build/libs/test-crud-cities-project.jar test-crud-cities-project.jar

ENTRYPOINT java -jar $JAVA_OPTS test-crud-cities-project.jar
