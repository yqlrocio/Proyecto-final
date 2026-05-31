package p3_modelo_de_la_aplicacion;

import p5_interfaces.Preparable;

public class Barra extends GestionInventario implements Preparable {
    private Enum<?> bebidas;
    private Enum<?> postres;

    public Barra(int stockIngredientes, Enum<?> bebidas, Enum<?> postres) {
        super(stockIngredientes);
        this.bebidas = bebidas;
        this.postres = postres;
    }

    public Barra(int stockIngredientes, Enum<?> bebidas) {
        super(stockIngredientes);
        this.bebidas = bebidas;
        this.postres = null;
    }

    @Override
    public void actualizarStock() {
        if (this.stockIngredientes > 0) this.stockIngredientes--;
    }

    @Override
    public void preparar() {
        System.out.print("Sirviendo de Barra -> " + bebidas);
        if (postres != null) System.out.print(" con postre " + postres);
        System.out.println();
        actualizarStock();
    }

    public boolean verificarIngrediente() {
        return hayIngredientes();
    }

    @Override
    public String toString() {
        return "Barra [bebidas=" + bebidas + ", postres=" + postres + ", " + super.toString() + "]";
    }
}