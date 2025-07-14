package com.example.reactiveuserservice.handler;

import com.example.reactiveuserservice.dto.UserDto;
import com.example.reactiveuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(userService.getAllUsers(), UserDto.class);
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param request The ServerRequest containing the user ID in the path variable "id"
     * @return A Mono<ServerResponse> that will:
     *         - Return HTTP 200 OK with the user data if found
     *         - Return HTTP 404 Not Found if no user exists with the given ID
     *         The successful response will contain the user details in JSON format
     */         
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String userId = request.pathVariable("id");
        return userService.getUserById(userId)
                .flatMap(userDto -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(userDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Creates a new user based on the request body.
     *
     * @param request The ServerRequest containing the user data in the request body
     * @return A Mono<ServerResponse> that will:
     *         - Return HTTP 201 Created with the newly created user data if successful
     *         - Return HTTP 400 Bad Request if the request body is invalid
     *         The successful response will contain the newly created user data in JSON format
     */
    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserDto.class)
                .flatMap(userDto -> userService.createUser(userDto)
                        .flatMap(savedUser -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(savedUser)));
    }

    /**
     * Updates an existing user based on the request body.
     *
     * @param request The ServerRequest containing the user ID in the path variable "id" and the updated user data in the request body
     * @return A Mono<ServerResponse> that will:
     *         - Return HTTP 200 OK with the updated user data if successful    
     *         - Return HTTP 404 Not Found if no user exists with the given ID
     *         The successful response will contain the updated user data in JSON format
     */
    public Mono<ServerResponse> updateUser(ServerRequest request) {
        String userId = request.pathVariable("id");
        return request.bodyToMono(UserDto.class)
                .flatMap(userDto -> userService.updateUser(userId, userDto)
                        .flatMap(updatedUser -> ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(updatedUser)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String userId = request.pathVariable("id");
        return userService.deleteUser(userId)
                .then(ServerResponse.noContent().build());
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param request The ServerRequest containing the email address in the path variable "email"
     * @return A Mono<ServerResponse> that will:
     *         - Return HTTP 200 OK with the user data if found 
     *         - Return HTTP 404 Not Found if no user exists with the given email address
     *         The successful response will contain the user details in JSON format
     */
    public Mono<ServerResponse> getUserByEmail(ServerRequest request) {
        String email = request.pathVariable("email");
        return userService.getUserByEmail(email)
                .flatMap(userDto -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(userDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
} 