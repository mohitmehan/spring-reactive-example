package com.example.reactiveuserservice.controller;

/*import com.example.reactiveuserservice.dto.UserDto;
import com.example.reactiveuserservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private WebTestClient webTestClient;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .active(true)
                .build();
    }

    //@Test
    void createUser_Success() {
        when(userService.createUser(any(UserDto.class))).thenReturn(Mono.just(userDto));

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .isEqualTo(userDto);
    }

    //@Test
    void getUserById_Success() {
        when(userService.getUserById(anyString())).thenReturn(Mono.just(userDto));

        webTestClient.get()
                .uri("/api/users/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .isEqualTo(userDto);
    }

    //@Test
    void getUserById_NotFound() {
        when(userService.getUserById(anyString())).thenReturn(Mono.error(new RuntimeException("User not found")));

        webTestClient.get()
                .uri("/api/users/{id}", "999")
                .exchange()
                .expectStatus().isBadRequest();
    }

    //@Test
    void getAllUsers_Success() {
        when(userService.getAllUsers()).thenReturn(Flux.just(userDto));

        webTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .hasSize(1)
                .contains(userDto);
    }

    //@Test
    void updateUser_Success() {
        when(userService.updateUser(anyString(), any(UserDto.class))).thenReturn(Mono.just(userDto));

        webTestClient.put()
                .uri("/api/users/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .isEqualTo(userDto);
    }

    //@Test
    void deleteUser_Success() {
        when(userService.deleteUser(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/users/{id}", "1")
                .exchange()
                .expectStatus().isNoContent();
    }
} */