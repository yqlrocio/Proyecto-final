package p3_modelo_de_la_aplicacion;

import p5_interfaces.Preparable;
import p5_interfaces.PlatosPreparable;

public class Sushi extends GestionInventario implements Preparable, PlatosPreparable {
    private String tipoPlatoSushi;

    public Sushi(int stockIngredientes, String tipoPlatoSushi) {
        super(stockIngredientes);
        this.tipoPlatoSushi = tipoPlatoSushi;
    }

    @Override
    public void actualizarStock() {
        if (this.stockIngredientes > 0) this.stockIngredientes--;
    }

    @Override
    public void preparar() {
        System.out.println("Elaborando piezas de sushi: " + tipoPlatoSushi);
        actualizarStock();
    }

    @Override
    public void emplatar() {
        System.out.println("Disponiendo las piezas de sushi en una tabla de madera con wasabi y jengibre.");
    }

    public boolean verificarIngrediente() {
        return hayIngredientes();
    }

    @Override
    public String toString() {
        return "Sushi [tipoPlatoSushi=" + tipoPlatoSushi + ", " + super.toString() + "]";
    }
}