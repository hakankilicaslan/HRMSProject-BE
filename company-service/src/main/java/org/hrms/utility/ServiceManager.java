package org.hrms.utility;

import org.hrms.repository.entity.BaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

/*
 * ServiceManager sınıfımızı bir servis yöneticisi olarak kullanıyoruz. Bu sınıf, T ve ID olmak üzere iki generic tip parametresi alır.
 * T -> BaseEntity sınıfından miras almış genellikle bir veritabanı tablosunu temsil eden entity sınıf olur. ID -> Bu entity sınıfının birincil anahtarının türünü temsil eder.
 * IService interface'ini implement ediyoruz ve bu interface genellikle bir entity sınıfı için CRUD işlemlerini (Create, Read, Update, Delete) tanımlayan bir interfacedir.
 * Override ettiğimiz metotları ilgili entityler için düzenliyoruz ve bu şekilde generic bir yapı oluşturarak yaptığımız servis yöneticisini istediğimiz entity ve id için kullanabiliyoruz.
 * Daha sonra aynı isimli yazdığımız metotlar olan MongoRepository içindeki ilgili metotlara parametremizi gönderiyoruz.
 */
public class ServiceManager<T extends BaseEntity,ID> implements IService<T,ID> {

    private final MongoRepository<T,ID> mongoRepository;

    public ServiceManager(MongoRepository<T, ID> mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public T save(T t) {
        t.setCreatedDate(System.currentTimeMillis());
        t.setUpdatedDate(System.currentTimeMillis());
        return mongoRepository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
        t.forEach(x->{
            x.setCreatedDate(System.currentTimeMillis());
            x.setUpdatedDate(System.currentTimeMillis());
        });
        return mongoRepository.saveAll(t);
    }

    @Override
    public T update(T t) {
        t.setUpdatedDate(System.currentTimeMillis());
        return mongoRepository.save(t);
    }

    @Override
    public void delete(T t) {
        mongoRepository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return mongoRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return mongoRepository.findAll();
    }

}
