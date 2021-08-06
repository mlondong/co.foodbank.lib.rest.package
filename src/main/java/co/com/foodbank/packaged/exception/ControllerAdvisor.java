package co.com.foodbank.packaged.exception;

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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import co.com.foodbank.stock.sdk.exception.SDKStockNotFoundException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceNotAvailableException;


/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.exception
 *         11/07/2021
 */
@ControllerAdvice
public class ControllerAdvisor {


    /**
     * Method to handle SDKStockServiceException
     */
    @ExceptionHandler(value = SDKStockServiceException.class)
    public ResponseEntity<Object> handleSDKStockServiceException(
            SDKStockServiceException ex) {

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());
    }



    /**
     * Method to handle NullPointerException
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(
            NullPointerException ex) {


        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());
    }



    /**
     * Method to handle SDKStockNotFoundException
     */
    @ExceptionHandler(value = SDKStockNotFoundException.class)
    public ResponseEntity<Object> handleSDKStockNotFoundException(
            SDKStockNotFoundException ex) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());
    }


    /**
     * Method to handle BindException
     */
    @ExceptionHandler(
            value = org.springframework.validation.BindException.class)
    public ResponseEntity<Object> handleBindException(
            org.springframework.validation.BindException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());
    }



    /**
     * Method to handle SDKStockServiceNotAvailableException when the service
     * Stock is off.
     */
    @ExceptionHandler(value = SDKStockServiceNotAvailableException.class)
    public ResponseEntity<Object> handleSDKStockServiceNotAvailableException(
            SDKStockServiceNotAvailableException ex) {

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),
                apiError.getStatus());
    }


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
     * Method to handle MethodArgumentTypeMismatchException.
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {

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
