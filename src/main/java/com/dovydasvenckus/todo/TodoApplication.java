package com.dovydasvenckus.todo;

import spark.Request;
import spark.Response;
import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

public class TodoApplication {

 public static void main(String[] args) {
   staticFileLocation("/public");
   get("/hello", TodoApplication::helloWorld);

 }

 public static String helloWorld(Request req, Response res) {
    return "hello world!";
 }
}
