package com.company.simplebankingsystem.dto.response;

import com.company.simplebankingsystem.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class UpdateUserResponse {
    Long id;
    String name;
    String surname;
    String email;
    String fin;
    Integer age;
    List<Long> accountIds;
    UserStatus userStatus;
    LocalDateTime updateAt;
}
