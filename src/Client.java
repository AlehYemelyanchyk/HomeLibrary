import java.util.Scanner;

class Client {

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

    void run() {

        Scanner scanner = new Scanner(System.in);
        LibraryService service = new LibraryService();
        UserBase userBase = new UserBase();

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
                    System.out.println("Enter a name:");
                    String loginName = scanner.nextLine();
                    System.out.println("Enter an e-mail:");
                    String email = scanner.nextLine();
                    System.out.println("Enter a password:");
                    String loginPassword = scanner.nextLine();
                    if (userBase.logIn(loginName, email, loginPassword)) {
                        service.showMenu(userBase);
                    }
                    break;
                case 2:
                    System.out.println("Enter a name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter an e-mail:");
                    email = scanner.nextLine();
                    System.out.println("Enter a password:");
                    String password = scanner.nextLine();
                    System.out.println("Choose a role:");
                    UserRole.showRoles();
                    int number = scanner.nextInt();
                    scanner.nextLine();
                    userBase.signUp(name, email, password, number);
                    break;
                case 0:
                    quit = true;

            }
        }
    }


}
