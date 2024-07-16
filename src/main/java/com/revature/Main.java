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
                }
            }
        }
    }
}