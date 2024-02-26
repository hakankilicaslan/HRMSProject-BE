package org.hrms.repository;

import org.hrms.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * @Repository anotasyonu, Spring Framework'te veritabanı erişimi için kullanılan sınıfları işaretlemeye yarayan bir anotasyondur.
 * Bu anotasyon, veritabanı işlemlerini gerçekleştiren DAO(Data Access Object) sınıflarını tanımlamak için kullanılır.
 * DAO sınıfları genellikle veritabanı işlemlerini gerçekleştiren CRUD(Create, Read, Update, Delete) operasyonlarını içerir.
 * Bu sınıflar, veritabanına erişim sağlar ve iş mantığı katmanı ile veritabanı arasında bir köprü görevi görür.
 *
 * JpaRepository, Spring Data JPA tarafından sağlanan bir interface'dir ve genellikle veritabanı işlemlerini gerçekleştirmek için kullanılır.
 * JpaRepository, diğer yaygın veritabanı işlemleri için de önceden tanımlanmış metotlara sahiptir.
 * JpaRepository interface'inin parametreleri de ilgili entity sınıfını ve bu sınıfın birincil anahtar türünü içerir.(<Auth,Long>)
 * Aşağıda yazılan metotlarda JpaRepository anlayacak şekilde doğru bir biçimde metot isimlendirilirse JpaRepository metot ismine göre veritabanında arama yaparak geriye istenilen değeri dönebilir.
 */
@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {

    Boolean existsByEmail(String email);  //Parametre olarak girilen emailin veritabanında olup olmadığını kontrol ediyoruz.
    Boolean existsByPhoneNumber(String phoneNumber); //Parametre olarak girilen telefon numarasının veritabanında olup olmadığını kontrol ediyoruz.
    Optional<Auth> findOptionalByEmailAndPassword(String email, String password); //Parametre olarak girilen email ve password'ün veritabanında olup olmadığını kontrol ediyoruz ve optional olarak geriye dönüyoruz.
    Optional<Auth> findOptionalByEmail(String email); //Parametre olarak girilen email'in veritabanında olup olmadığını kontrol ediyoruz ve optional olarak geriye dönüyoruz.



    //Parametre olarak girilen id değerinin veritabanında olup olmadığını kontrol edecek ve optional olarak geriye dönecek metot:
    Optional<Auth> findOptionalById(Long authid);


}
