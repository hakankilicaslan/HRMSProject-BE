package org.hrms.exception;

import lombok.Getter;

/*
 * Getter metotlarını otomatik oluşturmak için @Getter anotasyonunu kullanıyoruz.
 * ManagerServiceException sınıfımızı microservice ayağa kalktığında oluşabilecek hataları yakalaması için kullanacağız.
 * Sınıfımızı RuntimeException sınıfından extend ediyoruz ve bu şekilde microservice çalışırken oluşabilecek hataları yakalayacağız.
 */
@Getter
public class ManagerServiceException extends RuntimeException {

    private final ErrorType errorType;

    public ManagerServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ManagerServiceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

}
