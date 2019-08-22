public enum UserRole {
    ADMINISTRATOR("ADMINISTRATOR", 1), USER("USER", 2);

    String role;
    int number;

    UserRole(String role, int number) {
        this.role = role;
        this.number = number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNumber() {
        return number;
    }

    static void showRoles() {
        for (UserRole user : UserRole.values()) {
            System.out.println(user.number + ". " + user);
        }
    }
}


