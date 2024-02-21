package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.CompanyRegisterModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class CompanyRegisterProducer {

    private final RabbitTemplate rabbitTemplate;

    public CompanyRegisterProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.manager-exchange}")
    private String managerExchange;

    @Value("${rabbitmq.company-register-bindingKey}")
    private String companyRegisterBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * CompanyRegisterProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için managerExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan managerExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan companyRegisterBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan CompanyRegisterModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz CompanyRegisterModel'imizi ilgili kuyruk olan companyRegisterQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(CompanyRegisterModel companyRegisterModel){
        rabbitTemplate.convertAndSend(managerExchange,companyRegisterBindingKey,companyRegisterModel);
    }

}
