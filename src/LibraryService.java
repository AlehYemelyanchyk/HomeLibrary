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

import java.io.*;
import java.util.*;

public class LibraryService {

    private List<Catalog> catalogs;
    private Scanner scanner = new Scanner(System.in);
    private User currentUser;
    private String CATALOGS_FILE = "Catalogs.txt";
    private Writer writer = new Writer();
    private Reader reader = new Reader();
    private UserBase userBase;

    LibraryService() {
        this.catalogs = new ArrayList<>();
        readWhenStart();
    }

    private void readWhenStart() {
        try {
            reader.readCatalogFromFile(catalogs, CATALOGS_FILE);
            reader.readBooksFromFile(catalogs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showMenu(UserBase userBase) {

        currentUser = userBase.getCurrentUser();
        this.userBase = userBase;

        if (currentUser.getRole() == UserRole.ADMINISTRATOR) {
            showAdminMenu();
        } else {
            showUserMenu();
        }
    }

    private void showAdminMenu() {

        boolean logOut = false;

        while (!logOut) {
            System.out.println("\n" + userBase.getCurrentUser().getRole() + " "
                    + userBase.getCurrentUser().getName());
            System.out.println("1. Show catalogs.\n" +
                    "2. Create catalog.\n" +
                    "3. Delete catalog.\n" +
                    "4. View a catalog.\n" +
                    "5. Search for a book in the catalog.\n" +
                    "6. Add book.\n" +
                    "7. Delete book.\n" +
                    "0. Log out.");

            while (!scanner.hasNextInt()) {
                scanner.next();
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showCatalogs();
                    break;
                case 2:
                    System.out.println("Enter a catalog title:");
                    String catalogTitle = scanner.nextLine();
                    createCatalog(catalogTitle);
                    break;
                case 3:
                    System.out.println("Choose the catalog:");
                    showCatalogs();
                    System.out.println("0. Menu.");

                    int numberDelete = checkCatalogNumber();

                    if (numberDelete != 0) {
                        deleteCatalog(numberDelete);
                    }
                    break;
                case 4:
                    System.out.println("Choose the catalog:");
                    showCatalogs();
                    System.out.println("0. Menu.");

                    int numberShow = checkCatalogNumber();

                    if (numberShow != 0) {
                        showCatalogContent(numberShow);
                    }
                    break;
                case 5:
                    System.out.println("Enter a book title:");
                    String title = scanner.nextLine();
                    showBookTypes();
                    System.out.println("Choose a book type:");

                    while (!scanner.hasNextInt()) {
                        scanner.next();
                    }

                    int number = scanner.nextInt();
                    scanner.nextLine();

                    doesBookExist(title, getBookType(number));
                    break;
                case 6:
                    System.out.println("Choose a number of the catalog:");
                    showCatalogs();
                    System.out.println("0. Menu.");

                    int catalogNumber = checkCatalogNumber();

                    if (catalogNumber != 0) {
                        catalogTitle = catalogs.get(catalogNumber - 1).getCatalogTitle();

                        System.out.println("Enter a book title:");
                        String bookTitle = scanner.nextLine();

                        System.out.println("Choose a book type number:");
                        BookType.showNumbers();

                        int bookTypeNumber = checkBookTypeNumber();

                        System.out.println("White a description of the book:");
                        String description = scanner.nextLine();

                        addBook(catalogTitle, bookTitle, getBookType(bookTypeNumber), description);
                    }
                    break;
                case 7:
                    System.out.println("Enter a book title:");
                    title = scanner.nextLine();
                    showBookTypes();
                    System.out.println("Choose a book type:");

                    while (!scanner.hasNextInt()) {
                        scanner.next();
                    }

                    number = scanner.nextInt();
                    scanner.nextLine();

                    deleteBook(title, getBookType(number));
                    break;
                case 0:
                    logOut = true;
                    userBase.setCurrentUser(null);

            }
        }
    }

    private void showUserMenu() {

        boolean logOut = false;

        while (!logOut) {
            System.out.println("\n" + userBase.getCurrentUser().getRole() + " "
                    + userBase.getCurrentUser().getName());
            System.out.println("1. Show catalogs.\n" +
                    "2. View a catalog.\n" +
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
                    showCatalogs();
                    break;
                case 2:
                    System.out.println("Choose the catalog:");
                    showCatalogs();
                    System.out.println("0. Menu.");

                    int numberShow = checkCatalogNumber();

                    if (numberShow != 0) {
                        showCatalogContent(numberShow);
                    }
                    break;
                case 3:
                    System.out.println("Enter a book title:");
                    String title = scanner.nextLine();
                    showBookTypes();
                    System.out.println("Choose a book type:");

                    while (!scanner.hasNextInt()) {
                        scanner.next();
                    }

                    int number = scanner.nextInt();
                    scanner.nextLine();

                    doesBookExist(title, getBookType(number));
                    break;
                case 4:
                    System.out.println("Choose a number of the catalog:");
                    showCatalogs();

                    while (!scanner.hasNextInt()) {
                        scanner.next();
                    }

                    int catalogNumber = scanner.nextInt();
                    scanner.nextLine();
                    String catalogTitle = catalogs.get(catalogNumber - 1).getCatalogTitle();

                    System.out.println("Enter a book title:");
                    String bookTitle = scanner.nextLine();

                    System.out.println("Choose a book type number:");
                    BookType.showNumbers();

                    while (!scanner.hasNextInt()) {
                        scanner.next();
                    }

                    int number2 = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("White a description of the book:");
                    String description = scanner.nextLine();

                    addBook(catalogTitle, bookTitle, getBookType(number2), description);

                    break;
                case 0:
                    logOut = true;
                    userBase.setCurrentUser(null);

            }
        }
    }

    private void showBookTypes() {
        int number = 1;
        for (BookType type : BookType.values()) {
            System.out.println(number + ". " + type);
            number++;
        }
    }

    private BookType getBookType(int number) {
        BookType bookType = null;
        for (BookType type : BookType.values()) {
            if (type.getNumber() == number) {
                bookType = type;
            }
        }
        return bookType;
    }

    private int checkCatalogNumber() {
        boolean quit = false;
        int number = 0;
        while (!quit) {

            while (!scanner.hasNextInt()) {
                scanner.next();
            }

            number = scanner.nextInt();
            scanner.nextLine();

            if (number < 0 || number > catalogs.size()) {
                quit = false;
            } else {
                quit = true;
            }
        }
        return number;
    }

    void addBook(String catalogTitle, String title, BookType bookType, String description) {
        Book newBook = new Book(title, bookType, description);

        for (Catalog catalog : catalogs) {
            if (catalog.getCatalogTitle().equals(catalogTitle)) {
                catalog.addBook(newBook);
                writer.writeBookToFile(catalogTitle, newBook);
            }
        }
    }

    void deleteBook(String title, BookType bookType) {
        for (Catalog catalog : catalogs) {
            catalog.deleteBookFromCatalog(title, bookType);
        }
        System.out.println("The book '" + title + " in such type was not found.");
    }

    boolean doesBookExist(String title, BookType bookType) {
        for (Catalog catalog : catalogs) {
            Book book = catalog.isInCatalog(title, bookType);
            if (book != null) {
                return true;
            }
        }
        System.out.println("The book '" + title + " in such type was not found.");
        return false;
    }

    void showCatalogs() {
        ListIterator<Catalog> catalogIterator = catalogs.listIterator();

        if (catalogs == null || catalogs.isEmpty()) {
            System.out.println("There are no catalogs in the library.");
        } else {
            System.out.println("Catalogs: ");
            while (catalogIterator.hasNext()) {
                System.out.println((catalogIterator.nextIndex() + 1) + ". " + catalogIterator.next().toString() + ".");
            }
        }
    }

    private int checkBookTypeNumber() {
        boolean quit = false;
        int number = 0;
        while (!quit) {

            while (!scanner.hasNextInt()) {
                scanner.next();
            }

            number = scanner.nextInt();
            scanner.nextLine();

            if (number < 1 || number > BookType.values().length) {
                quit = false;
            } else {
                quit = true;
            }
        }
        return number;
    }

    void showCatalogContent(int number) {
        for (int i = 0; i < catalogs.size(); i++) {
            if (i == (number - 1)) {
                catalogs.get(i).showCatalog();
            }
        }
    }

    void createCatalog(String catalogTitle) {
        if (isCatalogExist(catalogTitle)) {
            System.out.println("The catalog with the title '" + catalogTitle + "' already exists.");
        } else {
            catalogs.add(new Catalog(catalogTitle));
            System.out.println("The catalog '" + catalogTitle + "' has been created.");
            writer.writeCatalogToFile(catalogTitle, CATALOGS_FILE);
            try {
                new FileWriter(catalogTitle + ".txt", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void deleteCatalog(int number) {
        String catalogTitle = catalogs.get(number - 1).getCatalogTitle() + ".txt";

        deleteFile(CATALOGS_FILE);
        deleteFile(catalogTitle);

        catalogs.remove(number - 1);

        for (
                Catalog catalog : catalogs) {
            writer.writeCatalogToFile(catalog.getCatalogTitle(), CATALOGS_FILE);
        }

    }

    private boolean isCatalogExist(String catalogTitle) {
        if (catalogs == null || catalogs.isEmpty()) {
            return false;
        } else {
            for (Catalog catalog : catalogs) {
                if (catalog.getCatalogTitle().equalsIgnoreCase(catalogTitle)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

}
