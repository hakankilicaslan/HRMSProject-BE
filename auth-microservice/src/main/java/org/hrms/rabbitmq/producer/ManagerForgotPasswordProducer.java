package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.AuthForgotPasswordModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class ManagerForgotPasswordProducer {

    private final RabbitTemplate rabbitTemplate;

    public ManagerForgotPasswordProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.manager-exchange}")
    private String managerExchange;

    @Value("${rabbitmq.manager-forgot-password-bindingKey}")
    private String managerForgotPasswordBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * CompanyRegisterProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için managerExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan managerExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan managerForgotPasswordBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan AuthForgotPasswordModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz AuthForgotPasswordModel'imizi ilgili kuyruk olan managerForgotPasswordQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(AuthForgotPasswordModel authForgotPasswordModel){
        rabbitTemplate.convertAndSend(managerExchange,managerForgotPasswordBindingKey,authForgotPasswordModel);
    }

}
