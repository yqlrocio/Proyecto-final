package p3_modelo_de_la_aplicacion;

import p5_interfaces.Preparable;
import p5_interfaces.PedidosPreparable;

public class PedidoEnRestaurante extends GestionPedido implements Preparable, PedidosPreparable {
    private int numeroMesa;

    public PedidoEnRestaurante(int stockIngredientes, int idPedido, String fecha, String estado, int numeroMesa) {
        super(stockIngredientes, idPedido, fecha, estado);
        this.numeroMesa = numeroMesa;
    }

    @Override
    public double calcularTotal() {
        return 32.80; // Total del consumo directo en mesa
    }

    @Override
    public boolean verificarPedido() {
        return numeroMesa > 0 && hayIngredientes();
    }

    @Override
    public void actualizarStock() {
        if (this.stockIngredientes > 0) this.stockIngredientes--;
    }

    @Override
    public void preparar() {
        System.out.println("Preparando los servicios necesarios para la Mesa: " + numeroMesa);
        actualizarStock();
    }

    @Override
    public void elaborar() {
        System.out.println("Marchando comanda en los fogones para la Mesa #" + numeroMesa);
        this.estado = "Servido en mesa";
    }

    public boolean verificarIngrediente() {
        return hayIngredientes();
    }

    @Override
    public String toString() {
        return super.toString() + " [En Restaurante -> Mesa: " + numeroMesa + "]";
    }
}