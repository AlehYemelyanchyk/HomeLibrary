package by.epam.ayem.module6.service;

/* Задание 1: создать консольное приложение "Учет книг в домашней библиотеке".
        Общие требования к заданию:
        1) Система учитывает книги как в электронном, так и в бумажном варианте.
        2) Существующие роли: пользователь, администратор.
        3) Пользователь может просматривать книги в каталоге книг, осуществлять поиск книг в каталоге.
        4) Администратор может модифицировать каталог.
        5) *При добавлении описания книги в каталог оповещение о ней рассылается на e-mail всем пользователям.
        6) **При просмотре каталога желательно реализовывать постраничный просмотр.
        7) ***Пользователь может предложить добавить книгу в библиотеку, переслав ее администратору на e-mail.
        8) Каталог книг хранится в текстовом файле.
        9) Данные аутонтефикации пользователей хранятся в текстовом файле. Пароль не хранится в открытом виде.*/


import by.epam.ayem.module6.model.*;

import java.util.Scanner;

public class HomeLibraryApp {

    private static Scanner scanner = new Scanner(System.in);
    private HomeLibraryService homeLibraryService = new HomeLibraryService();
    private CatalogBase catalogBase = new CatalogBase();
    private UserBase userBase = new UserBase();
    private UserService userService = new UserService();

    public void run() {
        homeLibraryService.readWhenStart(userBase, catalogBase);

        System.out.println("Welcome to the library service.");

        boolean quit = false;

        while (!quit) {
            System.out.println("\n1. Log in.\n" +
                    "2. Sign Up.\n" +
                    "0. Quit.");

            while (!scanner.hasNextInt()) {
                scanner.next();
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (userService.logIn(userBase)) {
                        homeLibraryService.showMenu(userBase, catalogBase);
                    }
                    break;
                case 2:
                    if (userService.signUp(userBase)) {
                        homeLibraryService.showMenu(userBase, catalogBase);
                    }
                    break;
                case 0:
                    homeLibraryService.writeWhenFinish(userBase, catalogBase);
                    quit = true;

            }
        }
    }


}
