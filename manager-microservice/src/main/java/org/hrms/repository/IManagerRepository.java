package org.hrms.repository;

import org.hrms.repository.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * @Repository anotasyonu, Spring Framework'te veritabanı erişimi için kullanılan sınıfları işaretlemeye yarayan bir anotasyondur.
 * Bu anotasyon, veritabanı işlemlerini gerçekleştiren DAO(Data Access Object) sınıflarını tanımlamak için kullanılır.
 * DAO sınıfları genellikle veritabanı işlemlerini gerçekleştiren CRUD(Create, Read, Update, Delete) operasyonlarını içerir.
 * Bu sınıflar, veritabanına erişim sağlar ve iş mantığı katmanı ile veritabanı arasında bir köprü görevi görür.
 *
 * JpaRepository, Spring Data JPA tarafından sağlanan bir interface'dir ve genellikle veritabanı işlemlerini gerçekleştirmek için kullanılır.
 * JpaRepository, diğer yaygın veritabanı işlemleri için de önceden tanımlanmış metotlara sahiptir.
 * JpaRepository interface'inin parametreleri de ilgili entity sınıfını ve bu sınıfın birincil anahtar türünü içerir.(<Manager,Long>)
 * Aşağıda yazılan metotlarda JpaRepository anlayacak şekilde doğru bir biçimde metot isimlendirilirse JpaRepository metot ismine göre veritabanında arama yaparak geriye istenilen değeri dönebilir.
 */
@Repository
public interface IManagerRepository extends JpaRepository<Manager,Long> {

    Boolean existsByEmail(String email);  //Parametre olarak girilen emailin veritabanında olup olmadığını kontrol ediyoruz.
    Boolean existsByPhoneNumber(String phoneNumber); //Parametre olarak girilen telefon numarasının veritabanında olup olmadığını kontrol ediyoruz.
    Boolean existsByIdentityNumber(String identityNumber); //Parametre olarak girilen kimlik numarasının veritabanında olup olmadığını kontrol ediyoruz.
    Boolean existsByAuthId(Long authId); //Parametre olarak girilen authId veritabanında olup olmadığını kontrol ediyoruz.
    Boolean existsByCompanyId(Long authId); //Parametre olarak girilen companyId veritabanında olup olmadığını kontrol ediyoruz.
    Optional<Manager> findOptionalByAuthId(Long authId); //Parametre olarak girilen authId'ye göre veritabanını kontrol edip geriye optional olarak bir Manager dönüyor.
    Optional<Manager> findOptionalByCompanyId(Long companyId); //Parametre olarak girilen companyId'ye göre veritabanını kontrol edip geriye optional olarak bir Manager dönüyor.
    Boolean existsByCompanyName(String companyName); //Parametre olarak girilen şirket adının veritabanında olup olmadığını kontrol ediyoruz.
    List<Manager> findManagersByCompanyName(String companyName); //Parametre olarak girilen şirket adına sahip kullanıcıların bilgilerini Manager listesi olarak dönüyor.

}
