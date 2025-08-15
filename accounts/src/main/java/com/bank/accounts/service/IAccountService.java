package com.bank.accounts.service;

import com.bank.accounts.dtos.CustomerDto;

public interface IAccountService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetch(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(Long customerId);
}
