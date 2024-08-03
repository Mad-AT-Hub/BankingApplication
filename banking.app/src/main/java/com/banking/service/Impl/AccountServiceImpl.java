package com.banking.service.Impl;

import com.banking.dto.AccountDto;
import com.banking.entity.Account;
import com.banking.mapper.AccountMapper;
import com.banking.repository.AccountRepository;
import com.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {

        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccount(Long id) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);

    }

    @Override
    public AccountDto depositAmount(Long id, double amount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new RuntimeException("Account does  not exists"));

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        Account depositedSavedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(depositedSavedAccount);
    }

    @Override
    public AccountDto withdrawAmount(Long id, double amount) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        Account withdrawlSavedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(withdrawlSavedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account -> AccountMapper.mapToAccountDto(account))).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Account not found"));

        accountRepository.deleteById(id);

    }
}
