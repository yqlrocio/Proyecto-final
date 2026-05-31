package p6_entorno_operativo;


/**
 * Gestiona las transacciones monetarias de los pedidos del restaurante.
 * Se encarga de procesar el cobro, validar que la transacción sea correcta
 * y emitir los recibos correspondientes.
 */

public class EO3_Pago {
	
	    
	    private int idPago;
	    private double cuenta;
	    private String metodo;

	    /**
	     * Constructor completo para generar un registro de Pago.
	     * @param idPago El identificador único de la transacción.
	     * @param cuenta El importe total a cobrar.
	     * @param metodo El medio de pago elegido.
	     */
	    public EO3_Pago (int idPago, double cuenta, String metodo) {
	        this.idPago = idPago;
	        this.cuenta = cuenta;
	        this.metodo = metodo;
	    }

	    /**
	     * Realiza el procesamiento del cobro de la cuenta a través del método seleccionado.
	     * * @return {@code true} si la transacción económica se procesó con éxito; 
	     * {@code false} en caso contrario.
	     */
	    public boolean procesarPago() {
	        System.out.println("Procesando pago de " + cuenta + "€ mediante " + metodo + "...");
	        return true; 
	    }

	    /**
	     * Verifica que los parámetros del pago sean válidos y los fondos existan.
	     * * @return {@code true} si el pago es legítimo y está verificado; 
	     * {@code false} si se detecta alguna anomalía.
	     */
	    public boolean validarPago() {
	        System.out.println("Validando la transacción del pago ID: " + idPago);
	        return true; 
	    }

	    /**
	     * Genera un comprobante formal o recibo de la transacción en formato de texto.
	     * * @return Una cadena de texto estructurada que representa el ticket/recibo de compra.
	     */
	    public String generarRecibo() {
	        String recibo = "--- RECIBO DE COMPRA ---\n" +
	                        "ID Pago: " + idPago + "\n" +
	                        "Método: " + metodo + "\n" +
	                        "Total: " + cuenta + "€\n" +
	                        "¡Gracias por su visita!";
	        return recibo;
	    }

	    /**
	     * Obtiene el identificador del pago.
	     * @return El ID del pago.
	     */
	    public int getIdPago() { 
	    	return idPago; 
	    	}

	    /**
	     * Obtiene el total de la cuenta.
	     * @return El importe de la cuenta.
	     */
	    public double getCuenta() { 
	    	return cuenta; 
	    	}

	    /**
	     * Obtiene el método de pago de la transacción.
	     * @return El método de pago.
	     */
	    public String getMetodo() { 
	    	return metodo; 
	    	}

	    /**
	     * Devuelve una representación en cadena de texto con los detalles resumidos del pago.
	     * * @return Una cadena con los atributos del pago.
	     */
	    @Override
	    public String toString() {
	        return "Pago{" +
	                "idPago=" + idPago +
	                ", cuenta=" + cuenta +
	                ", metodo='" + metodo + '\'' +
	                '}';
	    }
	}