package p3_modelo_de_la_aplicacion;

/**
 * Clase abstracta que gestiona el stock de ingredientes.
 */

public abstract class GestionInventario {
    protected int stockIngredientes;

    public GestionInventario(int stockIngredientes) {
        this.stockIngredientes = stockIngredientes;
    }

    public int consultarStock() {
        return this.stockIngredientes;
    }

    public abstract void actualizarStock();

    public boolean hayIngredientes() {
        return this.stockIngredientes > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GestionInventario other = (GestionInventario) obj;
        return this.stockIngredientes == other.stockIngredientes;
    }

    @Override
    public String toString() {
        return "Inventario [Stock = " + stockIngredientes + "]";
    }
}