package p3_modelo_de_la_aplicacion;

/**
 * Clase abstracta que gestiona los pedidos.
 * Hereda correctamente de GestionInventario.
 */
public abstract class GestionPedido extends GestionInventario {
    protected int idPedido;
    protected String fecha;
    protected String estado;

    public GestionPedido(int stockIngredientes, int idPedido, String fecha, String estado) {
        super(stockIngredientes);
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.estado = estado;
    }

    public abstract double calcularTotal();
    public abstract boolean verificarPedido();

    @Override
    public String toString() {
        return "Pedido ID: " + idPedido + " | Fecha: " + fecha + " | Estado: " + estado;
    }
}