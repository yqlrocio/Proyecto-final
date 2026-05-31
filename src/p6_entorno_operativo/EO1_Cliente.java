package p6_entorno_operativo;

/**
 * Representa a un cliente del restaurante.
 * Se encarga de almacenar la información personal del cliente y gestionar
 * sus acciones principales como realizar o consultar pedidos.
 */

public class EO1_Cliente {
    // Atributos privados según UML
    private int idCliente;
    private String nombreCliente;
    private String telefono; 
    private String direccion;

    // Constructor
    /**
     * Constructor completo para crear un nuevo Cliente.
     * @param idCliente     El identificador único.
     * @param nombreCliente El nombre del cliente.
     * @param telefono      El teléfono de contacto.
     * @param direccion     La dirección de domicilio.
     */
    public EO1_Cliente(int idCliente, String nombreCliente, String telefono, String direccion) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Métodos públicos
    
    /**
     * Inicia el proceso para que el cliente realice un nuevo pedido en el sistema.
     */
    public void realizarPedido() {
        System.out.println("El cliente " + nombreCliente + " está realizando un pedido.");
    }

    /**
     * Consulta el historial de todos los pedidos asociados a este cliente.
     */
    public void consultarPedidos() {
        System.out.println("Consultando los pedidos del cliente: " + nombreCliente);
    }

    // Métodos Getters y Setters (Opcionales pero recomendados)
    public int getIdCliente() { return idCliente; }
    public String getNombreCliente() { return nombreCliente; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }

    /**
     * Devuelve una representación en cadena de texto con los datos del cliente.
     * * @return Una cadena con los atributos del cliente.
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}