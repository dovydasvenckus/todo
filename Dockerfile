FROM openjdk:8-jdk-alpine
MAINTAINER Dovydas Venckus "dovydas.venckus@live.com"
ENV APP_ROOT=/home/todo \
    APP_NAME=todo-api-1.0-all.jar
WORKDIR $APP_ROOT
RUN apk --update add git && \
    git clone https://github.com/dovydasvenckus/todo-api.git && \
    apk --update del git && \
    cd todo-api && \
    ./gradlew build && \
    cp todo-api/build/libs/$APP_NAME .. && \
    cd .. && \
    rm -r todo-api && \
    rm -r /root/.gradle && \
    rm -r /var/cache/apk
USER nobody
ENTRYPOINT ["/bin/sh", "-c", \
 "java -jar $APP_NAME --app-user $APP_USER --app-pass $APP_PASS"]
EXPOSE 8080