package org.hrms.mapper;

import org.hrms.dto.request.CompanyRegisterRequestDto;
import org.hrms.dto.request.GuestRegisterRequestDto;
import org.hrms.dto.response.CompanyRegisterResponseDto;
import org.hrms.dto.response.FindAllResponseDto;
import org.hrms.dto.response.FindByIdResponseDto;
import org.hrms.dto.response.GuestRegisterResponseDto;
import org.hrms.rabbitmq.model.AdminSaveModel;
import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.hrms.rabbitmq.model.EmployeeCreateModel;
import org.hrms.repository.entity.Auth;
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
public interface IAuthMapper {

    /*
     * Bu kod parçası, MapStruct kütüphanesini kullanarak nesne dönüşümlerini gerçekleştirmek için bir dönüşüm sınıfının(mapper interface) bir örneğini oluşturmayı sağlar.
     * MapStruct, dönüşüm sınıflarını oluşturmak için arayüzleri kullanır. Bu arayüzler, kaynak ve hedef nesneleri arasındaki alanları eşleştirmek için kullanılan yönergeleri içerir.
     * IAuthMapper arayüzünün bir örneğini oluşturup kullanarak, bu arayüze tanımlı olan dönüşüm metotlarına erişebiliyor ve bu metotları kullanarak nesne dönüşümlerini gerçekleştirebiliyoruz.
     */
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth companyRegisterRequestDtoToAuth(CompanyRegisterRequestDto dto);
    Auth guestRegisterRequestDtoToAuth(GuestRegisterRequestDto dto);
    GuestRegisterResponseDto authToGuestRegisterResponseDto(Auth auth);
    CompanyRegisterResponseDto authToCompanyRegisterResponseDto(Auth auth);
    FindAllResponseDto authToFindAllResponseDto(Auth auth);
    FindByIdResponseDto authToFindByIdResponseDto(Auth auth);
    Auth employeeCreateModelToAuth(EmployeeCreateModel model);
    Auth  adminSaveModelToAuth(AdminSaveModel model);

    /*
     * Burada AuthUpdateModel sınıfındaki authId'yi Auth sınıfındaki id'ye eşitlemiş oluyoruz. İsimleri aynı olmadığı için eşleşmediğinden ignore ediyor.
     * Bunun önüne geçmek için @Mapping anotasyonunu kullanarak kaynak ve hedef değişkenleri yazarak birbiriyle eşleşmesini sağlıyoruz.
     */
    /*@Mapping(source = "authId",target = "id")
    Auth authUpdateModelToAuth(AuthUpdateModel model);*/

}
