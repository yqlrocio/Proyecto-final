package p4_excepciones;

/**
 * Excepción que se lanza cuando se intenta procesar una comanda 
 * que no cuenta con stock o no existe en el restaurante.
 */
public class PedidoNoDisponibleException extends RestauranteException {
    // Atributo privado requerido por el UML
    private String mensaje;

    /**
     * Constructor de la excepción para pedidos no disponibles.
     * @param mensaje Detalle del incidente con el pedido.
     */
    public PedidoNoDisponibleException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }

    @Override
    public void reportarError() {
        System.err.println("[ALERTA DE CONTROL]: No se puede procesar la orden -> " + mensaje);
    }

    @Override
    public String toString() {
        return "PedidoNoDisponibleException: " + mensaje;
    }
}