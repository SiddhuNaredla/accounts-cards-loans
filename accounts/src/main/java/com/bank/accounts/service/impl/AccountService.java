package com.bank.accounts.service.impl;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dtos.AccountsDto;
import com.bank.accounts.dtos.CustomerDto;
import com.bank.accounts.exception.CustomerAlreadyExistsException;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.AccountsMapper;
import com.bank.accounts.mapper.CustomerMapper;
import com.bank.accounts.model.Accounts;
import com.bank.accounts.model.Customer;
import com.bank.accounts.repository.AccountsRepository;
import com.bank.accounts.repository.CustomerRepository;
import com.bank.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;

    /**
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
             Customer customer=CustomerMapper.mapToCustomer(customerDto,new Customer());
             Optional<Customer> customerOptional=customerRepository.findByMobileNumber(customer.getMobileNumber());
             if(customerOptional.isPresent()){
                 throw new CustomerAlreadyExistsException("Customer already exists");
             }
             customer.setCreatedBy("anonymous");
             customer.setCreatedAt(LocalDateTime.now());
             Customer savedCustomer=customerRepository.save(customer);
             System.out.println(savedCustomer);
             accountsRepository.save(createNewAccount(savedCustomer));
    }


    private Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        Long accountNumber=100000000L+new Random().nextInt(900000000);

        accounts.setAccountNumber(accountNumber);
        accounts.setCreatedBy("anonymous");
        accounts.setCreatedAt(LocalDateTime.now());
        return accounts;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetch(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("User not found for this mobileNumber")
        );
        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account not found for this mobileNumber")
        );
        AccountsDto accountsDto= AccountsMapper.mapToAccountsDto(accounts,new AccountsDto());
        CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    /**
     * @param customerDto
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated=false;
        AccountsDto accountsDto=customerDto.getAccountsDto();
        if(accountsDto!=null){
            Accounts accounts=accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account not found for this accountNumber")
            );
            accounts=AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts=accountsRepository.save(accounts);

            Long customerId=accounts.getCustomerId();
            Customer customer=customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer not found for this accountNumber")
            );
            customer=CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    /**
     * @param customerId
     */
    @Override
    public boolean deleteAccount(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found for this id")
        );
        customerRepository.delete(customer);
        accountsRepository.deleteByCustomerId(customerId);
        return true;
    }

}
