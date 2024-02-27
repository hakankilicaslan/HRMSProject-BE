package org.hrms.mapper;

import org.hrms.dto.request.CompanySaveRequestDto;
import org.hrms.dto.request.CompanySaveResponseDto;
import org.hrms.repository.entity.Company;
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
public interface ICompanyMapper {

    /*
     * Bu kod parçası, MapStruct kütüphanesini kullanarak nesne dönüşümlerini gerçekleştirmek için bir dönüşüm sınıfının(mapper interface) bir örneğini oluşturmayı sağlar.
     * MapStruct, dönüşüm sınıflarını oluşturmak için arayüzleri kullanır. Bu arayüzler, kaynak ve hedef nesneleri arasındaki alanları eşleştirmek için kullanılan yönergeleri içerir.
     * ICompanyMapper arayüzünün bir örneğini oluşturup kullanarak, bu arayüze tanımlı olan dönüşüm metotlarına erişebiliyor ve bu metotları kullanarak nesne dönüşümlerini gerçekleştirebiliyoruz.
     */
    ICompanyMapper INSTANCE = Mappers.getMapper(ICompanyMapper.class);

    Company companySaveRequestDtoToCompany(CompanySaveRequestDto dto);
    CompanySaveResponseDto companyToCompanySaveResponseDto(Company company);
}
