package csibyte.com.challenge_literalura.modelo;

import csibyte.com.challenge_literalura.modelo.records.DatosLibro;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)//para guardar automaticaente
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idioma;
    private Long descargas;
    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        //si autor es una lista de autores
        if (datosLibro.autor() != null && !datosLibro.autor().isEmpty()){
            this.autor = new Autor(datosLibro.autor().get(0));
        }else {
            this.autor = null;
        }
        this.idioma = lenguaje(datosLibro.idioma());
        this.descargas = datosLibro.descargas();
    }
    private String lenguaje(List<String>idiomas){
        if (idiomas ==null || idiomas.isEmpty()){
            return "Desconocido";
        }
        return idiomas.get(0);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return  "\n║*************** Libro ***************║"+
                "\nTitulo: " + titulo +
                "\nAutor: " + (autor !=null ? autor.getNombre() : "N/A") +
                "\nIdioma: " + idioma +
                "\nTotal de Descargar:  " + descargas +
                "\n║************* Fin Libro *************║";
    }
}
