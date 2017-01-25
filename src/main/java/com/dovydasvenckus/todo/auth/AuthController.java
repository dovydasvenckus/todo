package com.dovydasvenckus.todo.auth;

import com.dovydasvenckus.todo.util.Controller;
import spark.Response;

import static spark.Spark.post;

public class AuthController implements Controller {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void setupRoutes() {
        post("/auth/logged", (request, response) -> {
            try {
                if (!authService.isAuthorized(request)) {
                    unauthorized(response);
                }
            }
            catch (RuntimeException ex) {
                unauthorized(response);
            }
            return "";
        });
    }

    private void unauthorized(Response response) {
        response.status(401);
        response.body("Unauthorized");
    }
}
