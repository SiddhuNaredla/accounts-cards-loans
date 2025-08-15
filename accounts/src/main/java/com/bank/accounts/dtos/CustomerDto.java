package com.bank.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name="customer dto",
        description = "customer dto which will interact with customer db"
)
public class CustomerDto {

    @NotEmpty(message = "name not be empty or null")
    @Size(min = 5,max = 30,message = "name must in size 5 and 30")
    @Schema(
            name="name",
            example = "abc"
    )
    private String name;

    @NotEmpty(message = "emial not be null or empty")
    @Email(message = "emial address should be a valid value")
    @Schema(
            name="email",
            example="abc@gmial.com"
    )
    private String email;

    @Pattern(regexp ="(^$|[0-9]{10})",message = "mobile must be 10 digits")
    @Schema(
            name="mobileNumber",
            example = "123xxxxxx"
    )
    private String mobileNumber;
    @Schema(
            name = "accountsDto"
    )
    private AccountsDto accountsDto;

}
