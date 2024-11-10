package csibyte.com.challenge_literalura.repositorio;

import csibyte.com.challenge_literalura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    Boolean existsByTitulo(String titulo);

    Libro findByTituloContainsIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l WHERE l.idioma= :idioma")
    List<Libro> filterLibroByLanguage(String idioma);
}
