package com.company.simplebankingsystem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class CreateUserRequest {
    @NotNull
    String name;
    @NotNull
    String surname;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email format is invalid")
    String email;
    @Size(min = 7, max = 7, message = "FIN must be exactly 7 characters")
    String fin;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    Integer age;
}
