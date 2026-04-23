package service;

import java.util.*;
import repository.AuthorRepository;

public class AuthorService {

    private AuthorRepository authorRepository = new AuthorRepository();

    public void createAuthor(String firstName, String lastName, String nationality, String birthDate){
        authorRepository.addAuthor(firstName, lastName, nationality, birthDate);
    }

    public void editAuthor(int id, String firstName, String lastName, String nationality, String birthDate){
        authorRepository.updateAuthor(id, firstName, lastName, nationality, birthDate);
    }
}
