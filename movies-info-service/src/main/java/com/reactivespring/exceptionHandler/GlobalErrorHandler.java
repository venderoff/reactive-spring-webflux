package com.reactivespring.exceptionHandler;


import com.reactivespring.domain.MovieInfo;
import com.reactivespring.dto.Err;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {


    @ExceptionHandler(WebExchangeBindException.class)
//    public ResponseEntity<String> handleReqBodyErr(WebExchangeBindException  ex) {
    public ResponseEntity<Err> handleReqBodyErr(WebExchangeBindException  ex) {

        log.error("Exception caught in request : {}" ,ex.getMessage(),ex );

        var error =
        ex.getBindingResult().getAllErrors()     //list created
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(",")) ;

         log.error("Error is : {}", error);


//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Err().setError(error)) ;
         return new ResponseEntity<>(new Err(error), HttpStatus.BAD_REQUEST) ;

    }

}
