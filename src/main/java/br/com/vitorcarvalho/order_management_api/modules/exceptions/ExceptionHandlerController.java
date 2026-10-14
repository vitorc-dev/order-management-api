package br.com.vitorcarvalho.order_management_api.modules.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleValidationErrors(MethodArgumentNotValidException ex){
        List<ErrorMessageDTO> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(err ->
            errors.add(new ErrorMessageDTO(err.getDefaultMessage(), err.getField()))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleItemNotFoundException(ItemNotFoundException ex){
        ErrorMessageDTO error = new ErrorMessageDTO(ex.getMessage(), "id");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserNotFoundException(UserNotFoundException ex){
        ErrorMessageDTO error = new ErrorMessageDTO(ex.getMessage(), "id");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<String> handleInvalidCredentialException(InvalidCredentialException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        ErrorMessageDTO error = new ErrorMessageDTO(ex.getMessage(), "email");  
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
