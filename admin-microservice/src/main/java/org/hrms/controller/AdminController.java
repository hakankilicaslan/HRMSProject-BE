package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.AdminSaveRequestDto;
import org.hrms.dto.request.AdminUpdateRequestDto;
import org.hrms.dto.response.AdminSaveResponseDto;
import org.hrms.dto.response.FindAdminByIdResponseDto;
import org.hrms.dto.response.FindAllAdminsResponseDto;
import org.hrms.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
 * @RequestMapping(ADMIN) diyerek "api/v1/admin" URL'sine gelen istekleri bu sınıfa yönlendirir.
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
 * @RequiredArgsConstructor anotasyonu dependencies injection edilen final fieldlara(AdminService) constructor oluşturmak için kullanılan bir anotasyondur.
 */
@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /*
     * @RequestBody anotasyonu HTTP isteğinin gövdesindeki (request body) verilerin, bir metot parametresine otomatik olarak bağlanmasını sağlar. POST ve PUT isteklerinde kullanılır.
     * Bu anotasyon, Spring tarafından otomatik olarak HTTP isteğinin gövdesini uygun bir Java nesnesine dönüştürmek için kullanılır.
     * Bu sayede, gönderilen verilerin manuel olarak çözülmesine veya dönüştürülmesine gerek kalmaz, Spring bunu otomatik olarak yapar ve metot parametresine bağlar.
     * Bu anotasyon sayesinde gönderilen POST isteğinde URL kısmında girilen parametrelerin de body içinde gizler ve güvenli şekilde istek atılmasını sağlar.
     */
    @PostMapping(SAVE)
    public ResponseEntity<AdminSaveResponseDto> saveAdmin(@RequestBody AdminSaveRequestDto dto){
        return ResponseEntity.ok(adminService.saveAdmin(dto));
    }

    @PatchMapping(UPDATE)
    public ResponseEntity<String> softUpdate(@RequestBody AdminUpdateRequestDto dto){
        return ResponseEntity.ok(adminService.softUpdate(dto));
    }

    /*
     * @PathVariable anotasyonu bir HTTP isteği sırasında URL'deki bir değişkenin değerini dinamik olarak almak için kullanılır.
     * DELETE_BY_ID endpoint'in sonundaki {id} değeri dinamik olarak alınır ve metot içinde kullanılır.
     * Bu sayede, URL'de belirtilen kullanıcı kimliğine sahip olan kullanıcı bilgileri alınabilir.
     */
    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<String> softDelete(@PathVariable Long id){
        return ResponseEntity.ok(adminService.softDelete(id));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<FindAllAdminsResponseDto>> findAllAdmins(){
        return ResponseEntity.ok(adminService.findAllAdmins());
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FindAdminByIdResponseDto> findAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.findAdminById(id));
    }

    @GetMapping(FIND_BY_AUTH_ID)
    public ResponseEntity<FindAdminByIdResponseDto> findAdminByAuthId(@PathVariable Long authId) { //Path verirken constant tarafına da {authId} olarak vermezsek hata alıyoruz.
        return ResponseEntity.ok(adminService.findAdminByAuthId(authId));
    }


}
