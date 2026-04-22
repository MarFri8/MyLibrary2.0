package mapper;

public class BookMapper {
    /*public static BookDTO toDTO(Book book){
        BookDTO dto = new BookDTO();
        dto.setBookId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setLanguage(book.getLanguage());
        dto.setYearPublished(book.getYearPublished());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setPageCount(book.getPageCount());
        dto.setSummary(book.getSummary());
        ArrayList<String> authorNames = new ArrayList<>();
        for(Author a : book.getAuthors()){
            authorNames.add(a.getFirstName() + " " + a.getLastName());
        }
        dto.setAuthorNames(authorNames);
        return dto;
    }

    public static List<BookDTO> bookDTOList(List<Book> books){
        return books.stream().map(BookMapper::toDTO).toList();
    }*/
}
