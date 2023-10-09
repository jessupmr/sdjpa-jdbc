package guru.springframework.sdjpajdbc.dao;

import guru.springframework.sdjpajdbc.domain.Book;

public interface BookDao {

    Book getById(Long id);
    Book getByTitle(String title);
    Book saveBook(Book book);
    Book updateBook(Book book);
    void deleteById(Long id);
}
