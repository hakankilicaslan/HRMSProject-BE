package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.service.CompanyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.hrms.constant.ApiUrls.*;

/*
 * @CrossOrigin anotasyonu, Spring Framework'te CORS(Cross-Origin Resource Sharing) konfigürasyonunu yapılandırmak için kullanılır.
 * CORS, web tarayıcıları aracılığıyla, bir kaynağın (örneğin bir web sitesi) farklı bir kök URL'den gelen istekleri kabul edip etmeyeceğini belirleyen bir güvenlik önlemidir.
 * maxAge = 3600 parametresi, tarayıcının yapılan bir önbelleği 3600 saniye boyunca saklamasına izin verir.
 * allowedHeaders = "*" parametresi de tüm HTTP başlıklarının isteklerde kullanılmasına izin verir.
 * @CrossOrigin anotasyon olarak direkt sınıfa uygulanırsa sınıf bazında bütün metotlara ya da metot bazında uygulanırsa sadece o metot bazında çalışır.
 *
 * @RestController anotasyonu @Controller anotasyonunun özel bir türüdür ve bu sınıfın Spring MVC'de bir RESTful web servisi sağladığı belirtilir. @Controller ve @ResponseBody anotasyonlarının birleşimidir.
 * @RestController anotasyonu, herhangi bir metot tarafından döndürülen verilerin doğrudan HTTP yanıt gövdesine (response body) yazılmasını sağlar. Yani, ayrı bir @ResponseBody anotasyonu kullanmaya gerek kalmaz.
 * @Controller ile işaretlenen sınıflarda her metot varsayılan olarak bir görünüm döndürmek için tasarlanmıştır.
 * @RestController ile işaretlenen sınıflarda ise metotlar genellikle JSON, XML veya diğer veri formatlarını döndürmek için kullanılır.
 * @RestController, RESTful web servislerinin oluşturulması için daha uygundur çünkü JSON veya XML gibi veri formatlarını doğrudan döndürmek ve istekleri işlemek için daha uygun bir yapıya sahiptir.
 *
 * @RequestMapping anotasyonu, Spring MVC'de HTTP isteklerini belirli bir denetleyici metoda eşlemek için kullanılır.
 * Bu anotasyon bir sınıf seviyesinde veya bir metot seviyesinde kullanılabilir ve belirli bir URL veya URL kalıbıyla eşleşen HTTP isteklerini belirli bir denetleyici metoda yönlendirir.
 * @RequestMapping(COMPANY) diyerek "api/v1/company" URL'sine gelen istekleri bu sınıfa yönlendirir.
 * @RequestMapping anotasyonunun özel amaçlı alt türleri: @PostMapping, @GetMapping, @DeleteMapping, @PutMapping ve @PatchMapping gibi anotasyonlardır.
 * @PostMapping: Spring Framework'ün request body ile gelen HTTP POST isteklerini işlemek ve denetleyici metotlarla eşlemek için kullandığı özel amaçlı bir @RequestMapping anotasyonudur.
 * @GetMapping: Spring Framework'ün HTTP GET isteklerini işlemek ve geri dönmek için kullanılan genellikle sorgu parametreleriyle çalışan özel amaçlı bir @RequestMapping anotasyonudur.
 * @DeleteMapping: Spring Framework'ün HTTP DELETE isteklerini işlemek için kullanılan silme işlemlerinde gelen parametreye göre silme işlemi yapan özel amaçlı bir @RequestMapping anotasyonudur.
 * @PutMapping: Spring Framework'ün request body ile gelen HTTP PUT isteklerini işlemek için kullanılan ve genelde body içinde gelen parametrelerle veriyi güncelemeye yarayan özel amaçlı bir @RequestMapping anotasyonudur.
 * @PatchMapping: Spring Framework'ün HTTP PATCH isteklerini işlemek için kullanılan ve kaynağın tamamını değil de sadece belirli alanlarını güncellemeye yarayan özel amaçlı bir @RequestMapping anotasyonudur.
 * @PutMapping ile @PatchMapping arasındaki fark @PutMapping isteğinde gelen verile kaynağın tamamını içermelidir ve genellikle kaynağın tamamen değiştirilmesi gerektiğinde kullanılır.
 * Güncellenmeyen değişkenler için mevcut değerler gönderilmelidir ama güncellenmeyen değişkenlerin mevcut değerleri gönderilmediyse de  ilgili alanlar mevcut değerlerini korur.
 * @PatchMapping ise kaynağın belirli alanlarını değiştirmek için kullanılır. Yalnızca gönderilen değerler güncellenir ve diğer alanlar değişmeden mevcut değerini korur.
 *
 * @RequiredArgsConstructor anotasyonu dependencies injection edilen final fieldlara(CompanyService) constructor oluşturmak için kullanılan bir anotasyondur.
 */
@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(COMPANY)
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
}
