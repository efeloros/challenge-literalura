package csibyte.com.challenge_literalura.repositorio;

import csibyte.com.challenge_literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :year AND a.fechaFallecido >= :year")
    List<Autor> filterAutorsByYear(int year);
}
