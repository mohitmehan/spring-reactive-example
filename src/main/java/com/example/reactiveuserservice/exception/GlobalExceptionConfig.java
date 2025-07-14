package com.example.reactiveuserservice.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class GlobalExceptionConfig {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
    
    @Component
    @Order(-2)
    public static class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
        
        public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                             WebProperties.Resources resources,
                                             ApplicationContext applicationContext,
                                             ServerCodecConfigurer configurer) {
            super(errorAttributes, resources, applicationContext);
            this.setMessageWriters(configurer.getWriters());
        }

        @Override
        protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
            return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
        }

        private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
            Map<String, Object> errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
            
            Throwable error = getError(request);
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            String message = "Internal Server Error";
            
            if (error instanceof RuntimeException) {
                status = HttpStatus.BAD_REQUEST;
                message = error.getMessage();
            } else if (error instanceof WebExchangeBindException) {
                status = HttpStatus.BAD_REQUEST;
                WebExchangeBindException ex = (WebExchangeBindException) error;
                message = ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .collect(Collectors.joining(", "));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", status.value());
            response.put("error", status.getReasonPhrase());
            response.put("message", message);
            response.put("path", request.path());
            
            return ServerResponse.status(status)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(response));
        }
    }
} 