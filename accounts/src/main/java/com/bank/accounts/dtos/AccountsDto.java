package com.bank.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name="Accounts Dto",
        description = "Accounts Dto for interacting wih Acoounts model"
)
public class AccountsDto {

    @NotEmpty(message = "account number not be null or empty")
    @Pattern(regexp ="(^$|[0-9]{10})",message = "mobile must be 10 digits")
    @Schema(
            name="account number",
            example = "1234567890"
    )
    private Long accountNumber;
    @NotEmpty(message = "account type not be null or empty")
    @Schema(
            name="account type",
            example="savings"
    )
    private String accountType;
    @NotEmpty(message = "branch address not null or empty")
    @Schema(
            name="branch Address",
            example = "abc street,US"
    )
    private String branchAddress;

}
