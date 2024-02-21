package org.hrms.repository.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/*
 * @NoArgsConstructor anotasyonu sınıflar için default bir boş constructor oluşturur. Sınıfın hiçbir argüman(parametre) almayan bir constructor'a sahip olmasını sağlar.
 * Bu sayede, sınıfın bir nesnesi oluşturulurken bu constructor kullanılabilir. @NoArgsConstructor Java derleyicisi tarafından otomatik olarak default bir constructor oluşturulur.
 * Ancak, başka bir contructor tanımlanmışsa, Java derleyicisi otomatik olarak default bir constructor oluşturmaz. Bu anotasyon özellikle JPA varlıkları veya DTO (Data Transfer Object) gibi sınıflar için faydalıdır.
 *
 * @AllArgsConstructor anotasyonu sınıfın tüm değişkenlerini(field) içeren bir constructor oluşturur yani tüm alanları parametre olarak alan bir constructor oluşturarak sınıfın dışından bu alanları başlatmanızı sağlar.
 *
 * @Data anotasyonu sınıf için getter, setter, equals(), hashCode() ve toString() metotlarını otomatik olarak oluşturur. Sınıf içindeki tüm değişkenler(field) için getter ve setter metotlarını oluşturur.
 *
 * @SuperBuilder anotasyonu, builder desenini kullanarak nesne oluşturmayı sağlar ve aynı zamanda üst sınıflardan(superclass) kalıtılan değişkenleri de dahil etmemizi sağlar.
 * Bu sayede alt sınıfın(subclass) builder'ı üst sınıfın(superclass) builder'ı gibi davranır ve oradaki değişkenlere de erişebilir ve kullanabilir.
 * Hem miras alınan hem de miras alan sınıflar @SuperBuilder olarak işaretlenmelidir. Manager sınıfı BaseEntity'den miras aldığı için @Builder yerine @SuperBuilder anotasyonunu kullanmalıyız.
 *
 * @MappedSuperclass, JPA(Java Persistence API) entegrasyonu ile kullanılan bir Java anotasyonudur. Bu anotasyon, bir Java sınıfının, veritabanında kalıtım yapılacak(inheritance) bir üst sınıfı(superclass) temsil ettiğini belirtir.
 * @MappedSuperclass anotasyonu ile işaretlenmiş bir üst sınıf(superclass), veritabanında kendisi için bir tablo oluşturulmaz, ancak alt sınıfların(subclass) veritabanı tabloları için bir temel oluşturur.
 * Alt sınıf(subclass) için veritabanında tablo oluşturulurken @MappedSuperclass sayesinde buradaki değişkenleri de o tabloya eklemesini sağlıyoruz.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseEntity {
    private Long createdDate;
    private Long updatedDate;
}
