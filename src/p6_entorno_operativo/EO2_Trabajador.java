package p6_entorno_operativo;

import p6_entorno_operativo.EO2_GestionTrabajador.Rol;

/**
 * Representa a un empleado del restaurante.
 * Almacena sus datos identificativos y gestiona las acciones operativas
 * asociadas al ciclo de vida de un pedido (gestión, preparación y entrega).
 */
public class EO2_Trabajador {
    
    private int idTrabajador;
    private String nombreTrabajador;
    private Rol rol;

    /**
     * Constructor completo para registrar un nuevo Trabajador.
     * @param idTrabajador     El identificador único.
     * @param nombreTrabajador El nombre del empleado.
     * @param rol              El rol asignado (Cocinero, Camarero, etc.).
     */
    public EO2_Trabajador(int idTrabajador, String nombreTrabajador, Rol rol) {
        this.idTrabajador = idTrabajador;
        this.nombreTrabajador = nombreTrabajador;
        this.rol = rol;
    }

    /**
     * Gestiona el estado administrativo de un pedido (recepción o modificaciones).
     */
    public void gestionarPedido() {
        System.out.println("El trabajador " + nombreTrabajador + " (" + rol + ") está gestionando un pedido.");
    }

    /**
     * Realiza la elaboración del pedido. 
     */
    public void prepararPedido() {
        System.out.println("El trabajador " + nombreTrabajador + " está preparando el pedido.");
    }

    /**
     * Finaliza el ciclo del pedido entregándolo al cliente (en mesa o a domicilio).
     */
    public void entregarPedido() {
        System.out.println("El trabajador " + nombreTrabajador + " está entregando el pedido.");
    }

    /**
     * Obtiene el identificador del trabajador.
     * @return El ID del trabajador.
     */
    public int getIdTrabajador() { 
        return idTrabajador; 
    }

    /**
     * Obtiene el nombre del trabajador.
     * @return El nombre del trabajador.
     */
    public String getNombreTrabajador() { 
        return nombreTrabajador; 
    }

    /**
     * Obtiene el rol del trabajador.
     * @return El rol (Enum) del trabajador.
     */
    public Rol getRol() { 
        return rol; 
    }

    /**
     * Devuelve una representación en cadena de texto con la información del trabajador.
     * @return Una cadena con los atributos del trabajador.
     */
    @Override
    public String toString() {
        return "Trabajador [ID=" + idTrabajador + ", Nombre=" + nombreTrabajador + ", Rol=" + rol + "]";
    }
}