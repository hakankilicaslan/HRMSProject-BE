package org.hrms.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Bir konfigürasyon sınıfı olduğunu belirtiyoruz yani bu sınıfın Spring Container'ında yapılandırma sınıfı olarak kullanılacağını belirtmiş oluyoruz.
@EnableWebMvc //Spring MVC(Model-View-Controller) kullanımını etkinleştirmek için kullanılır.
public class CorsConfig implements WebMvcConfigurer { //WebMvcConfigurer interface'i implement edilerek Spring MVC tabanlı uygulamaları özelleştirmek için çeşitli ayarları yapılandırabiliriz.

    /*
     * CORS -> Cross-Origin Resource Sharing
     * addMapping() kısmında CORS yapılandırmasının hangi URL yollarına uygulanacağını belirtiyoruz ve "/**" diyerekte tüm url'lere yani isteklere CORS politikası uygulamış oluyoruz.
     * allowedOrigins() hangi kaynakların bu API'ye erişebileceğini belirtiyoruz ve "*" diyerekte tüm kaynakların erişimine izin vermiş oluyoruz. (`"http://example.com"`, vs.) şeklinde izin vermek daha güvenlidir.
     * allowedMethods() hangi HTTP yöntemlerinin bu API'yi kullanabileceği belirtilir ve girdiğimiz bu 4 metoda izin vermiş oluyoruz.
     * allowCredentials() tarayıcının isteğe kimlik doğrulama bilgileri göndermesini belirtir ve false diyerek tarayıcının kimlik doğrulama bilgilerini göndermemesi gerektiğini ifade ediyoruz.
     * allowCredentials(false) kullanımında bazı durumlarda tarayıcının CORS politikaları nedeniyle sınırlamalar olabilir ve bazı tarayıcılar kimlik doğrulama bilgileri olmadan yapılan CORS isteklerini kısıtlayabilir.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(false);
    }

}