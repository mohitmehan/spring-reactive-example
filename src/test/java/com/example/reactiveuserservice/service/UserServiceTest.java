package com.example.reactiveuserservice.service;
/* 
import com.example.reactiveuserservice.dto.UserDto;
import com.example.reactiveuserservice.model.User;
import com.example.reactiveuserservice.repository.UserRepository;
import com.example.reactiveuserservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDto = UserDto.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .active(true)
                .build();

        user = User.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .active(true)
                .build();
    }

    @Test
    void createUser_Success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(false));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<UserDto> result = userService.createUser(userDto);

        StepVerifier.create(result)
                .expectNextMatches(savedUser -> 
                        savedUser.getId().equals("1") &&
                        savedUser.getFirstName().equals("John") &&
                        savedUser.getEmail().equals("john.doe@example.com"))
                .verifyComplete();
    }

    @Test
    void createUser_EmailExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(true));

        Mono<UserDto> result = userService.createUser(userDto);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));

        Mono<UserDto> result = userService.getUserById("1");

        StepVerifier.create(result)
                .expectNextMatches(foundUser -> 
                        foundUser.getId().equals("1") &&
                        foundUser.getFirstName().equals("John"))
                .verifyComplete();
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<UserDto> result = userService.getUserById("999");

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void getAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(Flux.just(user));

        Flux<UserDto> result = userService.getAllUsers();

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
} */