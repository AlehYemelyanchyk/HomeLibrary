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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleh Yemelyanchyk on 9/29/2019.
 */
public class HomeLibraryRepository {

    public void readCatalogFromFile(CatalogBase catalogBase, String fileName) {
        List<Catalog> catalogs = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String c;
            while ((c = bufferedReader.readLine()) != null) {
                catalogs.add(new Catalog(c));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            catalogBase.setCatalogs(catalogs);
        }
    }

    public void writeCatalogToFile(String title, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(title + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readUsersFromFile(UserBase userBase, String fileName) {
        List<User> users = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String c;
            String[] subStr;
            while ((c = bufferedReader.readLine()) != null) {
                subStr = c.split(";");
                users.add(createUserNoHash(subStr[0], subStr[1], subStr[2], subStr[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            userBase.setUsers(users);
        }
    }

    public void writeUserToFile(UserBase userBase, User user, String filename) {
        readUsersFromFile(userBase, filename);

        try (FileWriter fileWriter = new FileWriter(filename, true)) {

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            printWriter.write(user.getName() + ";"
                    + user.getEmail() + ";"
                    + user.getPassword() + ";"
                    + user.getRole() + "\n");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User createUserNoHash(String name, String email, String password, String userRole) {
        UserRole user = null;
        for (UserRole item : UserRole.values()) {
            if (item.toString().equals(userRole)) {
                user = item;
            }
        }
        return new User(name, email, password, user);
    }

    public void writeBooksToFile(Catalog catalog, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName + ".dat")))) {
            for (Book book : catalog.getBooks()) {
                out.writeObject(book);
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    public List<Book> readBooksFromFile(String fileName) {
        List<Book> books = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(fileName + ".dat")))) {
            boolean endOfFile = false;
            while (!endOfFile) {
                Book book = (Book) in.readObject();
                if (book != null) {
                    books.add(book);
                } else {
                    endOfFile = true;
                }
            }
        } catch (EOFException e) {
            System.out.println("File has been ended");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found", e);
        } catch (IOException e) {
            throw new UncheckedIOException("File not found", e);
        }
        return books;
    }
}