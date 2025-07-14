package com.example.reactiveuserservice.service;

import com.example.reactiveuserservice.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

public interface UserService {
    
    Mono<UserDto> createUser(UserDto userDto);
    
    Mono<UserDto> getUserById(String id);
    
    Mono<UserDto> updateUser(String id, UserDto userDto);
    
    Mono<Void> deleteUser(String id);
    
    Flux<UserDto> getAllUsers();
    
    Mono<UserDto> getUserByEmail(String email);
    
    Flux<UserDto> getUsersByNameAndSalary(String name, BigDecimal salary);
    
    Mono<UserDto> getUserByIdAndEmail(String id, String email);
} 