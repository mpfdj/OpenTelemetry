package nl.craftsmen.brewery.util;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    @Order(0)
    public ResponseEntity<String> exception(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
