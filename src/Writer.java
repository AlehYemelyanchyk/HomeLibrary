import java.io.*;

class Writer {

    private Reader reader = new Reader();

    void writeBookToFile(String catalogTitle, Book book) {
        try (FileWriter writer = new FileWriter(catalogTitle + ".txt", true)) {
            writer.write(book.getTitle() + "-;-"
                    + book.getBookType() + "-;-"
                    + book.getDescription() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeCatalogToFile(String title, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(title + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeUserToFile(User user, String filename) {

        try {
            reader.readUsersFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter;
        try (FileWriter fileWriter = new FileWriter(filename, true)) {

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);

            printWriter.write(user.getName() + ";"
                    + user.getEmail() + ";"
                    + user.getPassword() + ";"
                    + user.getRole() + "\n");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
