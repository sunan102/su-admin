FROM openjdk:8-jre
VOLUME /app
MAINTAINER "su"<978230136@qq.com>
ADD target/*.jar app.jar
EXPOSE 7080
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java  -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]