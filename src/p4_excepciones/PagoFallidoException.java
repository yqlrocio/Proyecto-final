package p4_excepciones;

/**
 * Excepción que se lanza cuando hay un fallo al realizar el pago, 
 * dinero no suficiente o errores a la hora de pasar la tarjeta y realizar el pago.
 */
public class PagoFallidoException extends RestauranteException {
    // Atributo privado requerido por el UML
    private String mensaje;

    /**
     * Constructor de la excepción para transacciones erróneas.
     * @param mensaje Detalle del error con el pago.
     */
    public PagoFallidoException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }

    @Override
    public void reportarError() {
        System.err.println("[ALERTA DE CAJA]: Transacción rechazada -> " + mensaje);
    }

    @Override
    public String toString() {
        return "PagoFallidoException: " + mensaje;
    }
}