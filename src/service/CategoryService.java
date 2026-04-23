package service;

import repository.CategoryRepository;

public class CategoryService {

    private CategoryRepository categoryRepository = new CategoryRepository();

    public void addBookToCategory(int bookId, int categoryId){
        categoryRepository.linkBookToCategory(bookId, categoryId);
    }
}
