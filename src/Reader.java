import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Reader {

    private List<User> userBase = new ArrayList<>();

    void readBooksFromFile(List<Catalog> catalogs) throws IOException {
        BufferedReader bufReader = null;

        for (Catalog catalog : catalogs) {
            try {
                bufReader = new BufferedReader(new FileReader(catalog.getCatalogTitle() + ".txt"));
            } catch (FileNotFoundException e) {
                new FileWriter(catalog.getCatalogTitle() + ".txt", true);
                bufReader.close();
            }

            String str;

            while ((str = bufReader.readLine()) != null) {
                String[] strArray = str.split("-;-");
                catalog.addBook(new Book(strArray[0], BookType.getTypeByString(strArray[1]), strArray[2]));
            }
        }
        bufReader.close();
    }

    void readCatalogFromFile(List<Catalog> catalogs, String fileName) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            bufferedReader.close();
            return;
        }

        String c;
        while ((c = bufferedReader.readLine()) != null) {
            catalogs.add(new Catalog(c));
        }
        bufferedReader.close();
    }

    List<User> readUsersFromFile(String fileName) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            bufferedReader.close();
            return userBase;
        }

        String c;
        String[] subStr;
        while ((c = bufferedReader.readLine()) != null) {
            subStr = c.split(";");
            userBase.add(createUserNoHash(subStr[0], subStr[1], subStr[2], subStr[3]));
        }
        bufferedReader.close();
        return userBase;
    }

    private User createUserNoHash(String name, String email, String password, String userRole) {
        UserRole user = null;
        for (UserRole item : UserRole.values()) {
            if (item.getRole().equals(userRole)) {
                user = item;
            }
        }
        return new User(name, email, password, user);
    }
}
