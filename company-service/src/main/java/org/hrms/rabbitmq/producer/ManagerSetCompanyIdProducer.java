package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.ManagerSetCompanyIdModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class ManagerSetCompanyIdProducer {

    private final RabbitTemplate rabbitTemplate;

    public ManagerSetCompanyIdProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.manager-exchange}")
    private String managerExchange;

    @Value("${rabbitmq.manager-set-company-id-bindingKey}")
    private String managerSetCompanyIdBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * ManagerSetCompanyIdProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için managerExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan managerExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan managerSetCompanyIdBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan ManagerSetCompanyIdModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz ManagerSetCompanyIdModel'imizi ilgili kuyruk olan managerSetCompanyIdQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(ManagerSetCompanyIdModel managerSetCompanyIdModel){
        rabbitTemplate.convertAndSend(managerExchange,managerSetCompanyIdBindingKey,managerSetCompanyIdModel);
    }
}
