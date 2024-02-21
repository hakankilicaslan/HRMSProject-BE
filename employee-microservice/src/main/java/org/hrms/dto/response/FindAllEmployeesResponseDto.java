package org.hrms.dto.response;

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
public class FindAllEmployeesResponseDto {
    private Long id;
    private Long authId;
    private Long companyId;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String identityNumber;
    private String address;
    private String companyName;
    private String title;
    private String photo;
    private Double salary;
    private LocalDate dateOfBirth;
    private EGender gender;
}
