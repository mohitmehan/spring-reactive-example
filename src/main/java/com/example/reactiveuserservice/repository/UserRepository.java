package com.example.reactiveuserservice.repository;

import com.example.reactiveuserservice.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
     
    Mono<User> findByEmail(String email);
    
    Mono<Boolean> existsByEmail(String email);
    
    Flux<User> findByFirstNameAndSalary(String firstName, BigDecimal salary);
    
    Flux<User> findByLastNameAndSalary(String lastName, BigDecimal salary);
    
    Flux<User> findByFirstNameContainingIgnoreCaseAndSalary(String firstName, BigDecimal salary);
    
    Flux<User> findByLastNameContainingIgnoreCaseAndSalary(String lastName, BigDecimal salary);
    
    Mono<User> findByIdAndEmail(String id, String email);
} 