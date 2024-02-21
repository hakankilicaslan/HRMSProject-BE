package org.hrms.mapper;

import org.hrms.dto.request.GuestUpdateRequestDto;
import org.hrms.dto.response.FindAllGuestsResponseDto;
import org.hrms.dto.response.FindGuestByIdResponseDto;
import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.hrms.repository.entity.Guest;
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
public interface IGuestMapper {

    /*
     * Bu kod parçası, MapStruct kütüphanesini kullanarak nesne dönüşümlerini gerçekleştirmek için bir dönüşüm sınıfının(mapper interface) bir örneğini oluşturmayı sağlar.
     * MapStruct, dönüşüm sınıflarını oluşturmak için arayüzleri kullanır. Bu arayüzler, kaynak ve hedef nesneleri arasındaki alanları eşleştirmek için kullanılan yönergeleri içerir.
     * IGuestMapper arayüzünün bir örneğini oluşturup kullanarak, bu arayüze tanımlı olan dönüşüm metotlarına erişebiliyor ve bu metotları kullanarak nesne dönüşümlerini gerçekleştirebiliyoruz.
     */
    IGuestMapper INSTANCE = Mappers.getMapper(IGuestMapper.class);

    Guest guestUpdateRequestDtoToGuest(GuestUpdateRequestDto dto);
    AuthUpdateModel guestToAuthUpdateModel(Guest guest);
    FindAllGuestsResponseDto guestToFindAllGuestsResponseDto(Guest guest);
    FindGuestByIdResponseDto guestToFindGuestByIdResponseDto(Guest guest);
}
