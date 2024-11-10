package csibyte.com.challenge_literalura;

import csibyte.com.challenge_literalura.repositorio.AutorRepositorio;
import csibyte.com.challenge_literalura.repositorio.LibroRepositorio;
import csibyte.com.challenge_literalura.servicio.AgregarServicio;
import csibyte.com.challenge_literalura.vista.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {
	@Autowired
	private LibroRepositorio repositorio;
	@Autowired
	private AutorRepositorio autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio, autorRepositorio);
		principal.menuPrincipal();

	}
}
