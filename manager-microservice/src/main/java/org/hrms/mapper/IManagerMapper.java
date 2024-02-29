package org.hrms.mapper;

import org.hrms.dto.request.ManagerUpdateRequestDto;
import org.hrms.dto.response.FindAllManagersResponseDto;
import org.hrms.dto.response.FindManagerByIdResponseDto;
import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.hrms.repository.entity.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/*
 * @Mapper anotasyonu, MapStruct kütüphanesini kullanarak Java sınıfları arasında nesne dönüşümlerini kolaylaştıran bir anotasyondur.
 * componentModel parametresi MapStruct tarafından oluşturulan dönüşüm sınıflarının nasıl oluşturulacağını belirtir.
 * "spring" olarak ayarlandığında, Spring framework'ünü kullanarak dönüşüm sınıflarını yönetmek için Spring component modelini kullanır.
 * unmappedTargetPolicy parametresi hedef sınıfta(target class) hedeflenmeyen(mapping yapılmayan) alanların nasıl işleneceğini belirtir.
 * ReportingPolicy.IGNORE olarak ayarlandığında, hedef sınıfta hedeflenmeyen alanlar için herhangi bir raporlama yapılmaz ve bu alanlar görmezden gelinir.
 * Diğer bir seçenek olarak ReportingPolicy.ERROR belirtilebilir ki bu durumda hedeflenmeyen alanlar hata olarak raporlanır.
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IManagerMapper {

    /*
     * Bu kod parçası, MapStruct kütüphanesini kullanarak nesne dönüşümlerini gerçekleştirmek için bir dönüşüm sınıfının(mapper interface) bir örneğini oluşturmayı sağlar.
     * MapStruct, dönüşüm sınıflarını oluşturmak için arayüzleri kullanır. Bu arayüzler, kaynak ve hedef nesneleri arasındaki alanları eşleştirmek için kullanılan yönergeleri içerir.
     * IManagerMapper arayüzünün bir örneğini oluşturup kullanarak, bu arayüze tanımlı olan dönüşüm metotlarına erişebiliyor ve bu metotları kullanarak nesne dönüşümlerini gerçekleştirebiliyoruz.
     */
    IManagerMapper INSTANCE = Mappers.getMapper(IManagerMapper.class);

    FindAllManagersResponseDto managerToFindAllManagersResponseDto(Manager manager);
    FindManagerByIdResponseDto managerToFindManagerByIdResponseDto(Manager manager);

    /*
     * Burada ManagerUpdateRequestDto sınıfındaki id'yi AuthUpdateModel sınıfındaki authId'ye eşitlemiş oluyoruz. İsimleri aynı olmadığı için eşleşmediğinden ignore ediyor.
     * Bunun önüne geçmek için @Mapping anotasyonunu kullanarak kaynak ve hedef değişkenleri yazarak birbiriyle eşleşmesini sağlıyoruz.
     */
    @Mapping(source = "id",target = "authId")
    AuthUpdateModel managerUpdateRequestDtoToAuthUpdateModel(ManagerUpdateRequestDto dto);

}
