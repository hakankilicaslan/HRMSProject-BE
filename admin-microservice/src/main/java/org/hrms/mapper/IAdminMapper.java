package org.hrms.mapper;

import org.hrms.dto.request.AdminSaveRequestDto;
import org.hrms.dto.request.AdminUpdateRequestDto;
import org.hrms.dto.response.AdminSaveResponseDto;
import org.hrms.dto.response.FindAdminByIdResponseDto;
import org.hrms.dto.response.FindAllAdminsResponseDto;
import org.hrms.rabbitmq.model.AdminSaveModel;
import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.hrms.repository.entity.Admin;
import org.mapstruct.Mapper;
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
public interface IAdminMapper {

    /*
     * Bu kod parçası, MapStruct kütüphanesini kullanarak nesne dönüşümlerini gerçekleştirmek için bir dönüşüm sınıfının(mapper interface) bir örneğini oluşturmayı sağlar.
     * MapStruct, dönüşüm sınıflarını oluşturmak için arayüzleri kullanır. Bu arayüzler, kaynak ve hedef nesneleri arasındaki alanları eşleştirmek için kullanılan yönergeleri içerir.
     * IAdminMapper arayüzünün bir örneğini oluşturup kullanarak, bu arayüze tanımlı olan dönüşüm metotlarına erişebiliyor ve bu metotları kullanarak nesne dönüşümlerini gerçekleştirebiliyoruz.
     */
    IAdminMapper INSTANCE = Mappers.getMapper(IAdminMapper.class);

    Admin adminSaveRequestDtoToAdmin(AdminSaveRequestDto dto);
    AdminSaveResponseDto adminToAdminSaveResponseDto(Admin admin);
    AdminSaveModel adminToAdminSaveModel(Admin admin);
    Admin adminUpdateRequestDtoToAdmin(AdminUpdateRequestDto dto);
    AuthUpdateModel adminToAuthUpdateModel(Admin admin);
    FindAllAdminsResponseDto adminToFindAllAdminsResponseDto(Admin admin);
    FindAdminByIdResponseDto adminToFindAdminByIdResponseDto(Admin admin);

}
