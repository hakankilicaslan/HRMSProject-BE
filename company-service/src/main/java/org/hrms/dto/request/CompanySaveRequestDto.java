package org.hrms.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.index.Indexed;

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
public class CompanySaveRequestDto {

    /*
     * @NotNull, @NotEmpty ve @NotBlank Java için Bean Validation API'si tarafından sağlanan bir kısıtlama(constraint) anotasyonudur.
     * @NotNull, bir alanın null olmadığını kontrol eder ancak içeriğin boş olup olmadığını kontrol etmez.
     * @NotEmpty, bir alanın null olmadığını ve içeriğinin boş olmadığını kontrol eder. En az bir karakter olmalıdır ama bu karakter boşluk bile olabilir.
     * @NotBlank, bir alanın null olmadığını, içeriğinin boş olmadığını ve içeriğin sadece boşluklardan oluşmadığını kontrol eder.
     */

    /*
     * @Size anotasyonu da bir kısıtlama anotasyonudur ve kullanıcının girdiği company name değişkeninin minimum 3 maksimum 40 karakterde olmasını sağlar.
     * message = "Company name must be between 3 and 40 characters." diyerekte karakter sayısı tutmadığında bu mesajı içeren bir doğrulama hatası gönderir.
     */
    @NotBlank(message = "Company name field cannot be blank.")
    @Size(min = 3, max = 40, message = "Company name must be between 3 and 40 characters.")
    private String companyName;

    //@Size kısmında hem min hem max 11 diyerek telefon numarasının 11 karakter olması ve @Pattern kısmında regexp içinde de sadece rakam girilmesi şartını koyuyoruz.
    @NotNull(message = "Phone number field cannot be null." )
    @Size(min = 11, max = 11, message = "Phone number must be 11 characters.")
    @Pattern(regexp = "[0-9]+", message = "Phone number must contain only digits.")
    private String companyPhoneNumber;

    /*
     * @Email anotasyonu bir kısıtlama anotasyonudur ve kullanıcının girdiği e-posta adresinin doğru formatını kontrol etmek için kullanılır.
     * message = "Please enter a valid email address." diyerekte geçerli bir e-posta adresi girilmezse bu mesajı içeren bir doğrulama hatası gönderir.
     */
    @Email(message = "Please enter a valid email address.")
    @Size(min = 3, max = 40, message = "Email must be between 3 and 40 characters.")
    private String infoEmail;

    @NotBlank(message = "Company address field cannot be blank.")
    @Size(min = 3, max = 100, message = "Company address must be between 3 and 100 characters.")
    private String companyAddress;

    private String about;

    private String logo;
}
