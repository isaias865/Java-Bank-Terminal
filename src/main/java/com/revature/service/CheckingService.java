package com.revature.service;

import com.revature.entity.CheckingAccount;
import com.revature.repository.CheckingDao;

public class CheckingService {

    private CheckingDao checkingDao;

    public CheckingService(CheckingDao checkingDao) {
        this.checkingDao = checkingDao;
    }

    public CheckingAccount persistNewAccount(CheckingAccount checkingAccount){
        return checkingDao.createCheckingAccount(checkingAccount);
    }
}
