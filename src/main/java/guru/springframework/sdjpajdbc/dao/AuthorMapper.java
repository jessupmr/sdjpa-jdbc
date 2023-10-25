package guru.springframework.sdjpajdbc.dao;

import guru.springframework.sdjpajdbc.domain.Author;

import guru.springframework.sdjpajdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {

        // create the author from the first record
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));

        try {
            // if we have an isbn on the first record then we know we have at least one book
            if (rs.getString("isbn") != null) {
                author.setBooks(new ArrayList<>());
                author.getBooks().add(mapBook(rs));
            }

            // go through the rest of the records and create the remaining books
            while (rs.next()) {
                author.getBooks().add(mapBook(rs));
            }
        } catch (SQLException e) {
            // do nothing
        }

        return author;

    }

    private Book mapBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong(4));
        book.setTitle(rs.getString(5));
        book.setIsbn(rs.getString(6));
        book.setPublisher(rs.getString(7));
        book.setAuthorId(rs.getLong(1));
        return book;
    }
}
