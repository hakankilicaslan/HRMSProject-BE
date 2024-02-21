package org.hrms.exception;

import lombok.Getter;

/*
 * Getter metotlarını otomatik oluşturmak için @Getter anotasyonunu kullanıyoruz.
 * AdminServiceException sınıfımızı microservice ayağa kalktığında oluşabilecek hataları yakalaması için kullanacağız.
 * Sınıfımızı RuntimeException sınıfından extend ediyoruz ve bu şekilde microservice çalışırken oluşabilecek hataları yakalayacağız.
 */
@Getter
public class AdminServiceException extends RuntimeException {

    private final ErrorType errorType;

    public AdminServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public AdminServiceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

}
