package org.hrms.mapper;



import org.hrms.dto.request.CreateEmployeeRequestDto;
import org.hrms.dto.request.EmployeeInfoUpdateDto;
import org.hrms.dto.request.GuestInfoUpdateDto;
import org.hrms.repository.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    User createEmployeeRequestDtoToUser(CreateEmployeeRequestDto createEmployeeRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User fromEmployeeInfoUpdateRequestDtoToUser(EmployeeInfoUpdateDto dto, @MappingTarget User user);

    User fromGuestInfoUpdateRequestDtoToUser(final GuestInfoUpdateDto dto, @MappingTarget User user);

//    //User fromRegisterModelToUserProfile(final UserRegisterModel model);
//
//   // UserCompanyListModel userCompanyListModelFromUser(final User x);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    User fromCompanyManagerRegisterModelToUser(final CompanyManagerRegisterModel companyManagerRegisterModel);
//
//    User fromGuestRegisterModelToUser(final GuestRegisterModel guestRegisterModel);
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    User fromEmployeeInfoUpdateRequestDtoToUser(final EmployeeInfoUpdateDto dto, @MappingTarget User user);
//
//    User fromAddEmployeeSaveUserModelToUser(final AddEmployeeSaveUserModel addEmployeeSaveUserModel);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    GetCompanyEmployeesResponseModel fromUserToGetCompanyEmployeesResponseModel(final User user);
//
//    User fromGuestInfoUpdateRequestDtoToUser(final GuestInfoUpdateDto dto, @MappingTarget User user);

}
