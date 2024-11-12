package csibyte.com.challenge_literalura.modelo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Scanner;

public class CrearJson {
    private String json;
    private Scanner teclado=new Scanner(System.in);
    public String crearJson(){
        try {
            //crear el autor
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

            NewAutor author=new NewAutor(nombre,fechaNacimiento,fechaFallecimiento);

            //crear resultado libro
            System.out.println("Digite el nombre del libro a agregar BD");
            String titulo = teclado.nextLine();
            System.out.println("Digite los idioma del Libro (su codigo es, fr, en, hu, pt, it)");
            String idioma = teclado.nextLine();
            Long descargas = (long) 0.0;

            Result result = new Result(titulo, Collections.singletonList(author), Collections.singletonList(idioma), descargas);
            //Result result=new Result("pride and prejudice", Collections.singletonList(author),Collections.singletonList("en"),58812);
            //crea la respuesta con la lista de resultados
            Response response = new Response(Collections.singletonList(result));
            //convierte a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(response);
            System.out.println("ver el json creado...."+ json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}