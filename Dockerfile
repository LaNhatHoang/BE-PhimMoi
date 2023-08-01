# FROM java:openjdk-7-jdk-alpine
# RUN sudo apt install oracle-java17-installer --install-recommends
FROM alpine:3.16.0
RUN apk add --no-cache java-cacerts openjdk17-jdk
WORKDIR /app
EXPOSE 8080
# RUN apt install oracle-java17-installer --install-recommends
RUN java -version
COPY ./target/Phimmoi-1.jar .
CMD ["java", "-jar", "Phimmoi-1.jar"]
