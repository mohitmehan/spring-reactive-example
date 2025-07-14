package com.example.reactiveuserservice.service.impl;

import com.example.reactiveuserservice.dto.UserDto;
import com.example.reactiveuserservice.model.User;
import com.example.reactiveuserservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

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

    /**
     * Test for the getUserByEmail method when the user exists.
     * This test verifies that the getUserByEmail method correctly returns the user when found.
     */
    @Test
    void getUserByEmail_WhenUserExists_ReturnsUser() {
        // Setup
        String email = "john.doe@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));
        
        // Execute and Verify
        StepVerifier.create(userService.getUserByEmail(email))
                .assertNext(returnedUserDto -> {
                    assertEquals(userDto.getId(), returnedUserDto.getId());
                    assertEquals(userDto.getFirstName(), returnedUserDto.getFirstName());
                    assertEquals(userDto.getLastName(), returnedUserDto.getLastName());
                    assertEquals(userDto.getEmail(), returnedUserDto.getEmail());
                    assertEquals(userDto.getPhone(), returnedUserDto.getPhone());
                    assertEquals(userDto.isActive(), returnedUserDto.isActive());
                })
                .verifyComplete();
    }

    /**
     * Test for the getUserByEmail method when the user does not exist.
     * This test verifies that the getUserByEmail method throws an error when the user is not found.
     */
    @Test
    void getUserByEmail_WhenUserDoesNotExist_ThrowsException() {
        // Setup
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Mono.empty());
        
        // Execute and Verify
        StepVerifier.create(userService.getUserByEmail(email))
                .expectErrorMatches(error -> {
                    assertEquals(RuntimeException.class, error.getClass());
                    assertEquals("User not found with email: " + email, error.getMessage());
                    return true;
                })
                .verify();
    }

    /**
     * Test for the private getUserCount method using reflection.
     * This test verifies that the getUserCount method correctly returns the count of users.
     * 
     * @throws Exception if reflection access fails
     */
    @Test
    void getUserCount_ReturnsCorrectCount() throws Exception {
        // Setup
        User user1 = User.builder().id("1").firstName("John").build();
        User user2 = User.builder().id("2").firstName("Jane").build();
        User user3 = User.builder().id("3").firstName("Bob").build();
        
        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2, user3));
        
        // Use reflection to access the private method
        Method getUserCountMethod = UserServiceImpl.class.getDeclaredMethod("getUserCount");
        getUserCountMethod.setAccessible(true);
        
        // Execute
        long count = (long) getUserCountMethod.invoke(userService);
        
        // Verify
        assertEquals(3, count, "getUserCount should return the correct number of users");
    }
    
    /**
     * Test for the private getUserCount method when no users exist.
     * This test verifies that the getUserCount method returns zero when there are no users.
     * 
     * @throws Exception if reflection access fails
     */
    @Test
    void getUserCount_ReturnsZeroWhenNoUsers() throws Exception {
        // Setup
        when(userRepository.findAll()).thenReturn(Flux.empty());
        
        // Use reflection to access the private method
        Method getUserCountMethod = UserServiceImpl.class.getDeclaredMethod("getUserCount");
        getUserCountMethod.setAccessible(true);
        
        // Execute
        long count = (long) getUserCountMethod.invoke(userService);
        
        // Verify
        assertEquals(0, count, "getUserCount should return zero when no users exist");
    }
} 