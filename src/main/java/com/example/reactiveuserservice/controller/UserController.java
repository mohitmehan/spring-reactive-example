package com.example.reactiveuserservice.controller;

import com.example.reactiveuserservice.dto.UserDto;
import com.example.reactiveuserservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    /**
     * Updates an existing user based on the request body.
     *
     * @param id The ID of the user to update
     * @param userDto The UserDto containing the updated user data
     * @return A Mono<UserDto> that will:
     *         - Return HTTP 200 OK with the updated user data if successful
     *         - Return HTTP 404 Not Found if no user exists with the given ID
     *         The successful response will contain the updated user data in JSON format
     */
    @PutMapping("/{id}")
    public Mono<UserDto> updateUser(@PathVariable String id, @Valid @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

    @GetMapping
    public Flux<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/email/{email}")
    public Mono<UserDto> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/search")
    public Flux<UserDto> getUsersByNameAndSalary(
            @RequestParam String name,
            @RequestParam BigDecimal salary) {
        return userService.getUsersByNameAndSalary(name, salary);
    }

    @GetMapping("/{id}/email/{email}")
    public Mono<UserDto> getUserByIdAndEmail(
            @PathVariable String id,
            @PathVariable String email) {
        return userService.getUserByIdAndEmail(id, email);
    }
} 