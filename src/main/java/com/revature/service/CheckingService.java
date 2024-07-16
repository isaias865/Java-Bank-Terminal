package com.revature.service;

import com.revature.entity.CheckingAccount;
import com.revature.repository.CheckingDao;

import java.util.List;

public class CheckingService {

    private CheckingDao checkingDao;

    public CheckingService(CheckingDao checkingDao) {
        this.checkingDao = checkingDao;
    }

    public CheckingAccount persistNewAccount(CheckingAccount checkingAccount){
        return checkingDao.createCheckingAccount(checkingAccount);
    }

    public List<CheckingAccount> listAccounts (String owner){
        return  checkingDao.getAllCheckingAccounts(owner);
    }

    public void newDeposit(CheckingAccount checkingAccount, double amount){
        checkingDao.deposit(checkingAccount, amount);
    }

    public void newWithdraw(CheckingAccount checkingAccount, double amount){
        if(checkingAccount.getSolaris()-amount < 0){
            System.out.println("Insufficient funds");
            return;
        }
        checkingDao.deposit(checkingAccount, -amount);
    }

    public void buySpice(CheckingAccount checkingAccount, double spice, double rate){
        if(spice < 0){
            System.out.println("Must buy a positive number of spice");
            return;
        }

        double cost = spice*rate;
        if(checkingAccount.getSolaris() - cost < 0){
            System.out.println("Insufficient funds");
            return;
        }
        checkingDao.spice(checkingAccount,cost, spice);
    }

    public void deletion(CheckingAccount checkingAccount){
        checkingDao.delete(checkingAccount);
    }
}
