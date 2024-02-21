package org.hrms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/*
 * @NoArgsConstructor anotasyonu sınıflar için default bir boş constructor oluşturur. Sınıfın hiçbir argüman(parametre) almayan bir constructor'a sahip olmasını sağlar.
 * Bu sayede, sınıfın bir nesnesi oluşturulurken bu constructor kullanılabilir. @NoArgsConstructor Java derleyicisi tarafından otomatik olarak default bir constructor oluşturulur.
 * Ancak, başka bir contructor tanımlanmışsa, Java derleyicisi otomatik olarak default bir constructor oluşturmaz. Bu anotasyon özellikle JPA varlıkları veya DTO (Data Transfer Object) gibi sınıflar için faydalıdır.
 *
 * @AllArgsConstructor anotasyonu sınıfın tüm değişkenlerini(field) içeren bir constructor oluşturur yani tüm alanları parametre olarak alan bir constructor oluşturarak sınıfın dışından bu alanları başlatmanızı sağlar.
 *
 * @Getter anotasyonunu Getter metotlarını otomatik oluşturmak için kullanıyoruz.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorType {

    PASSWORD_MISMATCH(1001,"The entered passwords did not match...", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1002,"This email address has already been registered...",HttpStatus.BAD_REQUEST),
    EMAIL_OR_PASSWORD_NOT_EXISTS(1003, "Email or password incorrect...",HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_ALREADY_EXISTS(1004,"This phone number has already been registered...",HttpStatus.BAD_REQUEST),
    IDENTITY_NUMBER_ALREADY_EXISTS(1005,"This identity number has already been registered...",HttpStatus.BAD_REQUEST),
    USER_ALREADY_DELETED(1006,"This account has already been deleted...",HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_MISMATCH(1007,"Activation code incorrect...",HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE(1008,"Account is not active" ,HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_ACTIVE(1009,"Account is already active..." ,HttpStatus.BAD_REQUEST),
    EMAIL_OR_PHONE_ALREADY_EXISTS(1010,"This email address or phone number has already been registered...",HttpStatus.BAD_REQUEST),
    PARAMETER_ALREADY_EXISTS(1011,"This email address, phone number or identity number has already been registered...",HttpStatus.BAD_REQUEST),

    COMPANY_NAME_NOT_FOUND(4001,"Company name not found..." ,HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4004,"User not found..." ,HttpStatus.BAD_REQUEST),

    INTERNAL_SERVER_ERROR(5001,"Internal Server Error...",HttpStatus.INTERNAL_SERVER_ERROR),

    PARAMETER_NOT_VALID(6001,"Parameter incorrect...",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(6002,"Invalid Token..." , HttpStatus.BAD_REQUEST),
    INVALID_TOKEN_FORMAT(6003,"Invalid token format...",HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(6004,"Token could not be created...",HttpStatus.BAD_REQUEST),
    INVALID_ROLE(6005,"Invalid Role: Password could not update in other database..." , HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;

}
