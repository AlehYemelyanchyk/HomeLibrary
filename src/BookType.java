public enum BookType {
    PAPER(1), KINDLE(2);

    int number;

    BookType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    static void showNumbers() {
        for (BookType type : BookType.values()) {
            System.out.println(type.number + ". " + type);
        }
    }

    static BookType getTypeByString(String str) {
        for (BookType type : BookType.values()) {
            if (type.toString().equals(str)) {
                return type;
            }
        }
        return null;
    }
}
