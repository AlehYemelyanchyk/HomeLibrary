package by.epam.ayem.module6.service;

import by.epam.ayem.module6.model.*;

import java.util.Scanner;

public class UserService {

    private Scanner scanner = new Scanner(System.in);

    public boolean logIn(UserBase userBase) {
        System.out.println("Enter a name:");
        String name = scanner.nextLine();
        System.out.println("Enter an e-mail:");
        String email = scanner.nextLine();
        System.out.println("Enter a password:");
        String password = scanner.nextLine();
        for (User user : userBase.getUsers()) {
            boolean sameUser = isSameUser(name, email, password, user);
            if (sameUser) {
                userBase.setCurrentUser(user);
                System.out.println("Welcome, " + user.getName() + ".");
                return true;
            }
        }
        System.out.println("The user has not found.");
        return false;
    }

    private boolean logIn(UserBase userBase, String name, String email, String password) {
        for (User user : userBase.getUsers()) {
            boolean sameUser = isSameUser(name, email, password, user);
            if (sameUser) {
                userBase.setCurrentUser(user);
                System.out.println("Welcome, " + user.getName() + ".");
                return true;
            }
        }
        System.out.println("The user has not found.");
        return false;
    }

    private boolean isSameUser(String name, String email, String password, User user) {
        return user.getName().equals(name) &&
                user.getEmail().equals(email) &&
                user.getPassword().equals(String.valueOf(password.hashCode()));
    }

    public boolean signUp(UserBase userBase) {
        System.out.println("Enter a name:");
        String name = scanner.nextLine();
        System.out.println("Enter an e-mail:");
        String email = scanner.nextLine();
        System.out.println("Enter a password:");
        String password = scanner.nextLine();

        if (userBase.getUsers().isEmpty()) {
            userBase.getUsers().add(new User(name, email, String.valueOf(password.hashCode()), UserRole.ADMINISTRATOR));
            System.out.println("The administrator has been created.");
            return logIn(userBase, name, email, password);
        }

        User newUser = new User(name, email, String.valueOf(password.hashCode()), UserRole.USER);

        if (!isUserExist(userBase, newUser)) {
            userBase.getUsers().add(newUser);
            System.out.println("The new account has been created.");
        } else {
            System.out.println("A user with such name already exists.");
            return false;
        }
        return logIn(userBase, name, email, password);
    }

    private boolean isUserExist(UserBase userBase, User newUser) {
        for (User user : userBase.getUsers()) {
            if (user.equals(newUser))
                return false;
        }
        return true;
    }
}
