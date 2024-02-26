package org.hrms.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EGender;
import org.hrms.repository.enums.ERole;
import org.hrms.repository.enums.EStatus;

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
public class EmployeeCreateRequestDto {

    /*
     * @NotNull, @NotEmpty ve @NotBlank Java için Bean Validation API'si tarafından sağlanan bir kısıtlama(constraint) anotasyonudur.
     * @NotNull, bir alanın null olmadığını kontrol eder ancak içeriğin boş olup olmadığını kontrol etmez.
     * @NotEmpty, bir alanın null olmadığını ve içeriğinin boş olmadığını kontrol eder. En az bir karakter olmalıdır ama bu karakter boşluk bile olabilir.
     * @NotBlank, bir alanın null olmadığını, içeriğinin boş olmadığını ve içeriğin sadece boşluklardan oluşmadığını kontrol eder.
     */

    /*
     * @Size anotasyonu da bir kısıtlama anotasyonudur ve kullanıcının girdiği name değişkeninin minimum 3 maksimum 40 karakterde olmasını sağlar.
     * message = "Name must be between 3 and 40 characters." diyerekte karakter sayısı tutmadığında bu mesajı içeren bir doğrulama hatası gönderir.
     */
    @NotBlank(message = "Name field cannot be blank.")
    @Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters.")
    private String name;

    @NotBlank(message = "Surname field cannot be blank.")
    @Size(min = 3, max = 40, message = "Surname must be between 3 and 40 characters.")
    private String surname;

    //@Size kısmında hem min hem max 11 diyerek kimlik numarasının 11 karakter olması ve @Pattern kısmında regexp içinde de sadece rakam girilmesi şartını koyuyoruz.
    @NotNull(message = "Phone number field cannot be null." )
    @Size(min = 11, max = 11, message = "Phone number must be 11 characters.")
    @Pattern(regexp = "[0-9]+", message = "Phone number must contain only digits.")
    private String phoneNumber;

    @NotNull(message = "Identity number field cannot be null.")
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

    @Email(message = "Please enter a valid email address.")
    private String personalEmail;

    @NotBlank(message = "Address field cannot be blank.")
    @Size(min = 3, max = 100, message = "Address must be between 3 and 100 characters.")
    private String address;

    @NotBlank(message = "Company name field cannot be blank.")
    @Size(min = 3, max = 40, message = "Company name must be between 3 and 40 characters.")
    private String companyName;

    @NotBlank(message = "Title field cannot be blank.")
    @Size(min = 3, max = 40, message = "Title must be between 3 and 40 characters.")
    private String title;

    @NotBlank(message = "Salary field cannot be blank.")
    private Double salary;

    @NotBlank(message = "Photo field cannot be blank.")
    private String photo;

    @NotNull(message = "Status field cannot be null.")
    @Builder.Default //@Builder.Default: Alanın default olarak nasıl atanacağını belirtiyoruz. EStatus.ACTIVE diyerek statüsü belirtilmediyse ACTIVE olarak atanmasını sağlıyoruz.
    private EStatus status = EStatus.ACTIVE;

    @NotNull(message = "Gender field cannot be null.") //@NotEmpty anotasyonu ekleyince hata verdi ondan dolayı @NotNull yaptım.
    private EGender gender;

    /*
     * @Temporal, JPA tarafından sağlanan ve veritabanında bir tarih/saat alanını nasıl saklayacağını belirttiğimiz bir anotasyondur.
     * Değişkenimizin türü bir LocalDate yani DATE olduğu için ve saat bilgisi içermediği için TemporalType.DATE olarak işaretliyoruz.
     */
    @NotNull(message = "Date of birth field cannot be null.") //@NotEmpty anotasyonu ekleyince hata verdi ondan dolayı @NotNull yaptım.
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

}
