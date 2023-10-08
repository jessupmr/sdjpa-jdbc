package guru.springframework.sdjpajdbc.repositories;

import guru.springframework.sdjpajdbc.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
