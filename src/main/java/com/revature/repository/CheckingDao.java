package com.revature.repository;

import com.revature.entity.CheckingAccount;
import com.revature.entity.User;

import java.util.List;

public interface CheckingDao {

    CheckingAccount createCheckingAccount(CheckingAccount newAccount);
    List<CheckingAccount> getAllCheckingAccounts(String user);
    void deposit(CheckingAccount account, double amount);
    void spice(CheckingAccount account, double cost, double spice);
    void delete(CheckingAccount account);
}
