package csibyte.com.challenge_literalura.vista;

import csibyte.com.challenge_literalura.modelo.Autor;
import csibyte.com.challenge_literalura.modelo.CrearJson;
import csibyte.com.challenge_literalura.modelo.Libro;
import csibyte.com.challenge_literalura.modelo.records.DatosLibro;
import csibyte.com.challenge_literalura.modelo.records.DatosRespuestaApi;
import csibyte.com.challenge_literalura.repositorio.AutorRepositorio;
import csibyte.com.challenge_literalura.repositorio.LibroRepositorio;
import csibyte.com.challenge_literalura.servicio.AgregarServicio;
import csibyte.com.challenge_literalura.servicio.ConectarApi;
import csibyte.com.challenge_literalura.servicio.ConvierteDatos;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConectarApi conectarApi =new ConectarApi();
    private final String URL_BASE ="http://gutendex.com/books/?search=";
    private LibroRepositorio repositorio;
    private AutorRepositorio autorRepositorio;
    private ConvierteDatos conversor = new ConvierteDatos();

    public Principal(LibroRepositorio repositorio, AutorRepositorio autorRepositorio) {
        this.repositorio = repositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void menuPrincipal(){
        var opcion=-1;
        while (opcion != 0){
            var menu = """
                    ║*************************************************║
                    ║*********      SELECCIONE UNA OPCIÓN    *********║
                    ║*************************************************║
                    ║                                                 ║
                    ║        1 - Agregar Libro por Titulo.            ║
                    ║        2 - Agregar Libro Manualmente            ║
                    ║        3 - Listar Libros.                       ║
                    ║        4 - Buscar Libro por Nombre.             ║
                    ║        5 - Listar Autores.                      ║
                    ║        6 - Listar Autores Vivos por año.        ║
                    ║        7 - Listar Libros por Idioma.            ║
                    ║        0 - Salir.                               ║
                    ║                                                 ║
                    ║***********          FIN MENU          **********║
                    """;
            System.out.println("\n********** Bienvenido A Biblioteca Alura **********\n\n" + menu);
                try {//con esto tabajamos la posible entrada de una opcion que no es numerica.
                    opcion =teclado.nextInt();
                    teclado.nextLine();
                    switch (opcion){
                        case 1:
                            buscarLibroEnWeb();
                            break;
                        case 2:
                            agregarManualmente();
                            break;
                        case 3:
                            listarLibrosRegisrados();
                            break;
                        case 4:
                            buscarLibroPorNombre();
                            break;
                        case 5:
                            listarAutores();
                            break;
                        case 6:
                            listarAutoresVivos();
                            break;
                        case 7:
                            listarLibrosIdioma();
                            break;
                        case 0:
                            System.out.println("Se esta cerrando la Aplicacion... ");
                            break;
                        default:
                            System.out.println("Opcción invalida -- Digite una opcción del menu");
                            break;
                    }
                }catch (InputMismatchException e){
                    System.out.println("¡Cuidado! Solo puedes introducir una opcion del menú.");
                    teclado.next();//Avanza el cursor para evitar entrar en un ciclo infinito
                    //opcion = 0;// da por terminado la ejecucion
                }
        }
    }
    //buscamos la capruta de los datos del libro
    private Libro getDatosLibro(){
        System.out.println("Digite el Nombre del Libro");
        var nombreLibro = teclado.nextLine();
        var json = conectarApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        //System.out.println("original " + json);
        DatosRespuestaApi datos = conversor.convertirDatos(json, DatosRespuestaApi.class);
        if (datos !=null && datos.getResultados() != null && !datos.getResultados().isEmpty()){
            DatosLibro datosLibro = datos.getResultados().get(0);
            return new Libro(datosLibro);
        }else {
            //System.out.println("No se encontro resultados.\n");
            return null;
        }
    }

    private void buscarLibroEnWeb() {
        Libro libro = getDatosLibro();

        try {
            if (libro==null){
                //System.out.println("Libro no Encontrado.\n");
            }
            boolean existeLibro = repositorio.existsByTitulo(libro.getTitulo());
            if (existeLibro){
                System.out.println("El Libro Ya esta Registrado en la Base de Datos\n");
            }else{
                repositorio.save(libro);
                System.out.println("Libro Nuevo registrado en la base de datos\n" + libro.toString());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("No se puede persistr el libro!\n");
        }catch (NullPointerException e){
            System.out.println("Libro no encontrado...");
        }
    }
    //agregar libros manualmente
    private void agregarManualmente() {
        CrearJson newJson=new CrearJson();
        String json = newJson.crearJson();
        DatosRespuestaApi datos = conversor.convertirDatos(json, DatosRespuestaApi.class);
        if (datos !=null && datos.getResultados() != null && !datos.getResultados().isEmpty()){
            DatosLibro datosLibro = datos.getResultados().get(0);
            Libro libro = new Libro(datosLibro);
            try {
                if (libro==null){
                    //System.out.println("Libro no Encontrado.\n");
                }
                boolean existeLibro = repositorio.existsByTitulo(libro.getTitulo());
                if (existeLibro){
                    System.out.println("El Libro Ya esta Registrado en la Base de Datos\n");
                }else{
                    repositorio.save(libro);
                    System.out.println("Libro Nuevo registrado en la base de datos\n" + libro.toString());
                }
            }catch (InvalidDataAccessApiUsageException e){
                System.out.println("No se puede persistr el libro!\n");
            }catch (NullPointerException e){
                System.out.println("Libro no encontrado...");
            }
        }
    }

@Transactional(readOnly = true)
    private void listarLibrosRegisrados() {
        List<Libro> libros = repositorio.findAll();
        if (libros.isEmpty()){
            System.out.println("No se encontraron libros en la base de datos");
        }else{
            System.out.println("Estos son los Libros encontrados en la base de datos\n");
            for (Libro libro : libros){
                System.out.println(libro.toString());
            }
        }
    }

    private void buscarLibroPorNombre(){
        System.out.println("Digite el nombre del libro a buscar ");
        var titulo = teclado.nextLine();
        Libro libroBuscado = repositorio.findByTituloContainsIgnoreCase(titulo);
        if (libroBuscado != null){
            System.out.println("El libro buscado es: " + libroBuscado);
        } else {
            System.out.println("El libro " + titulo + "no esta en la base de datos");
        }
    }

    private void listarAutores() {
        List<Autor> autors = autorRepositorio.findAll();
        autors.forEach(System.out::println);
        printSizeBr("autores", autors.size());
    }

    private void printSizeBr(String entity, int size) {
        System.out.println("Total de Autores registrados en la BD Tabla \n"+ entity +" es de " + size);

    }

    private void listarAutoresVivos() {
        System.out.println("Digite el año que quires consultar a autores");
        int year = teclado.nextInt();
        teclado.nextLine();
        List<Autor> filtroAutores =autorRepositorio.filterAutorsByYear(year);
        if (filtroAutores==null || filtroAutores.isEmpty()){
            System.out.println("No se ha encontrado autores vivos a la fecha " + year);
        }else {
            System.out.println("Autores vivos a la fecha " + year +"\n");
            filtroAutores.forEach(System.out::println);
        }
    }

    private void listarLibrosIdioma() {
        List<String> idiomas = List.of("es","en","fr","pt","hu","it");
        Map<String,String> idiomasCodigo =new HashMap<>();
        idiomasCodigo.put("Español","es");
        idiomasCodigo.put("Ingles","en");
        idiomasCodigo.put("Frances","fr");
        idiomasCodigo.put("Portugues","pt");
        idiomasCodigo.put("Hungaro","hu");
        idiomasCodigo.put("Italiano","it");
        String menuIdiomas = """
                ║***********  MENU IDIOMAS **********║
                ║            es - Español.           ║
                ║            en - Ingles.            ║
                ║            fr - Frances.           ║
                ║            pt - Portugues.         ║
                ║            hu - Hungaro.           ║
                ║            it - Italiano.          ║
                ║************ FIN MENU **************║
                        Elije una Opción
                """;
        System.out.println(menuIdiomas);
        String idioma = teclado.nextLine();
        String idiomaCapital= idioma.substring(0,1).toUpperCase() + idioma.substring(1).toLowerCase();

        //validamos el idiomas
        String nombre = idiomasCodigo.get(idiomaCapital);
        System.out.println("transformado "+nombre);

        if (nombre != null){
            System.out.println("el idioma es "+nombre);
            idioma=nombre;
            while (!idiomas.contains(idioma)){
                System.out.println("Es invalido, Digite una opción del menú");
                idioma = teclado.nextLine();
            }
            List<Libro> bdLibro = repositorio.filterLibroByLanguage(idioma);
            if (bdLibro == null || bdLibro.isEmpty()){
                System.out.println("║****************************************║\n║No Hay libros con el idioma seleccionado║\n║**************************************║");
            }else {
                bdLibro.forEach(System.out::println);
            }
        }else{
            System.out.println("idioma no esta en lista "+idioma);
        }
    }

}
