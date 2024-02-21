package org.hrms.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
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
 *
 * @JsonInclude, Jackson kütüphanesinde bulunan ve JSON serileştirmesini yapılandırmak için kullanılan bir anotasyondur ve JSON çıktısında hangi alanların dikkate alınacağını belirler.
 * JsonInclude.Include.NON_NULL diyerek null olmayan alanların yalnızca JSON çıktısında yer almasını sağlıyoruz, null olanlar dahil edilmez.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {

    private int code;
    private String message;
    private List<String> fields;
    @Builder.Default //@Builder.Default anotasyonu kullanarak eğer statüsü belirtilmediyse default olarak aşağıda belirttiğimiz gibi LocalDateTime.now() olarak atanmasını sağlıyoruz.
    private LocalDateTime date = LocalDateTime.now();

}
