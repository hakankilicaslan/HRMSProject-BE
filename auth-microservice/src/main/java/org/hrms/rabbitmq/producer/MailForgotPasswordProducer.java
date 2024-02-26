package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.MailForgotPasswordModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class MailForgotPasswordProducer {

    private final RabbitTemplate rabbitTemplate;

    public MailForgotPasswordProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.mail-exchange}")
    private String mailExchange;

    @Value("${rabbitmq.mail-forgot-password-bindingKey}")
    private String mailForgotPasswordBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * MailForgotPasswordProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için mailExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan mailExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan mailForgotPasswordBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan MailForgotPasswordModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz MailForgotPasswordModel'imizi ilgili kuyruk olan mailForgotPasswordQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(MailForgotPasswordModel mailForgotPasswordModel){
        rabbitTemplate.convertAndSend(mailExchange,mailForgotPasswordBindingKey,mailForgotPasswordModel);
    }
}
