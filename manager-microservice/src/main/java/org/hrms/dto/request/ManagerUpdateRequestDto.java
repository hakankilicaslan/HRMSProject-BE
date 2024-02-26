package org.hrms.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerUpdateRequestDto {

    private Long id;

    /*
     * @Size anotasyonu da bir kısıtlama anotasyonudur ve kullanıcının girdiği name değişkeninin minimum 3 maksimum 40 karakterde olmasını sağlar.
     * message = "Name must be between 3 and 40 characters." diyerekte karakter sayısı tutmadığında bu mesajı içeren bir doğrulama hatası gönderir.
     */
    @Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters.")
    private String name;

    @Size(min = 3, max = 40, message = "Surname must be between 3 and 40 characters.")
    private String surname;

    //@Size kısmında hem min hem max 11 diyerek kimlik numarasının 11 karakter olması ve @Pattern kısmında regexp içinde de sadece rakam girilmesi şartını koyuyoruz.
    @Size(min = 11, max = 11, message = "Phone number must be 11 characters.")
    @Pattern(regexp = "[0-9]+", message = "Phone number must contain only digits.")
    private String phoneNumber;

    @Size(min = 11, max = 11, message = "Identity number must be 11 characters.")
    @Pattern(regexp = "[0-9]+", message = "Identity number must contain only digits.")
    private String identityNumber;

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
    @Pattern(message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,32}$")
    private String password;

    @Size(min = 3, max = 100, message = "Address must be between 3 and 100 characters.")
    private String address;

    @Size(min = 3, max = 40, message = "Company name must be between 3 and 40 characters.")
    private String companyName;

    @Size(min = 3, max = 40, message = "Title must be between 3 and 40 characters.")
    private String title;

    private String photo;

    private Double salary;

    private EGender gender;

    /*
     * @Temporal, JPA tarafından sağlanan ve veritabanında bir tarih/saat alanını nasıl saklayacağını belirttiğimiz bir anotasyondur.
     * Değişkenimizin türü bir LocalDate yani DATE olduğu için ve saat bilgisi içermediği için TemporalType.DATE olarak işaretliyoruz.
     */
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

}
