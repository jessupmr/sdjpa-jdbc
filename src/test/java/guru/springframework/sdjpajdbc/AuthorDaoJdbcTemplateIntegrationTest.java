package guru.springframework.sdjpajdbc;

import guru.springframework.sdjpajdbc.dao.AuthorDao;
import guru.springframework.sdjpajdbc.dao.AuthorDaoImpl;
import guru.springframework.sdjpajdbc.dao.AuthorDaoJdbcTemplateImpl;
import guru.springframework.sdjpajdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@Import(AuthorDaoJdbcTemplateImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoJdbcTemplateIntegrationTest {

    @Autowired
    AuthorDao authorDao;
    @Test
    void testGetAuthorById_Found() {

        Author author = authorDao.getById(4L);

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorById_NotFound() {

        assertThrows(TransientDataAccessResourceException.class, () -> {
            authorDao.getById(0L);
        });
    }

    @Test
    void testGetAuthorByName_Found() {

        Author author = authorDao.getByName("Robert", "Martin");
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName_NotFound() {

        assertThrows(EmptyResultDataAccessException.class, () -> {
            authorDao.getByName("Mike", "Jesssup");
        });

    }

    @Test
    void testSaveAuthor() {

        String firstName = "Mike";
        String lastName = "Jessup";

        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        Author saved = authorDao.saveAuthor(author);

        assertThat(saved).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo(firstName);
        assertThat(saved.getLastName()).isEqualTo(lastName);
    }

    @Test
    void testUpdateAuthor() {
        String firstName = "Mike";
        String lastName = "J";
        String newLastName = "Jessup";

        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        Author saved = authorDao.saveAuthor(author);

        saved.setLastName(newLastName);
        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo(newLastName);
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("Mike");
        author.setLastName("Jessup");
        Author saved = authorDao.saveAuthor(author);

        authorDao.deleteAuthorById(saved.getId());

        assertThrows(TransientDataAccessResourceException.class, () -> {
            authorDao.getById(saved.getId());
        });
    }
}
