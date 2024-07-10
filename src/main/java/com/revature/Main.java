package com.revature;

import com.revature.controller.UserController;
import com.revature.entity.User;
import com.revature.repository.*;
import com.revature.service.CheckingService;
import com.revature.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
            Registration Steps
                - user needs to prompt they want to make an account
                - user needs to provide a username and password
                - system needs to check the username and password conform to software requirements
                - system needs to save the credentials if they are valid, or reject them if they are not
                - user needs to be informed of the results
         */

        try(Scanner scanner = new Scanner(System.in)){
            // this userDao will handle accessing and persisting User data
            UserDao userDao = new SqliteUserDao();
            CheckingDao checkingDao = new SqliteCheckingDao();
            // this userService will handle validating User data follows software/business rules
            // the service needs access to the dao in order to transfer data to the repository layer
            UserService userService = new UserService(userDao);
            // this userController will handle receiving and returning data to the user
            // the controller needs access to the service in order to transfer data to the service layer
            CheckingService checkingService = new CheckingService(checkingDao);
            UserController userController = new UserController(scanner, userService, checkingService);
            // this Map will update the loopApplication boolean and store the logged-in user data
            Map<String, String> controlMap = new HashMap<>();
            controlMap.put("Continue Loop", "true");
            Random rand = new Random();
            controlMap.put("Solaris/Spice exchange rate",String.valueOf(0.5 + (3.0 - 0.5) * rand.nextDouble()));
            while(Boolean.parseBoolean(controlMap.get("Continue Loop"))){
                userController.promptUserForService(controlMap);
                if(controlMap.containsKey("User")){
                    while (Boolean.parseBoolean(controlMap.get("Continue Loop"))){
                        userController.showBankPortal(controlMap);
                    }

                    /*
                    System.out.printf("Banking stuff for %s can happen here! Press any key to continue", controlMap.get("User"));
                    scanner.nextLine();
                        NOTE: currently the User information has no means of being removed: when you implement a log out
                        functionality the controlMap needs to have the User key/value pair removed:
                        - controlMap.remove("User");
                     */
                }
            }
        }
    }
}