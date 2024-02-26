package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.MailSenderModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class MailSenderProducer {

    private final RabbitTemplate rabbitTemplate;

    public MailSenderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.mail-exchange}")
    private String mailExchange;

    @Value("${rabbitmq.mail-bindingKey}")
    private String mailBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * CompanyRegisterProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için mailExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan mailExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan mailBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan MailSenderModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz MailSenderModel'imizi ilgili kuyruk olan mailQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(MailSenderModel model){
        rabbitTemplate.convertAndSend(mailExchange,mailBindingKey,model);
    }

}
