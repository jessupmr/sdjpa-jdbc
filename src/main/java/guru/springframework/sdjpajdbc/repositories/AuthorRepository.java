package guru.springframework.sdjpajdbc.repositories;

import guru.springframework.sdjpajdbc.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
