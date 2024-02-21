package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.ERole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeRequestDto {
    private String username;
    private String email;
    private String password;
    private ERole role;
    private String name;
    private String surname;
    private String phone;
    private String address;
    private String info;
    private String avatar;
    private String birthday;
}
