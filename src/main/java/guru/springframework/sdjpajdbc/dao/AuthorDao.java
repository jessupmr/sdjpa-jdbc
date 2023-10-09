package guru.springframework.sdjpajdbc.dao;

import guru.springframework.sdjpajdbc.domain.Author;

public interface AuthorDao {

    Author getById(Long id);
    Author getByName(String firstName, String lastName);

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);
}
