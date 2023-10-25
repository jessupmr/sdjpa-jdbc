package guru.springframework.sdjpajdbc.dao;

import guru.springframework.sdjpajdbc.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookDaoJdbcTemplateImpl implements BookDao{

    private final JdbcTemplate jdbcTemplate;

    public BookDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Book getById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, getBookMapper(), id);
    }

    @Override
    public Book getByTitle(String title) {
        String sql = "SELECT * FROM book WHERE title = ?";
        return jdbcTemplate.queryForObject(sql, getBookMapper(), title);
    }

    @Override
    public Book saveBook(Book book) {
        String sql = "INSERT INTO book (isbn, publisher, title, author_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId());

        Long savedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return this.getById(savedId);
    }

    @Override
    public Book updateBook(Book book) {
        String sql = "UPDATE book SET isbn = ?, publisher = ?, title = ?, author_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId(), book.getId());
        return this.getById(book.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private BookMapper getBookMapper() {
        return new BookMapper();
    }
}
