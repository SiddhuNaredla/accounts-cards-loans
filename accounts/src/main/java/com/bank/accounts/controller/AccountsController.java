package com.bank.accounts.controller;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dtos.CustomerDto;
import com.bank.accounts.dtos.ErrorResponseDto;
import com.bank.accounts.dtos.ResponseDto;
import com.bank.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/accounts",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(
        name="Accounts Controller",
        description = "CRUD operations on Accounts Service"
)
public class AccountsController {

    private IAccountService accountService;

    @Operation(
            summary = "post method for creating account for user",
            description = "account numbers cannot be duplicated and customer ids also"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createNewAccount(@RequestBody @Valid CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }


    @Operation(
            summary = "get method for fetching all the users",
            description = "fetching all the customers and their account details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomer(@RequestParam
                                                         @Pattern(regexp ="(^$|[0-9]{10})",message = "mobile must be 10 digits")
                                                         String mobileNumber) {
        CustomerDto customerDto=accountService.fetch(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }


    @Operation(
            summary = "put method for updating users account",
            description = "customer can update their details here"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status CREATED"
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody @Valid  CustomerDto customerDto) {
        boolean isUpdated= accountService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
        }
    }

    @Operation(
            summary = "delete method for deleting an users account",
            description = "permenantly deleting customers bank account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "INTERNAL SERVER ERROR",
            content = @Content(
                    schema =@Schema(
                            name="error response dto",
                            implementation = ErrorResponseDto.class)
            )
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam Long customerId) {
        boolean isDeleted= accountService.deleteAccount(customerId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
        }
    }
}
