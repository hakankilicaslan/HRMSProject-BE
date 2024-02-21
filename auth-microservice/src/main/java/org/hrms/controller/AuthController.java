package org.hrms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.AuthForgotPasswordRequestDto;
import org.hrms.dto.request.AuthLoginRequestDto;
import org.hrms.dto.request.CompanyRegisterRequestDto;
import org.hrms.dto.request.GuestRegisterRequestDto;
import org.hrms.dto.response.*;
import org.hrms.exception.AuthServiceException;
import org.hrms.exception.ErrorType;
import org.hrms.service.AuthService;
import org.hrms.repository.enums.EStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

import static org.hrms.constant.ApiUrls.*; //Endpointleri tek tek import etmek yerine direkt sınıfı static olarak import ediyoruz ve sonuna .* diyerek hepsine ulaşabiliyoruz.

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
 * @RequestMapping(AUTH) diyerek "api/v1/auth" URL'sine gelen istekleri bu sınıfa yönlendirir.
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
 * @RequiredArgsConstructor anotasyonu dependencies injection edilen final fieldlara(AuthService) constructor oluşturmak için kullanılan bir anotasyondur.
 */
@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /*
     * @RequestBody anotasyonu HTTP isteğinin gövdesindeki (request body) verilerin, bir metot parametresine otomatik olarak bağlanmasını sağlar. POST ve PUT isteklerinde kullanılır.
     * Bu anotasyon, Spring tarafından otomatik olarak HTTP isteğinin gövdesini uygun bir Java nesnesine dönüştürmek için kullanılır.
     * Bu sayede, gönderilen verilerin manuel olarak çözülmesine veya dönüştürülmesine gerek kalmaz, Spring bunu otomatik olarak yapar ve metot parametresine bağlar.
     * Bu anotasyon sayesinde gönderilen POST isteğinde URL kısmında girilen parametrelerin de body içinde gizler ve güvenli şekilde istek atılmasını sağlar.
     *
     * @Valid anotasyonu, Spring Framework'te form doğrulamasını gerçekleştirmek için kullanılır ve bu sınıfın alanlarındaki doğrulama kısıtlamalarının uygulanmasını sağlar.
     * Bu anotasyonla işaretlendiğinde gelen veriler ilgili sınıftaki doğrulama kısıtlamalarına uymuyorsa, Spring otomatik olarak bir hata yanıtı döndürür.
     */
    @PostMapping(GUEST_REGISTER)
    public ResponseEntity<GuestRegisterResponseDto> guestRegister(@RequestBody @Valid GuestRegisterRequestDto dto) {
        if (!dto.getPassword().equals(dto.getRePassword())){
            throw new AuthServiceException(ErrorType.PASSWORD_MISMATCH);
        }
        return ResponseEntity.ok(authService.guestRegister(dto));
    }

    @PostMapping(COMPANY_REGISTER)
    public ResponseEntity<CompanyRegisterResponseDto> companyRegister(@RequestBody @Valid CompanyRegisterRequestDto dto) {
        if (!dto.getPassword().equals(dto.getRePassword())){
            throw new AuthServiceException(ErrorType.PASSWORD_MISMATCH);
        }
        return ResponseEntity.ok(authService.companyRegister(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<AuthLoginResponseDto> login(@RequestBody AuthLoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@RequestBody AuthForgotPasswordRequestDto dto) {
        return ResponseEntity.ok(authService.forgotPassword(dto));
    }

    /*
     * @RequestParam anotasyonu bir HTTP isteği sırasında URL'den veya form parametrelerinden bir parametreyi almak için kullanılır.
     * Bu anotasyon, belirli bir parametrenin zorunlu olup olmadığını belirtmek için kullanılır.
     * required = false parametresi, belirtilen parametrenin isteğe bağlı olduğunu belirtir yani, bu parametre ile işaretlenmiş bir parametrenin, HTTP isteğinde bulunması gerekmez.
     * Eğer istek gönderilirken bu parametre sağlanmazsa, ilgili controller metodu çalışacak ve bu parametre null olarak alınacaktır ve metot normal şekilde çalışacaktır.
     * Burada da findAll() metodu çağırıldığında EStatus status parametresini girmek zorunlu değildir yani girilmemişse null alınır ve metot çalışmaya devam eder.
     * findAll(String token, @RequestParam(required = false) EStatus status)
     */
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<FindAllResponseDto>> findAllUsers(String token){
        return ResponseEntity.ok(authService.findAllUsers(token));
    }

    /*
     * @PathVariable anotasyonu bir HTTP isteği sırasında URL'deki bir değişkenin değerini dinamik olarak almak için kullanılır.
     * FIND_BY_ID endpoint'in sonundaki {id} değeri dinamik olarak alınır ve metot içinde kullanılır.
     * Bu sayede, URL'de belirtilen kullanıcı kimliğine sahip olan kullanıcı bilgileri alınabilir.
     */
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FindByIdResponseDto> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(authService.findUserById(id));
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<String> softDelete(@PathVariable Long id){
        return ResponseEntity.ok(authService.softDelete(id));
    }

    /*
     * @PathVariable ile @RequestParam -> Her ikisi de HTTP isteklerinden parametre almak için kullanılır ancak alınan parametrelerin türleri ve alındıkları yerler farklıdır.
     * @PathVariable: URL'de yer alan sabit yapıdaki parçaları ({userId} gibi) almak için kullanılır. @RequestParam: URL'den veya HTML formundan gelen query string olarak (?key=value) veya HTML formunda gönderilen parametreleri almak için kullanılır.
     * @PathVariable: Dinamik URL parçalarını ({userId} gibi) almak için kullanılır. @RequestParam: Query string veya HTML form parametrelerini almak için kullanılır.
     * @PathVariable: Bu anotasyon varsayılan olarak zorunlu değildir. Yani, eğer URL'deki path variable sağlanmazsa, isteği işleyen metot hata almadan çalışabilir.
     * @RequestParam: Bu anotasyon varsayılan olarak zorunludur. Eğer istek sırasında ilgili parametre sağlanmazsa, isteği işleyen metot bir hata alabilir veya null bir değerle çalışabilir.
     * required = false parametresi girilirse belirtilen parametrenin isteğe bağlı olduğunu belirtir yani, bu parametre ile işaretlenmiş bir parametrenin, HTTP isteğinde bulunması gerekmez.
     * Eğer istek gönderilirken bu parametre sağlanmazsa, ilgili controller metodu çalışacak ve bu parametre null olarak alınacaktır ve metot normal şekilde çalışacaktır.
     */




    /*@GetMapping(USER_ACTIVE)
    public String userActive(String token) throws IOException {
        return authService.userActive(token);
    }*/
}
