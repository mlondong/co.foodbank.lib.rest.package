package co.com.foodbank.pckage.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.exception
 *         11/07/2021
 */
@ControllerAdvice
public class ControllerAdvisor {



    /**
     * Method to handle user not found by id.
     */
    @ExceptionHandler(value = PackageNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(
            PackageNotFoundException ex) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());
    }



    /**
     * Method to handle constrains Exceptions.
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleExceptionConstrains(
            ConstraintViolationException ex) {

        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());

    }


    /**
     * Method to handle HttpMessageNotReadableException.
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> httpMessageNotReadableException(
            HttpMessageNotReadableException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());

    }



    /**
     * Method to handle UnexpectedTypeException.
     */
    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ResponseEntity<Object> handleUnexpectedTypeException(
            UnexpectedTypeException ex) {

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());

    }


    /**
     * Method to handle PackageErrorException.
     */
    @ExceptionHandler(value = PackageErrorException.class)
    public ResponseEntity<Object> handleContributionErrorException(
            PackageErrorException ex) {

        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());

    }



    /**
     * Method to handle NotFoundException.
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleUnexpectedTypeException(
            NotFoundException ex) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());

    }



    /**
     * Method to handle MethodArgumentNotValidException.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        List<String> errors = new ArrayList<String>();

        for (ObjectError violation : ex.getFieldErrors()) {
            errors.add(violation.getCode());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                this.fieldError(ex.getAllErrors()), errors);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());
    }



    private String fieldError(List<ObjectError> list) {
        return list.stream().map(d -> d.getDefaultMessage())
                .collect(Collectors.joining(" ; "));
    }

}
