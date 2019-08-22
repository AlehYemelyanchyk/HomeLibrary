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

public class Book {
    private String title;
    private BookType bookType;
    private String description;

    public Book(String title, BookType bookType, String description) {
        this.title = title;
        this.bookType = bookType;
        this.description = description;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    BookType getBookType() {
        return bookType;
    }

    void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book: '" + title + "'. Book type: " + bookType + ". Description: " + description + ".";
    }
}
