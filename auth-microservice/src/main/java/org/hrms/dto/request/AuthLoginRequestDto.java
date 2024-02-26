package org.hrms.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AuthLoginRequestDto {

    /*
     * @NotNull, @NotEmpty ve @NotBlank Java için Bean Validation API'si tarafından sağlanan bir kısıtlama(constraint) anotasyonudur.
     * @NotNull, bir alanın null olmadığını kontrol eder ancak içeriğin boş olup olmadığını kontrol etmez.
     * @NotEmpty, bir alanın null olmadığını ve içeriğinin boş olmadığını kontrol eder. En az bir karakter olmalıdır ama bu karakter boşluk bile olabilir.
     * @NotBlank, bir alanın null olmadığını, içeriğinin boş olmadığını ve içeriğin sadece boşluklardan oluşmadığını kontrol eder.
     */

    /*
     * @Email anotasyonu bir kısıtlama anotasyonudur ve kullanıcının girdiği e-posta adresinin doğru formatını kontrol etmek için kullanılır.
     * message = "Please enter a valid email address." diyerekte geçerli bir e-posta adresi girilmezse bu mesajı içeren bir doğrulama hatası gönderir.
     */
    @Email(message = "Please enter a valid email address.")
    @Size(min = 3, max = 40, message = "Email must be between 3 and 40 characters.")
    private String email;

    /*
     * @Pattern anotasyonu bir kısıtlama anotasyonudur ve gerekli alanın regexp içinde verilen kurala göre doldurulmasını sağlar.
     * Bu kural şifrenin en az 8 en fazla 32 karakter olması ve en az bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter içermesini zorunlu kılar.
     * Bu kural sağlanmadığında ise message kısmındaki yazıyı içeren bir doğrulama hatası gönderir.
     */
    @NotBlank(message = "Password field cannot be blank.")
    @Pattern(message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,32}$")
    private String password;

}