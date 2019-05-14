FROM openjdk:8-jdk-alpine
MAINTAINER Dovydas Venckus "dovydas.venckus@live.com"
ENV APP_ROOT=/home/todo \
    APP_NAME=todo-api-1.0-all.jar
WORKDIR $APP_ROOT
COPY [".", "${APP_ROOT}/source/"]
RUN cd source && \
    ./gradlew build && \
    cp todo-api/build/libs/$APP_NAME .. && \
    cd .. && \
    rm -r source && \
    rm -r /root/.gradle
USER nobody
ENTRYPOINT ["/bin/sh", "-c", \
 "java -jar $APP_NAME --app-user $APP_USER --app-pass $APP_PASS --db-url $TODO_DB_JDBC --db-user $TODO_DB_USERNAME --db-pass $TODO_DB_PASSWORD"]
EXPOSE 8080