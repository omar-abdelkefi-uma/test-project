package io.glide.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

//@Component
public class RestResponseStatusExceptionResolver{ //implements WebExceptionHandler {


//    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
//        if (ex instanceof MethodArgumentTypeMismatchException) {
//            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
//
//            // marks the response as complete and forbids writing to it
//            return exchange.getResponse().setComplete();
//
//            List<String> details = new ArrayList<>();
//            for(ObjectError error : ex.getBindingResult().getAllErrors()) {
//                details.add(error.getDefaultMessage());
//            }
//            ErrorDetails error = new ErrorDetails(HttpStatus.BAD_REQUEST, "Validation Failed \n" +details);
//            return Mono.error(error);
//        }
//        return Mono.error(ex);
//    }


}
