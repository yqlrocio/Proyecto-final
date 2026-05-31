package p3_modelo_de_la_aplicacion;

import p5_interfaces.Preparable;
import p5_interfaces.PedidosPreparable;

public class PedidoParaLlevar extends GestionPedido implements Preparable, PedidosPreparable {
    private String direccionEntrega;

    public PedidoParaLlevar(int stockIngredientes, int idPedido, String fecha, String estado, String direccionEntrega) {
        super(stockIngredientes, idPedido, fecha, estado);
        this.direccionEntrega = direccionEntrega;
    }

    @Override
    public double calcularTotal() {
        return 15.00 + 3.50; // Total base + tarifa de envío
    }

    @Override
    public boolean verificarPedido() {
        return direccionEntrega != null && !direccionEntrega.isEmpty() && hayIngredientes();
    }

    @Override
    public void actualizarStock() {
        if (this.stockIngredientes > 0) this.stockIngredientes--;
    }

    @Override
    public void preparar() {
        System.out.println("Preparando y empaquetando comanda para reparto externo.");
        actualizarStock();
    }

    @Override
    public void elaborar() {
        System.out.println("Cocinando el menú completo asignado al ticket de reparto #" + idPedido);
        this.estado = "Preparado para enviar";
    }

    public boolean verificarIngrediente() {
        return hayIngredientes();
    }

    @Override
    public String toString() {
        return super.toString() + " [Para Llevar -> Dirección: " + direccionEntrega + "]";
    }
}