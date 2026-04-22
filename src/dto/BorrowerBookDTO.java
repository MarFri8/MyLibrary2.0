package dto;

import model.Author;
import model.Book;

import java.util.ArrayList;

public class BorrowerBookDTO {
    public String title;
    public int yearPublished;
    public int availableCopies;
    public String summary;
    public String language;
    public int pageCount;
    public ArrayList<String> authorNames;
    public ArrayList<String> categoryNames;

    public BorrowerBookDTO(){
    }

    public static BorrowerBookDTO toDTO(Book book){
        BorrowerBookDTO borrowerBookDTO = new BorrowerBookDTO();
        borrowerBookDTO.setTitle(book.getTitle());
        borrowerBookDTO.setLanguage(book.getLanguage());
        borrowerBookDTO.setYearPublished(book.getYearPublished());
        borrowerBookDTO.setAvailableCopies(book.getAvailableCopies());
        borrowerBookDTO.setPageCount(book.getPageCount());
        borrowerBookDTO.setSummary(book.getSummary());
        ArrayList<String> authorNames = new ArrayList<>();
        for(Author a : book.getAuthors()){
            authorNames.add(a.getFirstName() + " " + a.getLastName());
        }
        borrowerBookDTO.setAuthorNames(authorNames);
        return borrowerBookDTO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return "Title: " + title +
                "\nAuthor(s): " + String.join(", ", authorNames)  +
                "\nYear: " + yearPublished +
                "\nLanguage: " + language +
                "\nSummary: " + summary +
                "\nAvailable Copies: " + availableCopies +
                "\nPages: " + pageCount + " pages.\n";
    }

}
