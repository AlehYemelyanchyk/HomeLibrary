package by.epam.ayem.module6.model;

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

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private final String title;
    private BookType bookType;
    private final String description;

    public Book(String title, BookType bookType, String description) {
        this.title = title;
        this.bookType = bookType;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Book: '" + title + "'. Type: " + bookType + ". Description: " + description + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                bookType == book.bookType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, bookType);
    }
}
