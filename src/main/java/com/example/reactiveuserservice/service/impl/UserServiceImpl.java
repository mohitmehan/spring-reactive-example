package com.example.reactiveuserservice.service.impl;

import com.example.reactiveuserservice.dto.UserDto;
import com.example.reactiveuserservice.model.User;
import com.example.reactiveuserservice.repository.UserRepository;
import com.example.reactiveuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDto> createUser(UserDto userDto) {
        return userRepository.existsByEmail(userDto.getEmail())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new RuntimeException("Email already exists"));
                    }
                    User user = mapToEntity(userDto);
                    return userRepository.save(user)
                            .map(this::mapToDto);
                });
    }

    @Override
    public Mono<UserDto> getUserById(String id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + id)));
    }

    @Override
    public Mono<UserDto> updateUser(String id, UserDto userDto) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setFirstName(userDto.getFirstName());
                    existingUser.setLastName(userDto.getLastName());
                    existingUser.setPhone(userDto.getPhone());
                    existingUser.setActive(userDto.isActive());
                    
                    // Check if email is being changed
                    if (!existingUser.getEmail().equals(userDto.getEmail())) {
                        return userRepository.existsByEmail(userDto.getEmail())
                                .flatMap(exists -> {
                                    if (Boolean.TRUE.equals(exists)) {
                                        return Mono.error(new RuntimeException("Email already exists"));
                                    }
                                    existingUser.setEmail(userDto.getEmail());
                                    return userRepository.save(existingUser);
                                })
                                .map(this::mapToDto);
                    }
                    
                    return userRepository.save(existingUser)
                            .map(this::mapToDto);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + id)));
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + id)))
                .flatMap(userRepository::delete);
    }

    @Override
    public Flux<UserDto> getAllUsers() {
        return userRepository.findAll()
                .map(this::mapToDto);
    }

    @Override
    public Mono<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::mapToDto)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with email: " + email)));
    }

    private User mapToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .active(userDto.isActive())
                .build();
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .active(user.isActive())
                .build();
    }

    private long getUserCount() {
        return userRepository.findAll().count().block();
    }
} 