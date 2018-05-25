FROM alpine:latest
MAINTAINER Dovydas Venckus "dovydas.venckus@live.com"
RUN apk --update add openjdk8
ENV APP_ROOT=/home/todo \
    APP_NAME=todo-api-1.0-all.jar
WORKDIR $APP_ROOT
ADD "." $APP_ROOT
RUN ./gradlew build
ENTRYPOINT ["/bin/sh", "-c", \
 "java -jar todo-api/build/libs/$APP_NAME --app-user $APP_USER --app-pass $APP_PASS"]
EXPOSE 8080