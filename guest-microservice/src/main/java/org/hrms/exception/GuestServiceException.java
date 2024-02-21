package org.hrms.exception;

import lombok.Getter;

/*
 * Getter metotlarını otomatik oluşturmak için @Getter anotasyonunu kullanıyoruz.
 * GuestServiceException sınıfımızı microservice ayağa kalktığında oluşabilecek hataları yakalaması için kullanacağız.
 * Sınıfımızı RuntimeException sınıfından extend ediyoruz ve bu şekilde microservice çalışırken oluşabilecek hataları yakalayacağız.
 */
@Getter
public class GuestServiceException extends RuntimeException {

    private final ErrorType errorType;

    public GuestServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public GuestServiceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

}
