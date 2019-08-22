import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserBase {

    private List<User> userBase;
    private final String USERS_FILE = "Users.txt";
    private User currentUser;
    private Writer writer = new Writer();
    private Reader reader = new Reader();

    UserBase() {
        this.userBase = new ArrayList<>();
        readWhenStart();
    }

    void readWhenStart() {
        try {
            userBase = reader.readUsersFromFile(USERS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean logIn(String name, String email, String password) {
        for (User user : userBase) {
            boolean sameUser = user.getName().equals(name) &&
                    user.getEmail().equals(email) &&
                    user.getPassword().equals(String.valueOf(password.hashCode()));
            if (sameUser) {
                currentUser = user;
                System.out.println("Welcome, " + user.getName() + ".");
                return true;
            }
        }
        System.out.println("The user has not found.");
        return false;
    }

    void signUp(String name, String email, String password, int number) {
        if (UserRole.ADMINISTRATOR.getNumber() == number) {
            writer.writeUserToFile(new User(name, email, String.valueOf(password.hashCode()), UserRole.ADMINISTRATOR), USERS_FILE);
            System.out.println("The administrator has been created. Welcome, " + name + ".");
        } else if (!userExist(name)) {
            User newUser = new User(name, email, String.valueOf(password.hashCode()), UserRole.USER);
            System.out.println("The new account has been created.");
            writer.writeUserToFile(newUser, USERS_FILE);
            userBase.add(newUser);
        } else {
            System.out.println("A user with such name already exists.");
        }
    }

    private boolean userExist(String name) {
        for (User user : userBase) {
            if (user.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public List<User> getUserBase() {
        return userBase;
    }

    public void setUserBase(List<User> userBase) {
        this.userBase = userBase;
    }

    User getCurrentUser() {
        return currentUser;
    }

    void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
