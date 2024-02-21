package org.hrms.exception;

import lombok.Getter;

/*
 * Getter metotlarını otomatik oluşturmak için @Getter anotasyonunu kullanıyoruz.
 * AuthServiceException sınıfımızı microservice ayağa kalktığında oluşabilecek hataları yakalaması için kullanacağız.
 * Sınıfımızı RuntimeException sınıfından extend ediyoruz ve bu şekilde microservice çalışırken oluşabilecek hataları yakalayacağız.
 */
@Getter
public class AuthServiceException extends RuntimeException {

    private final ErrorType errorType;

    public AuthServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public AuthServiceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

}
