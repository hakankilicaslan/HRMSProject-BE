package org.hrms.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.ERole;
import org.hrms.repository.enums.EStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document //MongoDB için
@SuperBuilder

public class User extends BaseEntity {
    @MongoId
    private String id;
    private Long authId;
    private String companyId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String activationLink;
    private String phoneNumber;
    private String address;
    private String info;
    private String birthday;
    @Builder.Default
    private EStatus status=EStatus.PENDING;
    private ERole role;

}
