package org.hrms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class CompanyUpdateRequestDto {

    private String id;

    /*
     * @Size anotasyonu da bir kısıtlama anotasyonudur ve kullanıcının girdiği name değişkeninin minimum 3 maksimum 40 karakterde olmasını sağlar.
     * message = "Name must be between 3 and 40 characters." diyerekte karakter sayısı tutmadığında bu mesajı içeren bir doğrulama hatası gönderir.
     */
    @Size(min = 3, max = 40, message = "Company must be between 3 and 40 characters.")
    private String companyName;

    //@Size kısmında hem min hem max 11 diyerek telefon numarasının 11 karakter olması ve @Pattern kısmında regexp içinde de sadece rakam girilmesi şartını koyuyoruz.
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

    @Size(min = 3, max = 100, message = "Company address must be between 3 and 100 characters.")
    private String companyAddress;

    private String about;
    private String logo;
    private Long revenue;
    private Long expense;
    private Long profit;
    private Long loss;
    private Long netIncome;
    private List<Double> salaries;
    private List<Long> employees;
    private List<Long> shifts;
    private List<Long> holidays;

}
