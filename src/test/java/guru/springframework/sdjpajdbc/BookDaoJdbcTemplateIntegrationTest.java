package guru.springframework.sdjpajdbc;

import guru.springframework.sdjpajdbc.dao.BookDao;
import guru.springframework.sdjpajdbc.dao.BookDaoJdbcTemplateImpl;
import guru.springframework.sdjpajdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@Import(BookDaoJdbcTemplateImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoJdbcTemplateIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Test
    void testGetById() {
        Book found = bookDao.getById(1L);

        assertThat(found).isNotNull();
    }

    @Test
    void testGetByTitle() {
        String title = "Clean Code";

        Book found = bookDao.getByTitle(title);

        assertThat(found).isNotNull();
    }

    @Test
    void testSaveBook() {
        Book book = new Book("My Title", "ISBN1234", "Random House", 1L);
        Book saved = bookDao.saveBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testUpdateBook() {
        String title = "My Title";
        String newTitle = "My New Title";
        Book book = new Book(title, "ISBN1234", "Random House", 1L);
        Book saved = bookDao.saveBook(book);

        saved.setTitle(newTitle);
        Book updated = bookDao.updateBook(saved);

        assertThat(updated.getTitle()).isEqualTo(newTitle);
    }

    @Test
    void testDeleteBook() {
        Book book = new Book("My Title", "ISBN1234", "Random House", 1L);
        Book saved = bookDao.saveBook(book);

        bookDao.deleteById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookDao.getById(saved.getId());
        });
    }
}
