package com.revature.repository;

import com.revature.entity.CheckingAccount;
import com.revature.entity.User;

import java.util.List;

public interface CheckingDao {

    CheckingAccount createCheckingAccount(CheckingAccount newAccount);
    List<CheckingAccount> getAllCheckingAccounts(User user);

}
