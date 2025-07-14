package com.example.reactiveuserservice.router;

import com.example.reactiveuserservice.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return RouterFunctions
                .route(GET("/api/v2/users").and(accept(APPLICATION_JSON)), userHandler::getAllUsers)
                .andRoute(GET("/api/v2/users/{id}").and(accept(APPLICATION_JSON)), userHandler::getUserById)
                .andRoute(POST("/api/v2/users").and(accept(APPLICATION_JSON)), userHandler::createUser)
                .andRoute(PUT("/api/v2/users/{id}").and(accept(APPLICATION_JSON)), userHandler::updateUser)
                .andRoute(DELETE("/api/v2/users/{id}"), userHandler::deleteUser)
                .andRoute(GET("/api/v2/users/email/{email}").and(accept(APPLICATION_JSON)), userHandler::getUserByEmail);
    }
} 