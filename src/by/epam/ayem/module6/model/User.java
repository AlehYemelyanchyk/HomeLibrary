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

import java.util.Objects;

public class User {

    private String name;
    private String email;
    private String password;
    private final UserRole role;

    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

        @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        User newUser = (User) obj;
        return (this.getName().equals(newUser.getName())
                && (this.getEmail().equals(newUser.getEmail())
                && (this.getPassword().equals(newUser.getPassword()))));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }
}
