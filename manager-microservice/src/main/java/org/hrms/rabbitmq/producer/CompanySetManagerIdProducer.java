package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.CompanySetManagerIdModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class CompanySetManagerIdProducer {

    private final RabbitTemplate rabbitTemplate;

    public CompanySetManagerIdProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.company-exchange}")
    private String companyExchange;

    @Value("${rabbitmq.company-set-manager-id-bindingKey}")
    private String companySetManagerIdBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * ManagerSetCompanyIdProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için companyExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan companyExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan companySetManagerIdBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan CompanySetManagerIdModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz CompanySetManagerIdModel'imizi ilgili kuyruk olan companySetManagerIdQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(CompanySetManagerIdModel companySetManagerIdModel){
        rabbitTemplate.convertAndSend(companyExchange,companySetManagerIdBindingKey,companySetManagerIdModel);
    }
}
