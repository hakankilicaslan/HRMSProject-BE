package org.hrms.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EGender;

import java.time.LocalDate;

/*
 * @NoArgsConstructor anotasyonu sınıflar için default bir boş constructor oluşturur. Sınıfın hiçbir argüman(parametre) almayan bir constructor'a sahip olmasını sağlar.
 * Bu sayede, sınıfın bir nesnesi oluşturulurken bu constructor kullanılabilir. @NoArgsConstructor Java derleyicisi tarafından otomatik olarak default bir constructor oluşturulur.
 * Ancak, başka bir contructor tanımlanmışsa, Java derleyicisi otomatik olarak default bir constructor oluşturmaz. Bu anotasyon özellikle JPA varlıkları veya DTO (Data Transfer Object) gibi sınıflar için faydalıdır.
 *
 * @AllArgsConstructor anotasyonu sınıfın tüm değişkenlerini(field) içeren bir constructor oluşturur yani tüm alanları parametre olarak alan bir constructor oluşturarak sınıfın dışından bu alanları başlatmanızı sağlar.
 *
 * @Data anotasyonu sınıf için getter, setter, equals(), hashCode() ve toString() metotlarını otomatik olarak oluşturur. Sınıf içindeki tüm değişkenler(field) için getter ve setter metotlarını oluşturur.
 *
 * @Builder anotasyonu builder desenini kullanarak nesne oluşturmayı sağlar. Java'da builder deseni, genellikle iç içe geçmiş setter çağrılarını ve uzun kurucu metotları önlemek için kullanılır.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRegisterRequestDto {

    /*
     * @NotEmpty, Java için Bean Validation API'si tarafından sağlanan bir kısıtlama(constraint) anotasyonudur ve gerekli alanın boş geçilemeyeceğini belirtir.
     * @NotEmpty kullanımında gerekli alan null olamaz ve boş bırakılamaz yani en az 1 karakter girilmelidir ama bu karakter boşluk bile olabilir.
     *
     * @Size anotasyonu da @NotEmpty gibi bir kısıtlama anotasyonudur ve kullanıcının girdiği name değişkeninin minimum 3 maksimum 20 karakterde olmasını sağlar.
     * message = "Name must be between 3 and 20 characters." diyerekte karakter sayısı tutmadığında bu mesajı içeren bir doğrulama hatası gönderir.
     */
    @NotEmpty(message = "Name field cannot be empty.")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
    private String name;

    @NotEmpty(message = "Surname field cannot be empty")
    @Size(min = 3, max = 20, message = "Surname must be between 3 and 20 characters.")
    private String surname;

    //@Size kısmında hem min hem max 11 diyerek kimlik numarasının 11 karakter olması ve @Pattern kısmında regexp içinde de sadece rakam girilmesi şartını koyuyoruz.
    @NotEmpty(message = "Phone field cannot be empty." )
    @Size(min = 11, max = 11, message = "Phone number must be 11 characters.")
    @Pattern(regexp = "[0-9]+", message = "Phone number must contain only digits.")
    private String phoneNumber;

    @NotEmpty(message = "Identity number field cannot be empty.")
    @Size(min = 11, max = 11, message = "Identity number must be 11 characters.")
    @Pattern(regexp = "[0-9]+", message = "Identity number must contain only digits.")
    private String identityNumber;

    @NotEmpty(message = "Address field cannot be empty.")
    private String address;

    //@NotEmpty(message = "Date of birth field cannot be empty.")
    @NotNull(message = "Date of birth field cannot be empty.")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Company name field cannot be empty.")
    private String companyName;

    //@NotEmpty(message = "Gender field cannot be empty.") //İstek atarken hata verdi yoruma alınca düzeliyor. MALE yazılı olmasına rağmen boş geçilemez hatası veriyor.
    private EGender gender;

    /*
     * @Email anotasyonu bir kısıtlama anotasyonudur ve kullanıcının girdiği e-posta adresinin doğru formatını kontrol etmek için kullanılır.
     * message = "Please enter a valid email address." diyerekte geçerli bir e-posta adresi girilmezse bu mesajı içeren bir doğrulama hatası gönderir.
     */
    @Email(message = "Please enter a valid email address.")
    private String email;

    /*
     * @NotBlank anotasyonu bir kısıtlama anotasyonudur ve gerekli alanın boş geçilemeyeceğini belirtir.
     * @NotBlank kullanımında gerekli alan null olamaz ve boş bırakılamaz ayrıca sadece boşluk kabul etmez yani çift tırnakta kabul etmiyor.
     *
     * @Pattern anotasyonu bir kısıtlama anotasyonudur ve gerekli alanın regexp içinde verilen kurala göre doldurulmasını sağlar.
     * Bu kural şifrenin en az 8 en fazla 32 karakter olması ve en az bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter içermesini zorunlu kılar.
     * Bu kural sağlanmadığında ise message kısmındaki yazıyı içeren bir doğrulama hatası gönderir.
     */
    @NotBlank(message = "Password cannot be empty.")
    @Pattern(message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,32}$")
    private String password;

    private String rePassword;

}
