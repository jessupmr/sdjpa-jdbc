package guru.springframework.sdjpajdbc.dao;

import guru.springframework.sdjpajdbc.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoJdbcTemplateImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(Long id) {
        String sql = """
                SELECT author.id as id, first_name, last_name, book.id, book.isbn, book.publisher, book.title
                FROM author
                LEFT OUTER JOIN book
                ON author.id = book.author_id
                WHERE author.id = ?
                """;

//        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", getRowMapper(), id);

        return jdbcTemplate.query(sql, new AuthorExtractor(), id);
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        String sql = "SELECT * FROM author WHERE first_name = ? AND last_name = ?";
        return jdbcTemplate.queryForObject(sql, getRowMapper(), firstName, lastName);
    }

    @Override
    public Author saveAuthor(Author author) {
        String sql = "INSERT INTO author (first_name, last_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, author.getFirstName(), author.getLastName());

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return this.getById(createdId);
    }


    @Override
    public Author updateAuthor(Author author) {
        String sql = "UPDATE author SET first_name = ?, last_name = ? WHERE id = ?";
        jdbcTemplate.update(sql, author.getFirstName(), author.getLastName(), author.getId());

        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        String sql = "DELETE FROM author WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Author> getRowMapper(){
        return new AuthorMapper();
    }
}
