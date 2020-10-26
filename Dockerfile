FROM openjdk:11.0.8-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/*.jar /home/demo.jar
WORKDIR /home
RUN sh -c 'touch demo.jar'
ENTRYPOINT ["java","-jar","demo.jar"]