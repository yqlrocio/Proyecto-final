package p4_excepciones;

/**
 * Clase abstracta base para el control de excepciones del restaurante.
 * Mapea directamente al bloque <<abstract>> RestauranteException del UML.
 */
public abstract class RestauranteException extends Exception {
    // Atributo privado obligatorio según UML
    private String mensaje;

    /**
     * Constructor para inicializar la excepción con su mensaje de error.
     * @param mensaje Descripción detallada del fallo.
     */
    public RestauranteException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }

    /**
     * Método abstracto que obligará a las excepciones hijas a 
     * procesar y reportar la salida del error.
     */
    public abstract void reportarError();

    @Override
    public String toString() {
        return "RestauranteException: [Mensaje = " + mensaje + "]";
    }
}