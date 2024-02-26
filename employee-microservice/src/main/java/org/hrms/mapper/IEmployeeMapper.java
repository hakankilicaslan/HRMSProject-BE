package org.hrms.mapper;

import org.hrms.dto.request.EmployeeCreateRequestDto;
import org.hrms.dto.request.EmployeeUpdateRequestDto;
import org.hrms.dto.response.EmployeeCreateResponseDto;
import org.hrms.dto.response.FindAllEmployeesResponseDto;
import org.hrms.dto.response.FindEmployeeByIdResponseDto;
import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.hrms.rabbitmq.model.EmployeeCreateModel;
import org.hrms.repository.entity.Employee;
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
public interface IEmployeeMapper {

    /*
     * Bu kod parçası, MapStruct kütüphanesini kullanarak nesne dönüşümlerini gerçekleştirmek için bir dönüşüm sınıfının(mapper interface) bir örneğini oluşturmayı sağlar.
     * MapStruct, dönüşüm sınıflarını oluşturmak için arayüzleri kullanır. Bu arayüzler, kaynak ve hedef nesneleri arasındaki alanları eşleştirmek için kullanılan yönergeleri içerir.
     * IEmployeeMapper arayüzünün bir örneğini oluşturup kullanarak, bu arayüze tanımlı olan dönüşüm metotlarına erişebiliyor ve bu metotları kullanarak nesne dönüşümlerini gerçekleştirebiliyoruz.
     */
    IEmployeeMapper INSTANCE = Mappers.getMapper(IEmployeeMapper.class);

    Employee employeeCreateRequestDtoToEmployee(EmployeeCreateRequestDto dto);
    EmployeeCreateResponseDto employeeToEmployeeCreateResponseDto(Employee employee);
    EmployeeCreateModel employeeToEmployeeCreateModel(Employee employee);
    AuthUpdateModel employeeToAuthUpdateModel(Employee employee);
    FindAllEmployeesResponseDto employeeToFindAllEmployeesResponseDto(Employee employee);
    FindEmployeeByIdResponseDto employeeToFindEmployeeByIdResponseDto(Employee employee);

}
