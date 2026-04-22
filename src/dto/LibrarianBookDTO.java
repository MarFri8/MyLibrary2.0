package dto;

import model.Author;
import model.Book;

import java.util.ArrayList;

public class LibrarianBookDTO {
    public int bookId;
    public String title;
    public String isbn;
    public int yearPublished;
    public int availableCopies;
    public int totalCopies;
    public String summary;
    public String language;
    public int pageCount;
    public ArrayList<String> authorNames;
    public ArrayList<String> categoryNames;

    public LibrarianBookDTO(){
    }

    public static LibrarianBookDTO toDTO(Book book){
        LibrarianBookDTO librarianBookDTO = new LibrarianBookDTO();
        librarianBookDTO.setBookId(book.getId());
        librarianBookDTO.setTitle(book.getTitle());
        librarianBookDTO.setIsbn(book.getIsbn());
        librarianBookDTO.setLanguage(book.getLanguage());
        librarianBookDTO.setYearPublished(book.getYearPublished());
        librarianBookDTO.setAvailableCopies(book.getAvailableCopies());
        librarianBookDTO.setTotalCopies(book.getTotalCopies());
        librarianBookDTO.setPageCount(book.getPageCount());
        librarianBookDTO.setSummary(book.getSummary());
        ArrayList<String> authorNames = new ArrayList<>();
        for(Author a : book.getAuthors()){
            authorNames.add(a.getFirstName() + " " + a.getLastName());
        }
        librarianBookDTO.setAuthorNames(authorNames);
        return librarianBookDTO;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public int getTotalCopies() { return totalCopies; }

    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public ArrayList<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(ArrayList<String> authorNames) {
        this.authorNames = authorNames;
    }

    @Override
    public String toString() {
        return "LibrarianBookDTO{" +
                "id=" + bookId +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", yearPublished=" + yearPublished +
                ", availableCopies=" + availableCopies +
                ", totalCopies=" + totalCopies +
                ", summary='" + summary + '\'' +
                ", language='" + language + '\'' +
                ", pageCount=" + pageCount +
                ", authors=" + authorNames +
                '}';
    }

}
