package csibyte.com.challenge_literalura.modelo.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(@JsonAlias("name") String nombre,
                         @JsonAlias("birth_year") Integer fechaNacimiento,
                         @JsonAlias("death_year") Integer fechaFallecido) {
}
