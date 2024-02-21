package org.hrms.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.*;
import java.time.LocalDate;

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
 * Hem miras alınan hem de miras alan sınıflar @SuperBuilder olarak işaretlenmelidir. Employee sınıfı BaseEntity'den miras aldığı için @Builder yerine @SuperBuilder anotasyonunu kullanmalıyız.
 *
 * @Table anotasyonu JPA (Java Persistence API) entegrasyonu ile kullanılan bir Java anotasyonudur ve postgre veritabanı tablolarıyla eşleştirmek için kullanılır ve sınıfın hangi veritabanı tablosuna karşılık geldiğini belirtir.
 * name = "tbl_employee" diyerek veritabanında tbl_employee adında bir tablo oluşturur ve sınıfın verilerini o tabloya kaydeder ve bu tablodan veri çeker.
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@SuperBuilder
@Table(name = "tbl_employee")
public class Employee extends BaseEntity{

    //Bu iki anotasyonu kullanarak kullanıcıların veritabanı tarafından yönetilen otomatik artan eşsiz bir kimliğe sahip olmasını sağlıyoruz.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long authId;

    @Column(unique = true)
    private Long companyId;

    private String name;
    private String surname;

    @Column(unique = true) //Bu anotasyonu kullanarak ve true işaretleyerek emailin eşsiz olmasını başkası tarafından alınamamasını sağlıyoruz. Bu kontrolü entity sınıfında yaparak veritabanına manuel eklerken de bu koşulu sağlamasını istiyoruz.
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String identityNumber;

    private String password;
    private String address;
    private String companyName;
    private String title;
    private String photo;
    private Double salary;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING) //Bu anotasyonu kullanarak enum sabitlerinin veritabanında String olarak tutulmasını sağlıyoruz.
    @Builder.Default //@Builder.Default anotasyonu kullanarak eğer statüsü belirtilmediyse default olarak aşağıda belirttiğimiz gibi EMPLOYEE olarak atanmasını sağlıyoruz.
    private ERole role = ERole.EMPLOYEE;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Enumerated(EnumType.STRING)
    private EGender gender;

}
