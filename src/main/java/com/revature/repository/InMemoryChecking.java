package com.revature.repository;

import com.revature.entity.CheckingAccount;
import com.revature.entity.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryChecking implements CheckingDao{

    private List<CheckingAccount> checkingAccounts;

    public InMemoryChecking(String owner){
        checkingAccounts = new ArrayList<>();
        checkingAccounts.add(new CheckingAccount(owner,0,0));
    }

    @Override
    public CheckingAccount createCheckingAccount(CheckingAccount newAccount) {
        checkingAccounts.add(newAccount);
        return newAccount;
    }

    @Override
    public List<CheckingAccount> getAllCheckingAccounts(User user) {
        return checkingAccounts;
    }
}
