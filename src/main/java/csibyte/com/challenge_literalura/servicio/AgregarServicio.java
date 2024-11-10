package csibyte.com.challenge_literalura.servicio;

import csibyte.com.challenge_literalura.modelo.Autor;
import csibyte.com.challenge_literalura.modelo.Libro;
import csibyte.com.challenge_literalura.repositorio.AutorRepositorio;
import csibyte.com.challenge_literalura.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class AgregarServicio {
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;


    public Libro saveLibro(String titulo, Autor autor, String idioma, Long descargas) {
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setIdioma(idioma);
        libro.setDescargas(descargas);
        return libroRepositorio.save(libro);


    }
    public  Autor saveAutor(String nombre, Integer anoNacimiento, Integer anoFallecimiento){
        Autor autor=new Autor();
        autor.setNombre(nombre);
        autor.setFechaNacimiento(anoNacimiento);
        autor.setFechaFallecido(anoFallecimiento);
        return autorRepositorio.save(autor);
    }


    public void capturaDatos() {
        Scanner teclado=new Scanner(System.in);
        System.out.println("Digite los Autores (separados por coma): ");
        String nombre = teclado.nextLine();
        System.out.println("Digite el año de nacimiento del autor");
        Integer fechaNacimiento = teclado.nextInt();
        teclado.nextLine();
        System.out.println("Digite el año de fallecimiento del autor, si esta vivo solo un enter ");
        var fechaMuerte = teclado.nextLine();
        Integer fechaFallecimiento;
        if (fechaMuerte.isEmpty()) {
            fechaFallecimiento = 0;
        } else {
            fechaFallecimiento = Integer.valueOf(fechaMuerte);
        }
        Autor autor =new Autor();
        autor.setNombre(nombre);
        autor.setFechaNacimiento(fechaNacimiento);
        autor.setFechaFallecido(fechaFallecimiento);
        autorRepositorio.save(autor);

        System.out.println("Digite el nombre del libro a agregar BD");
        String titulo = teclado.nextLine();

        System.out.println("Digite los idioma del Libro ");
        String idioma = teclado.nextLine();
        Long descargas = (long) 0.0;

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setIdioma(idioma);
        libro.setDescargas(descargas);
        libro.setAutor(autor);
        libroRepositorio.save(libro);
        System.out.println(" Datos guardados correctamente");
    }
}
