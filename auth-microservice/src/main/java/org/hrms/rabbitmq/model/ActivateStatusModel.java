package org.hrms.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

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
 *
 * Model sınıflarını serilize ve deserilize işlemlerini yapabilmek için Serializable interface'inden implement ediyoruz.
 * Model sınıflarını hem mesajı gönderen mikcoservice'te hem de mesajı alacak microservice'te aynı isimli sınıf olarak bulunması gerekiyor.
 * Model sınıflarının iki microservice'te de package ismine kadar aynı olacak şekilde bulunmaları gerekiyor.
 * Jackson2JsonMessageConverter yöntemini kullansaydık model sınıflarını Serializable interface'inden implement etmeye gerek kalmayacaktı.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ActivateStatusModel implements Serializable {
    private Long authId;
}
