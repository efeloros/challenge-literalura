package csibyte.com.challenge_literalura.servicio;

public interface IConvierteDatos {
    <T> T convertirDatos(String json, Class<T> clase);
}
