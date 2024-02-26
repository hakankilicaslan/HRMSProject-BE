package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.ActivateStatusModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class ManagerActivateStatusProducer {

    private final RabbitTemplate rabbitTemplate;

    public ManagerActivateStatusProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.manager-exchange}")
    private String managerExchange;

    @Value("${rabbitmq.manager-activate-status-bindingKey}")
    private String managerActivateStatusBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * ActivateStatusProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için managerExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan managerExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan managerActivateStatusBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan ActivateStatusModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz ActivateStatusModel'imizi ilgili kuyruk olan managerActivateStatusQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(ActivateStatusModel activateStatusModel){
        rabbitTemplate.convertAndSend(managerExchange,managerActivateStatusBindingKey,activateStatusModel);
    }
}
