package by.epam.ayem.module6.service;

import by.epam.ayem.module6.model.Book;
import by.epam.ayem.module6.model.BookType;
import by.epam.ayem.module6.model.Catalog;
import by.epam.ayem.module6.model.CatalogBase;

import java.io.File;
import java.util.Scanner;

/**
 * @author Aleh Yemelyanchyk on 9/30/2019.
 */
public class BookService {

    private Scanner scanner = new Scanner(System.in);

    public void addBook(CatalogBase catalogBase, Catalog catalog) {
        System.out.println("Enter a book title:");
        String title = scanner.nextLine();
        System.out.println("Choose a book type number:");
        for (int i = 0; i < BookType.values().length; i++) {
            System.out.println((i + 1) + ". " + BookType.values()[i]);
        }
        int number = getNumber();
        BookType type = BookType.values()[number];

        System.out.println("White a description of the book:");
        String description = scanner.nextLine();

        if (!isBookExist(catalogBase, title, type)) {
            catalog.getBooks().add(new Book(title, type, description));
        }
    }

    public void addBookRequest(CatalogBase catalogBase, Catalog catalog) {
        System.out.println("Enter a book title:");
        String title = scanner.nextLine();
        System.out.println("Choose a book type number:");
        for (int i = 0; i < BookType.values().length; i++) {
            System.out.println((i + 1) + ". " + BookType.values()[i]);
        }
        int number = getNumber();
        BookType type = BookType.values()[number];

        System.out.println("White a description of the book:");
        String description = scanner.nextLine();
    }

    public void deleteBook(CatalogBase catalogBase) {
        System.out.println("Enter a book title:");
        String title = scanner.nextLine();
        System.out.println("Choose a book type number:");
        for (int i = 0; i < BookType.values().length; i++) {
            System.out.println((i + 1) + ". " + BookType.values()[i]);
        }
        int number = getNumber();
        BookType type = BookType.values()[number];
        Book newBook = new Book(title, type, "");

        for (Catalog catalog : catalogBase.getCatalogs()) {
            for (int i = 0; i < catalog.getBooks().size(); i++) {
                if (catalog.getBooks().get(i).equals(newBook)) {
                    catalog.getBooks().remove(i);
                    break;
                }
            }
        }
    }

    public void showBook(CatalogBase catalogBase) {
        System.out.println("Enter a book title:");
        String title = scanner.nextLine();
        System.out.println("Choose a book type:");
        for (int i = 0; i < BookType.values().length; i++) {
            System.out.println((i + 1) + ". " + BookType.values()[i]);
        }
        int number = getNumber();
        BookType type = BookType.values()[number];
        isBookExist(catalogBase, title, type);
        System.out.println(findBook(catalogBase, title, type));
    }

    public Book findBook(CatalogBase catalogBase, String title, BookType type) {
        for (Catalog catalog : catalogBase.getCatalogs()) {
            for (Book book : catalog.getBooks()) {
                if (book.equals(new Book(title, type, ""))) {
                    return book;
                }
            }
        }
        System.out.println("The book '" + title + " in such type was not found.");
        return null;
    }

    public boolean isBookExist(CatalogBase catalogBase, String title, BookType type) {
        for (Catalog catalog : catalogBase.getCatalogs()) {
            for (Book book : catalog.getBooks()) {
                if (book.equals(new Book(title, type, ""))) {
                    return true;
                }
            }
        }
        System.out.println("The book '" + title + " in such type was not found.");
        return false;
    }

//    public void deleteBookFromFile(Catalog catalog, String bookTitle, BookType bookType) {
//        for (Book book : catalog.getBooks()) {
//
//            boolean sameBook = book.getTitle().equalsIgnoreCase(bookTitle)
//                    && book.getBookType().equals(bookType);
//
//            if (sameBook) {
//                deleteBookFromFile(catalog, book);
//                return;
//            } else {
//                return;
//            }
//        }
//        System.out.println("The book '" + bookTitle + "' was not found.");
//    }
//
//    private void deleteBookFromFile(Catalog catalog, Book book) {
//
//        MyWriter writer = new MyWriter();
//
//        File file = new File(catalog.getTitle() + ".txt");
//        file.delete();
//
//        catalog.getBooks().remove(book);
//        System.out.println("The book '" + book.getTitle() + "' has been removed from the catalog '"
//                + catalog.getTitle() + "'.");
//
//        for (Book item : catalog.getBooks()) {
//            writer.writeBooksToFile(catalog.getTitle(), item);
//        }
//
//    }

    private void openBook(Book book) {
        System.out.println("Would you like to open the book?\n" +
                "1. Yes.\n" +
                "2. No.");

        boolean quit = false;
        int choice = 0;

        while (!quit) {
            while (!scanner.hasNextInt()) {
                scanner.next();
            }

            choice = scanner.nextInt();

            if (choice >= 1 && choice <= 2) {
                quit = true;
            }
        }

        if (choice == 1) {
            System.out.println(book.toString());
            System.out.println("\n0. Quit.");
            while (!scanner.hasNextInt() && scanner.nextInt() != 0) {
                scanner.next();
            }
            scanner.next();
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
}
