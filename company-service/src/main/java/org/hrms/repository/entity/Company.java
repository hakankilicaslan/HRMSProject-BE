package org.hrms.repository.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hrms.repository.enums.EStatus;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.List;

/*
 * @EqualsAndHashCode sınıfın üst sınıfının(superclass) alanlarını da dahil ederek equals() ve hashCode() metotlarını otomatik olarak oluşturan bir anotasyondur.
 * callSuper = true parametresi, üst sınıfın alanlarını(değişkenlerini) da dahil etmek için kullanılır. Üst sınıftan data çekerken @Data hata vermesin diye bu anotasyonu kullanıyoruz.
 *
 * @NoArgsConstructor anotasyonu sınıflar için default bir boş constructor oluşturur. Sınıfın hiçbir argüman(parametre) almayan bir constructor'a sahip olmasını sağlar.
 * Bu sayede, sınıfın bir nesnesi oluşturulurken bu constructor kullanılabilir. @NoArgsConstructor Java derleyicisi tarafından otomatik olarak default bir constructor oluşturulur.
 * Ancak, başka bir contructor tanımlanmışsa, Java derleyicisi otomatik olarak default bir constructor oluşturmaz. Bu anotasyon özellikle JPA varlıkları veya DTO(Data Transfer Object) gibi sınıflar için faydalıdır.
 *
 * @AllArgsConstructor anotasyonu sınıfın tüm değişkenlerini(field) içeren bir constructor oluşturur yani tüm alanları parametre olarak alan bir constructor oluşturarak sınıfın dışından bu alanları başlatmanızı sağlar.
 *
 * @Data anotasyonu sınıf için getter, setter, equals(), hashCode() ve toString() metotlarını otomatik olarak oluşturur. Sınıf içindeki tüm değişkenler(field) için getter ve setter metotlarını oluşturur.

 * @SuperBuilder anotasyonu, builder desenini kullanarak nesne oluşturmayı sağlar ve aynı zamanda üst sınıflardan(superclass) kalıtılan değişkenleri de dahil etmemizi sağlar.
 * Bu sayede alt sınıfın(subclass) builder'ı üst sınıfın(superclass) builder'ı gibi davranır ve oradaki değişkenlere de erişebilir ve kullanabilir.
 * Hem miras alınan hem de miras alan sınıflar @SuperBuilder olarak işaretlenmelidir. Admin sınıfı BaseEntity'den miras aldığı için @Builder yerine @SuperBuilder anotasyonunu kullanmalıyız.
 *
 * @Document anotasyonu, MongoDB'deki belirli bir sınıfın bir belgeye karşılık geldiğini belirtmek için kullanılan bir Spring Data MongoDB anotasyonudur.
 * MongoDB, belgeleri JSON benzeri bir formatta saklayan bir NoSQL veritabanıdır. Sadece gönderilen değişkenleri içinde döküman olarak tutar.
 * PostgreDB kullanırken veritabanına kaydedilmeyen değişkenler null olarak atanırdı ama MongoDB'de döküman olarak tutulduğu için sadece gelen değişkenleri saklıyor.
 * (collection = "tbl_company") diyerekte collection olarak verdiğimiz tbl_company adında bir koleksiyonda saklanacağını belirtiyoruz.
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document(collection = "tbl_company")
public class Company extends BaseEntity{

    //@MongoId, MongoDB ile çalışırken id alanının belgeyi tanımlayan birincil anahtar olduğunu belirtir ve MongoDB'de belgelerin benzersiz bir String kimlikle tutulmasını sağlar.
    @MongoId
    private String id;

    @Indexed(unique = true)
    private Long managerId;

    /*
     * @Indexed: Belirli alanların dizinlenmesini sağlayan ve veritabanı işlemlerinin daha hızlı ve verimli olmasını sağlayan bir Spring Data MongoDB özelliğidir.
     * unique = true diyerek girilen değerin eşsiz olmasını sağlıyoruz ve veritabanında o değer önceden girilmişse tekrar kullanılmamasını sağlıyoruz.
     * @NotNull: Alanın değer girilmeden null olarak atanamasına izin vermiyoruz.
     * @Column: Alanın veritabanında bir sütuna karşılık geldiğini belirtir. Bu koşulları burada entity sınıfında da yaparak veritabanına manuel eklerken de bu koşulları sağlamasını istiyoruz.
     * @Size: Alanın uzunluğunu belirtiyoruz ve min=3 ve max=40 diyerek en az 3 en fazla 40 karakterden oluşmasını sağlıyoruz.
     */
    @Indexed(unique = true)
    @NotNull
    @Size(min = 3, max = 40)
    private String companyName;

    @Indexed(unique = true)
    @NotNull
    @Length(min = 11, max = 11) //@Length anotasyonu için hem min hem max olarak 11 vererek bu alanın sadece 11 karakterden oluşmasını sağlıyoruz.
    private String companyPhoneNumber;

    @Indexed(unique = true)
    @NotNull
    @Size(min = 3, max = 40)
    private String infoEmail;

    @NotNull
    @Size(min = 3, max = 100)
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

    //@Builder.Default: Alanın default olarak nasıl atanacağını belirtiyoruz. EStatus.ACTIVE diyerek statüsü belirtilmediyse ACTIVE olarak atanmasını sağlıyoruz.
    @Builder.Default
    private EStatus status = EStatus.ACTIVE;

}
