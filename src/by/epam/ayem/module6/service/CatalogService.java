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

public class CatalogService {

    private static Scanner scanner = new Scanner(System.in);

    public void showCatalogs(CatalogBase catalogBase) {
        if (catalogBase == null || catalogBase.getCatalogs().isEmpty()) {
            System.out.println("There are no catalogs in the library.");
        } else {
            System.out.println("Catalogs: ");
            for (int i = 0; i < catalogBase.getCatalogs().size(); i++) {
                System.out.println((i + 1) + ". " + catalogBase.getCatalogs().get(i).getTitle() + ".");
            }
            System.out.println("0. Menu.");
        }
    }

    public void createCatalog(CatalogBase catalogBase) {
        System.out.println("Enter a catalog title:");
        String title = scanner.nextLine() + ".txt";
        if (isCatalogExist(catalogBase, title)) {
            System.out.println("The catalog with the title '" + title + "' already exists.");
        } else {
            catalogBase.getCatalogs().add(new Catalog(title));
            System.out.println("The catalog '" + title + "' has been created.");
        }
    }

    private boolean isCatalogExist(CatalogBase catalogBase, String title) {
        if (catalogBase == null || catalogBase.getCatalogs().isEmpty()) {
            return false;
        } else {
            for (Catalog catalog : catalogBase.getCatalogs()) {
                if (catalog.getTitle().equalsIgnoreCase(title)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteCatalog(CatalogBase catalogBase) {
        System.out.println("Choose a catalog to delete:");
        int number = getNumber();

        if (isCatalogNumber(catalogBase, number)) {
            catalogBase.getCatalogs().remove(number - 1);
        }
    }

    private boolean isCatalogNumber(CatalogBase catalogBase, int number) {
        if (catalogBase == null || catalogBase.getCatalogs().isEmpty()) {
            return false;
        } else {
            return number > 0 && number < catalogBase.getCatalogs().size();
        }
    }

    private int getNumber() {
        int number;
        while (!scanner.hasNextInt()) {
            scanner.next();
        }

        number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public void showCatalogContent(CatalogBase catalogBase) {
        System.out.println("Choose a catalog to show:");
        int number = getNumber();
        showCatalogContent(catalogBase.getCatalogs().get(number - 1));
    }

    private void showCatalogContent(Catalog catalog) {
        int page = 0;
        int count = 0;
        for (Book book : catalog.getBooks()) {
            if (count % 4 == 0) {
                System.out.println("Page " + (page++) + ":");
            }
            System.out.println(book);
            count++;
        }
    }

    public Catalog takeCatalog(CatalogBase catalogBase) {
        System.out.println("Choose a catalog:");
        int number = getNumber();

        if (isCatalogNumber(catalogBase, number)) {
            return catalogBase.getCatalogs().get(number - 1);
        }
        return null;
    }
}
