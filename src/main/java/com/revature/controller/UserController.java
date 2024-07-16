package com.revature.controller;

import com.revature.entity.CheckingAccount;
import com.revature.exception.LoginFail;
import com.revature.service.CheckingService;
import com.revature.service.UserService;
import com.revature.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserController {

    private Scanner scanner;
    private UserService userService;
    private CheckingService checkingService;

    /*
        The controller takes in a scanner and service object
            - scanner is defined outside the class and passed in, so we can
              control closing the scanner when we are done in an easier way
              (try with resources)
            - userService gives the controller access to the service layer, which will
              handle enforcing our business and software requirements
     */
    public UserController(Scanner scanner, UserService userService, CheckingService checkingService){
        this.scanner = scanner;
        this.userService = userService;
        this.checkingService = checkingService;
    }

    /*
        this promptUserForService method is our entrypoint to the bank application:
        it currently contains code to handle registering an account or exiting the
        app, but this can be refactored to handle more options (like logging). It
        also can be refactored to call helper methods to control the flow of the application
        in a more organized and readable way
     */
    public void promptUserForService(Map<String,String> controlMap){
        // user needs to prompt they want to make an account
        System.out.println("Welcome to the CHOAM (Combine Honnete Ober Advancer Mercantiles) Banking Application!");
        System.out.printf("Current Solaris/Spice exchange rate: %s\n", controlMap.get("Solaris/Spice exchange rate"));
        System.out.println("What would you like to do?");
        System.out.println("1. Register an account");
        System.out.println("2. Login");
        System.out.println("q. quit");
        try{
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    registerNewUser();
                    break;
                case "2":
                    controlMap.put("User", login().getUsername());
                    break;
                case "q":
                    System.out.println("Goodbye!");
                    controlMap.put("Continue Loop", "false");
            }
        } catch(LoginFail exception){
            System.out.println(exception.getMessage());
        }
    }

    public void registerNewUser(){
        User newCredentials = getUserCredentials();
        User newUser = userService.validateNewCredentials(newCredentials);
        System.out.printf("New account created: %s", newUser);
    }

    public void createNewCheckingAccount(Map<String,String> controlMap){
        CheckingAccount newCheckingAccountInfo = getAccountCredentials(controlMap.get("User"));
        CheckingAccount newCheckingAccount = checkingService.persistNewAccount(newCheckingAccountInfo);
        System.out.printf("New account created: %s", newCheckingAccount);
    }

    public void editAccountDetails(Map<String,String> controlMap){
        String accountName;
        double amount;
        List<CheckingAccount> checkingAccountList = checkingService.listAccounts(controlMap.get("User"));

        if(checkingAccountList.isEmpty()){
            System.out.println("This user has no checking accounts");
            return;
        }

        System.out.println("Please enter the name of the account: ");
        accountName = scanner.nextLine();

        for(CheckingAccount i: checkingAccountList){
            if(accountName.equals(i.getName())){
                System.out.println("What would you like to do?");
                System.out.println("1. Deposit Solaris");
                System.out.println("2. Withdraw Solaris");
                System.out.println("3. Buy Spice");
                System.out.println("4. Go back");
                System.out.println("q. quit");

                String userActionIndicated = scanner.nextLine();
                switch (userActionIndicated){
                    case "1":
                        System.out.println("How much to deposit? ");
                        amount = Integer.parseInt(scanner.nextLine());
                        checkingService.newDeposit(i,amount);
                        break;
                    case "2":
                        System.out.println("How much to withdraw? ");
                        amount = Integer.parseInt(scanner.nextLine());
                        checkingService.newWithdraw(i,amount);
                        break;
                    case "3":
                        System.out.printf("Current Solaris/Spice exchange rate: %s\n", controlMap.get("Solaris/Spice exchange rate"));
                        System.out.println("How much spice would you like to buy? ");
                        amount = Integer.parseInt(scanner.nextLine());
                        checkingService.buySpice(i,amount, Double.parseDouble(controlMap.get("Solaris/Spice exchange rate")));
                        break;
                    case "4":
                        return;
                    case "q":
                        System.out.println("Goodbye!");
                        controlMap.put("Continue Loop", "false");
                }
                break;
            }
        }
    }

    public void displayAccountDetails(Map<String,String> controlMap){
        List<CheckingAccount> checkingAccountList = checkingService.listAccounts(controlMap.get("User"));

        if(checkingAccountList.isEmpty()){
            System.out.println("This user has no checking accounts");
            return;
        }

        for(CheckingAccount i: checkingAccountList){
            System.out.println(i);
        }
    }

    public User login(){
        return userService.checkLoginCredentials(getUserCredentials());
    }

    public User getUserCredentials(){
        String newUsername;
        String newPassword;
        // user needs to provide a username and password
        System.out.print("Please enter a username: ");
        newUsername = scanner.nextLine();
        System.out.print("Please enter a password: ");
        newPassword = scanner.nextLine();
        return new User(newUsername, newPassword);
    }

    public CheckingAccount getAccountCredentials(String owner){
        int solaris;
        String accountName;

        System.out.print("Please enter a name for the account: ");
        accountName = scanner.nextLine();

        while (true){
            System.out.print("Please enter the amount of solaris to deposit: ");
            solaris = Integer.parseInt(scanner.nextLine());
            if(solaris < 0){
                System.out.println("Must deposit a positive number of solaris");
            }
            else break;
        }
        return new CheckingAccount(owner, accountName, solaris, 0);
    }

    public void deleteAccount(Map<String,String> controlMap){
        List<CheckingAccount> checkingAccountList = checkingService.listAccounts(controlMap.get("User"));
        String accountName;
        if(checkingAccountList.isEmpty()){
            System.out.println("This user has no checking accounts");
            return;
        }

        System.out.print("Please enter the name for the account to be deleted: ");
        accountName = scanner.nextLine();

        for(CheckingAccount i: checkingAccountList){
            if(accountName.equals(i.getName())){
                checkingService.deletion(i);
            }
        }
    }

    public void showBankPortal(Map<String,String> controlMap){

        System.out.println("What would you like to do?");
        System.out.println("1. Create a checking account");
        System.out.println("2. Deposit or withdraw from a checking account");
        System.out.println("3. View account details of your checking accounts");
        System.out.println("4. Close a checking account");
        System.out.println("q. quit");

        String userActionIndicated = scanner.nextLine();
        //System.out.println(userActionIndicated);
        switch (userActionIndicated){
            case "1":
                createNewCheckingAccount(controlMap);
                break;
            case "2":
                editAccountDetails(controlMap);
                break;
            case "3":
                displayAccountDetails(controlMap);
                break;
            case "4":
                deleteAccount(controlMap);
                break;
            case "q":
                System.out.println("Goodbye!");
                controlMap.put("Continue Loop", "false");
        }
    }



}