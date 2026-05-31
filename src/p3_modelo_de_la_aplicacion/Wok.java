package p3_modelo_de_la_aplicacion;

import p5_interfaces.Preparable;
import p5_interfaces.PlatosPreparable;

public class Wok extends GestionInventario implements Preparable, PlatosPreparable {
    private String tipoPlatoChino;

    public Wok(int stockIngredientes, String tipoPlatoChino) {
        super(stockIngredientes);
        this.tipoPlatoChino = tipoPlatoChino;
    }

    @Override
    public void actualizarStock() {
        if (this.stockIngredientes >= 2) this.stockIngredientes -= 2;
    }

    @Override
    public void preparar() {
        System.out.println("Cocinando en el Wok: " + tipoPlatoChino);
        actualizarStock();
    }

    @Override
    public void emplatar() {
        System.out.println("Presentando el plato Wok en un cuenco hondo tradicional.");
    }

    public boolean verificarIngrediente() {
        return this.stockIngredientes >= 2;
    }

    @Override
    public String toString() {
        return "Wok [tipoPlatoChino=" + tipoPlatoChino + ", " + super.toString() + "]";
    }
}