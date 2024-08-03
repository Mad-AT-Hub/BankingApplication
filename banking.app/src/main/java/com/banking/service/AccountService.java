package com.banking.service;

import com.banking.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccount(Long id);

    AccountDto depositAmount(Long id ,double amount);

    AccountDto withdrawAmount(Long id, double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);
}
