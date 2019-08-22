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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Catalog {

    private String catalogTitle;
    private List<Book> bookCatalog;
    private Writer writer = new Writer();

    private Scanner scanner = new Scanner(System.in);

    Catalog(String catalogTitle) {
        this.catalogTitle = catalogTitle;
        this.bookCatalog = new ArrayList<>();
    }

    void deleteBookFromCatalog(String bookTitle, BookType bookType) {
        for (Book book : bookCatalog) {

            boolean sameBook = book.getTitle().equalsIgnoreCase(bookTitle)
                    && book.getBookType().equals(bookType);

            if (sameBook) {
                deleteBook(book);
                return;
            } else {
                return;
            }
        }
        System.out.println("The book '" + bookTitle + "' was not found.");
    }

    void showCatalog() {
        int page = 0;
        int count = 0;
        for (Book book : bookCatalog) {
            if (count % 4 == 0) {
                System.out.println("Page " + (page++) + ":");
            }
            System.out.println(book.toString());
            count++;
        }
    }

    void addBook(Book book) {
        if (bookCatalog == null || bookCatalog.size() == 0) {
            bookCatalog.add(book);
        } else if (!isInCatalog(book)) {
            bookCatalog.add(book);
        } else {
            System.out.println("The book '" + book.getTitle() + "' is already in the catalog.");
        }
    }

    private void deleteBook(Book book) {
        File file = new File(getCatalogTitle() + ".txt");
        file.delete();

        bookCatalog.remove(book);
        System.out.println("The book '" + book.getTitle() + "' has been removed from the catalog '"
                + getCatalogTitle() + "'.");

        for (Book item : bookCatalog) {
            writer.writeBookToFile(getCatalogTitle(), item);
        }

    }

    Book isInCatalog(String title, BookType bookType) {
        for (Book item : bookCatalog) {

            boolean sameBook = item.getTitle().equalsIgnoreCase(title)
                    && item.getBookType().equals(bookType);

            if (sameBook) {
                System.out.println("The book '" + item.getTitle() + "' " + item.getBookType()
                        + " type is in the catalog '" + catalogTitle + "'.");
                openBook(item);
                return item;
            }
        }
        return null;
    }

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

    boolean isInCatalog(Book book) {
        for (Book item : bookCatalog) {

            boolean sameBook = item.getTitle().equalsIgnoreCase(book.getTitle())
                    && item.getBookType().equals(book.getBookType());

            if (sameBook) {
                return true;
            }
        }
        return false;
    }

    String getCatalogTitle() {
        return catalogTitle;
    }

    @Override
    public String toString() {
        return catalogTitle;
    }
}
