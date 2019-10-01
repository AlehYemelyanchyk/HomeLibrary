package by.epam.ayem.module6.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleh Yemelyanchyk on 9/29/2019.
 */
public class UserBase {

    private User currentUser;
    private List<User> users;

    public UserBase() {
        this.users = new ArrayList<>();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
