package AA;

public class Book {
    private String bookname;
    private String author;
    private String date;
    private String publisher;

    public Book(String bookname, String author, String date, String publisher) {
        this.bookname = bookname;
        this.author = author;
        this.date = date;
        this.publisher = publisher;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}