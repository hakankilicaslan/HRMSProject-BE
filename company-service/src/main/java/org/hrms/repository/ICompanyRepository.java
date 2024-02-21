package org.hrms.repository;

import org.hrms.repository.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*
 * @Repository anotasyonu, Spring Framework'te veritabanı erişimi için kullanılan sınıfları işaretlemeye yarayan bir anotasyondur.
 * Bu anotasyon, veritabanı işlemlerini gerçekleştiren DAO(Data Access Object) sınıflarını tanımlamak için kullanılır.
 * DAO sınıfları genellikle veritabanı işlemlerini gerçekleştiren CRUD(Create, Read, Update, Delete) operasyonlarını içerir.
 * Bu sınıflar, veritabanına erişim sağlar ve iş mantığı katmanı ile veritabanı arasında bir köprü görevi görür.
 *
 * MongoRepository, Spring Framework ile ilişkili olan ve MongoDB veritabanıyla etkileşimde bulunmayı sağlayan bir interface'dir ve genellikle veritabanı işlemlerini gerçekleştirmek için kullanılır.
 * MongoRepository genellikle temel CRUD (Create, Read, Update, Delete) işlemlerini içeren bir dizi standart yöntem sağlar.
 * MongoRepository, Spring Data JPA tarafından sağlanan bir interface'dir ve genellikle veritabanı işlemlerini gerçekleştirmek için kullanılır.
 * MongoRepository, diğer yaygın veritabanı işlemleri için de önceden tanımlanmış metotlara sahiptir.
 * MongoRepository interface'inin parametreleri de ilgili entity sınıfını ve bu sınıfın birincil anahtar türünü içerir.(<Company, String>)
 * Aşağıda yazılan metotlarda MongoRepository anlayacak şekilde doğru bir biçimde metot isimlendirilirse MongoRepository metot ismine göre veritabanında arama yaparak geriye istenilen değeri dönebilir.
 */
@Repository
public interface ICompanyRepository extends MongoRepository<Company, String> {
}
