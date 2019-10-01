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

import java.util.*;

public class HomeLibraryService {

    private static Scanner scanner = new Scanner(System.in);
    private HomeLibraryRepository homeLibraryRepository = new HomeLibraryRepository();
    private CatalogService catalogService = new CatalogService();
    private BookService bookService = new BookService();

    public void readWhenStart(UserBase userBase, CatalogBase catalogBase) {
        homeLibraryRepository.readCatalogFromFile(catalogBase, "Catalogs.txt");
        for (Catalog catalog : catalogBase.getCatalogs()) {
            List<Book> books = homeLibraryRepository.readBooksFromFile(catalog.getTitle());
            catalog.setBooks(books);
        }
        homeLibraryRepository.readUsersFromFile(userBase, "Users.txt");
    }

    public void writeWhenFinish(UserBase userBase, CatalogBase catalogBase) {
        for (Catalog catalog : catalogBase.getCatalogs()) {
            homeLibraryRepository.writeCatalogToFile(catalog.getTitle(), "Catalogs.txt");
        }
        for (Catalog catalog : catalogBase.getCatalogs()) {
            homeLibraryRepository.writeBooksToFile(catalog, catalog.getTitle());
        }
        for (User user : userBase.getUsers()) {
            homeLibraryRepository.writeUserToFile(userBase, user, "Users.txt");
        }
    }

    public void showMenu(UserBase userBase, CatalogBase catalogBase) {

        User currentUser = userBase.getCurrentUser();

        if (currentUser.getRole() == UserRole.ADMINISTRATOR) {
            showAdminMenu(userBase, currentUser, catalogBase);
        } else {
            showUserMenu(userBase, currentUser, catalogBase);
        }
    }

    private void showAdminMenu(UserBase userBase, User user, CatalogBase catalogBase) {

        boolean logOut = false;

        while (!logOut) {
            System.out.println("\n" + user.getRole() + " "
                    + user.getName());
            System.out.println("1. Show catalogs.\n" +
                    "2. Create catalog.\n" +
                    "3. Delete catalog.\n" +
                    "4. View catalog.\n" +
                    "5. Search for a book in the catalog.\n" +
                    "6. Add book.\n" +
                    "7. Delete book.\n" +
                    "0. Log out.");

            int choice = getInteger();

            switch (choice) {
                case 1:
                    catalogService.showCatalogs(catalogBase);
                    break;
                case 2:
                    catalogService.createCatalog(catalogBase);
                    break;
                case 3:
                    catalogService.showCatalogs(catalogBase);
                    catalogService.deleteCatalog(catalogBase);
                    break;
                case 4:
                    catalogService.showCatalogs(catalogBase);
                    catalogService.showCatalogContent(catalogBase);
                    break;
                case 5:
                    bookService.showBook(catalogBase);
                    break;
                case 6:
                    catalogService.showCatalogs(catalogBase);
                    Catalog catalog = null;
                    while (catalog == null) {
                        catalog = catalogService.takeCatalog(catalogBase);
                    }
                    bookService.addBook(catalogBase, catalog);
                    break;
                case 7:
                    bookService.deleteBook(catalogBase);
                    break;
                case 0:
                    logOut = true;
                    userBase.setCurrentUser(null);

            }
        }
    }

    private int getInteger() {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int integer = scanner.nextInt();
        scanner.nextLine();
        return integer;
    }

    private void showUserMenu(UserBase userBase, User user, CatalogBase catalogBase) {

        boolean logOut = false;

        while (!logOut) {
            System.out.println("\n" + user.getRole() + " "
                    + user.getName());
            System.out.println("1. Show catalogs.\n" +
                    "2. View catalog.\n" +
                    "3. Search for a book in the catalog.\n" +
                    "4. Add book request.\n" +
                    "0. Log out.");

            while (!scanner.hasNextInt()) {
                scanner.next();
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    catalogService.showCatalogs(catalogBase);
                    break;
                case 2:
                    catalogService.showCatalogs(catalogBase);
                    catalogService.showCatalogContent(catalogBase);
                    break;
                case 3:
                    bookService.showBook(catalogBase);
                case 4:
                    catalogService.showCatalogs(catalogBase);
                    Catalog catalog = null;
                    while (catalog == null) {
                        catalog = catalogService.takeCatalog(catalogBase);
                    }
                    bookService.addBookRequest(catalogBase, catalog);
                    break;
                case 0:
                    logOut = true;
                    userBase.setCurrentUser(null);

            }
        }
    }
}
