package guru.springframework.sdjpajdbc;

import guru.springframework.sdjpajdbc.dao.AuthorDao;
import guru.springframework.sdjpajdbc.dao.AuthorDaoImpl;
import guru.springframework.sdjpajdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
// this tells spring to only scan the packages we need for these tests and makes it more efficient.
// @ComponentScan also has a bug right now and is not bringing in the desired AuthorDao dependency.
//@ComponentScan(basePackages = {"guru.springframework.sdjpajdbc.dao"})
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;
    @Test
    void testGetAuthorById_Found() {

        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorById_NotFound() {

        Author author = authorDao.getById(0L);

        assertThat(author).isNull();
    }

    @Test
    void testGetAuthorByName_Found() {

        Author author = authorDao.getByName("Robert", "Martin");
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName_NotFound() {

        Author author = authorDao.getByName("Mike", "Jessup");
        assertThat(author).isNull();
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

        Author deleted = authorDao.getById(saved.getId());

        assertThat(deleted).isNull();
    }

}
