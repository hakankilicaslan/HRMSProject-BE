### HRMSPROJECT NOTES ###

## RabbitMQ Docker Kurulumu:
docker run -d --hostname my-rabbit --name some-rabbit -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=user -m=128m  rabbitmq:3-management

## SPRING_AMQP_DESERIALIZATION_TRUST_ALL=true
RabbitMQ kullanımında deserilize işlemi sırasında hata almamak için deserilize edecek microservice'in Environment Variable(Ortam Değişkenleri) kısmına üsteki yazı eklenmelidir.
Environment Variable olarak eklerken ilgili service'in application'ına eklenmelidir yani AuthMicroservice için AuthMicroserviceApplication içine eklenmelidir.
AuthMicroserviceApplication üzerine sağ tıklayıp More Run/Debug -> Modify Run Configuration diyerek açılan pencerede Modify options kısmından Environment Variable seçerek Key-Value şeklinde eklenmelidir.
(auth-microservice, user-microservice, guest-microservice, employee-microservice, manager-microservice, admin-microservice, companyservice için applicationlara uygulandı.)

## JwtTokenManager Secret Key ve Issuer
SecretKey ve Issuer bilgilerimizi gizlemek için Environment Variable(Ortam Değişkenleri) kullanacağız. Denetim masasından ortam değişkenler içine de key-value olarak ekleyebiliriz.
Diğer yöntem ise Projeye sağ tıklayıp More Run/Debug -> Modify Run Configuration diyoruz ve Environment Variables kısmına Key-Value olarakta ekliyoruz.
(HRMS_SECRETKEY = Java12) ve (HRMS_ISSUER = Hackerhub) şeklinde Key-Value olarak ekleyeceğiz ve yml dosyamızda sadece HRMS_KEY ve HRMS_ISSUER olarak key değerlerinin yolunu vereceğiz.
(auth-microservice için applicationa uygulandı.)

## MongoDB Database Kurulumu
MongoDB ile işlem yaparken admin kullanıcısı ve admin şifreleri kullanılmamalıdır. Bundan dolayı oluşturulacak her bir DB için yeni bir kullanıcı ve şifre tanımlanmalıdır.
company-service için MongoDB kullanacağız. İlk olarak yml dosyasında da database kısmına eklediğimiz "HRMSCompanyDb" adında bir veritabanı oluşturacağız.
Bunun için mongoDBCompass uygulamasına girip önce Connect diyoruz ve açılan kısımda Databases ekleme yerinden veritabanımızı oluşturuyoruz.
Database Name kısmına HRMSCompanyDb giriyoruz ve Collection Name kısmına da ilgili entity sınıfı için bir isim girebiliriz.(CompanyDb gibi)
Daha sonra veritabanı üzerinde çalışma yapabilmek için mongoDBCompass içinde MONGOSH'ı açıyoruz ve "use HRMSCompanyDb" şeklinde komutumuzu girerek artık database üzerinde işlem yapabiliriz.
Bu database'i yönetecek bir kullanıcı tanımlamalıyız. Bunun için yml dosyasında da username: defaultUser ve password: hrms.42526 kısmında girdiğimiz değerleri kullanacağız.
Son olarakta db.createUser({user:"defaultUser",pwd:"hrms.42526",roles: ["readWrite","dbAdmin"]})  komutunu girerek kullanıcımızı belirtiyoruz.