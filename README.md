# Todo web api
[![Build Status](https://travis-ci.org/dovydasvenckus/todo-api.svg?branch=master)](https://travis-ci.org/dovydasvenckus/todo-api)
Lightweight simple todo list web api.

This is my pet project. This application uses just a handful of lightweight libs. I have chosen this approach for the sake of discovering pain points that heavy frameworks (Spring, Hibernate) and  other libs solve.

In this project I'm using an existing HTTP server, JSON parsing or security libs.

## Instructions

### Building

#### Building only API jar

    ./gradlew build

Running this command will build fatjar in build/libs/todo-all.jar

#### Building jar that will serve web and API
This command will pull web project and place it inside jar.

    ./gradlew stage
    
Running this command will build fatjar in build/libs/todo-all.jar

### Running
Application uses 8080 port.
 
#### Basics
Currently this app supports only one user. When launching jar you must pass user credentials.

    java -jar todo-all.jar --app-user test --app-pass test

#### Database configuration
By default this application uses in memory H2 database on boot.
But you can configure to use PostgreSQL by passing parameters when launching application.

    java -jar todo-all.jar --app-user test --app-pass test --db-url jdbc:postgresql:database --db-user test --db-pass test

## Web repository
You can find web code in this repository[todo-web](https://github.com/dovydasvenckus/todo-web).

## Disclaimer
This project doesn't reflect my values as a software developer. In this project I might be "reinventing the bicycle" just for the sake of implementing it and learning how to solve some problems without external libs.
