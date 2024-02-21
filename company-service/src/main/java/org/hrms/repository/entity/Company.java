package org.hrms.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
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

    private Long authId;
    private Long employeeId;
    private Long companyId;
    private String name;
    List<Double> salaries;
    List<String> employees;
    List<String> managers;
    List<Long> shifts;
    List<Long> holidays;

}
