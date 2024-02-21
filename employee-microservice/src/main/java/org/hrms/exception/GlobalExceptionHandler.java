package org.hrms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/*
 * @ControllerAdvice anotasyonu, Spring MVC tabanlı uygulamalarda global seviyede hata işleme ve genel kontrol sağlamak için kullanılan bir anotasyondur.
 * Bu anotasyon genellikle global bir hatayı yakalamak, belirli hata türlerine özel işlemler eklemek veya istisna durumları için özel bir dönüş sağlamak gibi senaryolarda kullanılır.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * @ExceptionHandler anotasyonu, Spring MVC tabanlı uygulamalarda belirli bir controller sınıfı içinde belirli türde istisna durumlarını ele almak için kullanılır.
     * Bu anotasyon, bir yöntemi belirli bir istisna türünü işlemek için işaretler. Parametre olarak verdiğimiz exception türünde bir hata olduğunda otomatik olarak çağrılır.
     *
     * @ResponseBody anotasyonu, Spring MVC tabanlı uygulamalarda, bir controller yönteminin dönüş değerini doğrudan HTTP yanıt gövdesine (response body) dönüştürmek için kullanılır.
     * @ExceptionHandler metotlarında @ResponseBody anotasyonu kullanılmasının sebebi ise, hatanın ayrıntılarının istemciye geri dönmesini sağlamaktır.
     * Bir istisna durumu oluştuğunda, genellikle bu istisna durumunun ayrıntılarını içeren bir hata mesajı veya nesnesi döndürmek istenir.
     * Bu durumda, @ResponseBody anotasyonu ile yöntemin dönüş değeri olarak istisna durumu ile ilgili bilgiler dönülebilir.
     */
    @ExceptionHandler(EmployeeServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleAuthServiceException(EmployeeServiceException ex){
        HttpStatus httpStatus=ex.getErrorType().getHttpStatus();
        ErrorType errorType=ex.getErrorType();
        ErrorMessage errorMessage=createError(errorType,ex);
        errorMessage.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage,httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorType errorType = ErrorType.PARAMETER_NOT_VALID;
        List<String> fields = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e-> fields.add(e.getField()+": " + e.getDefaultMessage()));
        ErrorMessage errorMessage=createError(errorType,ex);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(errorMessage,errorType.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleAllExceptions(Exception ex){
        ErrorType errorType = ErrorType.INTERNAL_SERVER_ERROR;
        ErrorMessage errorMessage=createError(errorType,ex);
        errorMessage.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage,errorType.getHttpStatus());
    }

    private ErrorMessage createError(ErrorType errorType, Exception e){
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

}
