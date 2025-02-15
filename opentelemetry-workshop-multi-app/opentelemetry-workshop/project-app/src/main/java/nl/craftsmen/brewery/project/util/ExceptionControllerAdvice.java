package nl.craftsmen.brewery.project.util;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    @Order(/* Ordered.LOWEST_PRECEDENCE */)
    public ResponseEntity<String> exception(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
