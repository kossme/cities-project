FROM openjdk:11-slim
ADD build/libs/cities-project.jar cities-project.jar

ENTRYPOINT java -jar $JAVA_OPTS cities-project.jar
