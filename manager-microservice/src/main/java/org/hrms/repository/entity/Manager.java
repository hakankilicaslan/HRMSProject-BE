package org.hrms.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.*;
import java.time.LocalDate;
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
 *
 * @Entity, JPA(Java Persistence API) standardını destekleyen ORM(Object-Relational Mapping) framework'leriyle kullanılan bir Java anotasyonudur.
 * Bu anotasyon, bir sınıfın bir ilişkisel veritabanı tablosunu temsil ettiğini belirtir. JPA, Java sınıflarını ilişkisel veritabanı tablolarıyla eşleştirmek için kullanılan bir API'dir.
 * @Entity anotasyonu, JPA entegrasyonu ile birlikte kullanılarak, Java sınıflarının veritabanı tablolarını temsil etmesini sağlar.
 * Sınıf içine yazılan bütün değişkenler(field) veritabanı tablosunun sütunlarına karşılık gelir.
 *
 * @SuperBuilder anotasyonu, builder desenini kullanarak nesne oluşturmayı sağlar ve aynı zamanda üst sınıflardan(superclass) kalıtılan değişkenleri de dahil etmemizi sağlar.
 * Bu sayede alt sınıfın(subclass) builder'ı üst sınıfın(superclass) builder'ı gibi davranır ve oradaki değişkenlere de erişebilir ve kullanabilir.
 * Hem miras alınan hem de miras alan sınıflar @SuperBuilder olarak işaretlenmelidir. Manager sınıfı BaseEntity'den miras aldığı için @Builder yerine @SuperBuilder anotasyonunu kullanmalıyız.
 *
 * @Table anotasyonu JPA (Java Persistence API) entegrasyonu ile kullanılan bir Java anotasyonudur ve postgre veritabanı tablolarıyla eşleştirmek için kullanılır ve sınıfın hangi veritabanı tablosuna karşılık geldiğini belirtir.
 * name = "tbl_manager" diyerek veritabanında tbl_manager adında bir tablo oluşturur ve sınıfın verilerini o tabloya kaydeder ve bu tablodan veri çeker.
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@SuperBuilder
@Table(name = "tbl_manager")
public class Manager extends BaseEntity {

    //Bu iki anotasyonu kullanarak kullanıcıların veritabanı tarafından yönetilen otomatik artan eşsiz bir kimliğe sahip olmasını sağlıyoruz.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long authId;

    @Column(unique = true)
    private String companyId;

    /*
     * @Column: Alanın veritabanında bir sütuna karşılık geldiğini belirtir. Bu koşulları burada entity sınıfında da yaparak veritabanına manuel eklerken de bu koşulları sağlamasını istiyoruz.
     * unique = true diyerek girilen değerin eşsiz olmasını sağlıyoruz ve veritabanında o değer önceden girilmişse tekrar kullanılmamasını sağlıyoruz.
     * nullable = false diyerek bir değer girilmeden null olarak atanamsına izin vermiyoruz.
     * length = 40 diyerek girilen değerin maksimum 40 karakterden oluşmasını sağlıyoruz.
     */
    @Column(unique = true, nullable = false, length = 40)
    private String email;

    @Column(unique = true, nullable = false, length = 11)
    private String phoneNumber;

    @Column(unique = true, nullable = false, length = 11)
    private String identityNumber;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 40)
    private String surname;

    @Column(nullable = false, length = 32)
    private String password;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 40)
    private String companyName;

    @Column(length = 40)
    private String title;

    private Double salary;

    private String photo;

    private LocalDate dateOfBirth;

    /*
     * @ElementCollection anotasyonu, List gibi birden fazla eleman içeren değişkenler için kullanılır.
     * Bu anotasyon ile işaretlenen değişkenler ayrı bir ilişkisel tabloya dönüştürülür.
     * Bu tablo, entity sınıfının tablosuyla ilişkilendirilir ve bu koleksiyon alanlarının verilerini depolar.
     * Bu şekilde şirketler listesi tbl_manager'a bağlı farklı bir tablo olarak liste içindeki şirketleri depolar.
     */
    @ElementCollection
    List<Long> companiesList;

    @ElementCollection
    List<Long> employeesList;

    /*
     * @Enumerated: Alanın bir enum tipine karşılık geldiğini belirtir ve (EnumType.STRING) veritabanında String olarak tutulmasını sağlıyoruz.
     * @Builder.Default: Alanın default olarak nasıl atanacağını belirtiyoruz. EStatus.PENDING diyerek statüsü belirtilmediyse PENDING olarak atanmasını sağlıyoruz.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EStatus status = EStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ERole role = ERole.MANAGER;

    @Enumerated(EnumType.STRING)
    private EGender gender;

}
