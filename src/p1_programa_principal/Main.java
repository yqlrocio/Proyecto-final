package p1_programa_principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import p6_entorno_operativo.EO2_Trabajador;
import p6_entorno_operativo.EO2_GestionTrabajador.Rol;
import p3_modelo_de_la_aplicacion.Sushi;
import p3_modelo_de_la_aplicacion.Wok;
import p3_modelo_de_la_aplicacion.Barra;
import p4_excepciones.PedidoNoDisponibleException;
import p4_excepciones.PagoFallidoException;
import p4_excepciones.RestauranteException;
import p6_entorno_operativo.EO1_Cliente;
import p6_entorno_operativo.EO3_Pago;

public class Main {
    private static Scanner teclado = new Scanner(System.in);
    // Ruta compartida del fichero de clientes
    private static final String RUTA_FICHERO_CLIENTES = "src/p2_almacenamiento_de_datos/clienteGuardado.txt";

    public static void main(String[] args) {
        int opPrincipal;
        do {
            System.out.println("\n==================================================");
            System.out.println("                MENÚ PRINCIPAL                    ");
            System.out.println("==================================================");
            System.out.println("1. Gestión de Cliente");
            System.out.println("2. Gestión de Trabajador");
            System.out.println("0. Salir del Sistema");
            System.out.print("Seleccione una opción: ");
            try {
                opPrincipal = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) { opPrincipal = -1; }

            switch (opPrincipal) {
                case 1: menuGestionCliente(); break;
                case 2: menuGestionTrabajador(); break;
                case 0: System.out.println("Cerrando el sistema del restaurante..."); break;
                default: System.out.println("Opción no válida."); break;
            }
        } while (opPrincipal != 0);
    }

    // =========================================================================
    // 1. CAPA GESTIÓN DE CLIENTE
    // =========================================================================
    private static void menuGestionCliente() {
        int opCliente;
        do {
            System.out.println("\n--------------------------------------------------");
            System.out.println("               GESTIÓN DE CLIENTE                 ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Añadir Cliente");
            System.out.println("2. Ver Lista de Clientes (Leer Fichero)");
            System.out.println("3. Eliminar Cliente");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opCliente = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) { opCliente = -1; }

            switch (opCliente) {
                case 1: datosNuevoCliente(); break;
                case 2: mostrarClientesDelFichero(); break; 
                case 3: System.out.println("[INFO] Lógica para eliminar de clienteGuardado.txt"); break;
                case 0: break;
                default: System.out.println("Opción no válida."); break;
            }
        } while (opCliente != 0);
    }

    private static void datosNuevoCliente() {
        System.out.println("\n--- Formulario de Nuevo Cliente ---");
        System.out.print("Introduzca Nombre: ");
        String nombreCliente = teclado.nextLine();
        System.out.print("Introduzca Teléfono: ");
        String telefono = teclado.nextLine();
        System.out.print("Introduzca Dirección: ");
        String direccion = teclado.nextLine();

        int idClienteAleatorio = (int) (Math.random() * 9000) + 1000;
        System.out.println("\n[SISTEMA] Cliente registrado con éxito.");
        System.out.println("Nombre del cliente: " + nombreCliente);
        System.out.println("Teléfono del cliente: " + telefono);
        System.out.println("Dirección del cliente: " + direccion);
        System.out.println("ID Cliente Asignado: " + idClienteAleatorio);
        
        EO1_Cliente nuevoCliente = new EO1_Cliente(idClienteAleatorio, nombreCliente, telefono, direccion);
        guardarClienteEnFichero(nuevoCliente);

        menuTipoPedido();
    }

    // =========================================================================
    // NUEVO MÉTODO: LEER Y MOSTRAR CLIENTES USANDO STREAMS (RÚBRICA OBLIGATORIA)
    // =========================================================================
    private static void mostrarClientesDelFichero() {
        System.out.println("\n==================================================");
        System.out.println("           LISTADO DE CLIENTES REGISTRADOS        ");
        System.out.println("==================================================");

        File fichero = new File(RUTA_FICHERO_CLIENTES);

        // Gestionar si el fichero no existe
        if (!fichero.exists() || fichero.length() == 0) {
            System.out.println("[AVISO] El archivo de datos está vacío o no existe ningún cliente aún.");
            return;
        }

        List<EO1_Cliente> listaClientes = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                // Separamos los datos por el punto y coma
                String[] datos = linea.split(";");
                if (datos.length == 4) {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String tlf = datos[2];
                    String dir = datos[3];
                    
                    // Reconstruimos el objeto temporalmente en memoria
                    listaClientes.add(new EO1_Cliente(id, nombre, tlf, dir));
                }
            }

            listaClientes.stream().forEach(cliente -> {
                System.out.println("ID: " + cliente.getIdCliente() + 
                                   " | Nombre: " + cliente.getNombreCliente() + 
                                   " | Teléfono: " + cliente.getTelefono() + 
                                   " | Dirección: " + cliente.getDireccion());
            });
            
            System.out.println("--------------------------------------------------");
            System.out.println("Total de clientes registrados: " + listaClientes.size());

        } catch (IOException e) {
            System.out.println("[ERROR CRÍTICO] Error al leer la base de datos de clientes: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR FORMATO] El archivo de texto contiene datos corruptos.");
        }
    }

    private static void menuTipoPedido() {
        int opTipo;
        do {
            System.out.println("\n--- Tipo de Servicio ---");
            System.out.println("1. Pedir para Comer Aquí");
            System.out.println("2. Pedir para Llevar");
            System.out.println("0. Cancelar Pedido");
            System.out.print("Seleccione una opción: ");
            try {
                opTipo = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) { opTipo = -1; }

            switch (opTipo) {
                case 1: 
                    System.out.println("[SISTEMA] Pedido En Restaurante Inicializado.");
                    
                    // 1. Creamos un objeto trabajador con el rol de CAMARERO usando tu clase corregida
                    EO2_Trabajador camarero = new EO2_Trabajador(201, "Carlos Gómez", Rol.CAMARERO);
                    
                    // 2. Mostramos por pantalla quién atiende al cliente
                    System.out.println("\n==================================================");
                    System.out.println(" -> ATENDIDO POR: " + camarero.getNombreTrabajador() + " (" + camarero.getRol() + ")");
                    System.out.println("==================================================");
                    
                    // 3. Opcional: Ejecutamos uno de sus métodos operativos para darle dinamismo
                    camarero.gestionarPedido();
                    
                    // Continuamos con la carta
                    menuCartaProductos("Restaurante");
                    opTipo = 0; // Rompe el bucle al finalizar la transacción
                    break;
                    
                case 2: 
                    System.out.println("[SISTEMA] Pedido Para Llevar Inicializado.");
                    
                    // 1. Creamos un objeto trabajador con el rol de COCINERO para preparar el paquete
                    EO2_Trabajador cocinero = new EO2_Trabajador(305, "Xiao Long", Rol.COCINERO);
                    
                    // 2. Mostramos quién va a elaborar su comida
                    System.out.println("\n==================================================");
                    System.out.println(" -> CHEF EN COCINA: " + cocinero.getNombreTrabajador() + " (" + cocinero.getRol() + ")");
                    System.out.println("==================================================");
                    
                    // 3. Ejecutamos su método de preparar
                    cocinero.prepararPedido();
                    
                    // Continuamos con la carta
                    menuCartaProductos("Llevar");
                    opTipo = 0; 
                    break;
                    
                case 0: 
                    System.out.println("Pedido cancelado.");
                    break;
                default: 
                    System.out.println("Opción no válida."); 
                    break;
            }
        } while (opTipo != 0);
    }
    
    private static void menuCartaProductos(String tipoPedido) {
        int opCarta;
        Sushi maki = new Sushi(4, "Maki California");
        Wok arrozWok = new Wok(5, "Pato frito");
        Barra refresco = new Barra(50, null);
        
        do {
            System.out.println("\n--- CARTA DEL RESTAURANTE (" + tipoPedido.toUpperCase() + ") ---");
            System.out.println("1. Pedir Bebida o Postre (Barra)");
            System.out.println("2. Pedir Wok");
            System.out.println("3. Pedir Sushi");
            System.out.println("4. Ir a Forma de Pago");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            try {
                opCarta = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) { opCarta = -1; }

            try {
                switch (opCarta) {
                    case 1:
                        refresco.preparar();
                        System.out.println("Añadido: " + refresco.toString());
                        break;
                    case 2:
                        arrozWok.preparar();
                        System.out.println("Añadido: " + arrozWok.toString());
                        break;
                    case 3:
                        if (!maki.hayIngredientes()) {
                            throw new PedidoNoDisponibleException("No quedan existencias de Arroz o Alga para el Sushi.");
                        }
                        maki.preparar();
                        System.out.println("Añadido: " + maki.toString());
                        break;
                    case 4:
                        procesarPasarelaPago();
                        opCarta = 0; 
                        break;
                    case 0: break;
                    default: System.out.println("Opción no válida."); break;
                }
            } catch (PedidoNoDisponibleException e) {
                System.out.println("\n[EXCEPCIÓN CONTROLADA] -> " + e.toString());
                e.reportarError();
            }
        } while (opCarta != 0);
    }

    private static void procesarPasarelaPago() {
        System.out.println("\n--- Pasarela de Pago ---");
        EO3_Pago pago = new EO3_Pago(2, 20.95, "Efectivo"); 
        
        try {
            if (!pago.validarPago()) {
                throw new PagoFallidoException("Saldo insuficiente o tarjeta rechazada.");
            }
            if (pago.procesarPago()) {
                System.out.println(pago.generarRecibo());
                System.out.println("¡Pedido completado con éxito!");
            }
        } catch (PagoFallidoException e) { 
            System.out.println("\n[FALLO DE FACTURACIÓN] -> " + e.toString());
            e.reportarError();
        } catch (RestauranteException e) {
            e.reportarError();
        }
    }

    // =========================================================================
    // 2. CAPA GESTIÓN DE TRABAJADOR
    // =========================================================================
    private static void menuGestionTrabajador() {
        int opTrabajador;
        do {
            System.out.println("\n--------------------------------------------------");
            System.out.println("             GESTIÓN DE TRABAJADOR                ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Añadir Trabajador");
            System.out.println("2. Eliminar Trabajador");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opTrabajador = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) { opTrabajador = -1; }

            switch (opTrabajador) {
                case 1: datosNuevoTrabajador(); break;
                case 2: System.out.println("[INFO] Lógica para eliminar de trabajadorGuardado.txt"); break;
                case 0: break;
                default: System.out.println("Opción no válida."); break;
            }
        } while (opTrabajador != 0);
    }

    private static void datosNuevoTrabajador() {
        System.out.println("\n--- Formulario de Nuevo Trabajador ---");
        System.out.print("Introduzca Nombre: ");
        teclado.nextLine();
        
        System.out.println("Seleccione el Rol:");
        System.out.println("1. COCINERO");
        System.out.println("2. CAMARERO");
        System.out.println("3. REPARTIDOR");
        System.out.println("4. JEFE");
        System.out.print("Opción: ");
        String rolSeleccionado = "CAMARERO";
        int opRol = Integer.parseInt(teclado.nextLine());
        if (opRol == 1) rolSeleccionado = "COCINERO";
        if (opRol == 3) rolSeleccionado = "REPARTIDOR";
        if (opRol == 4) rolSeleccionado = "JEFE";

        int idTrabajadorAleatorio = (int) (Math.random() * 9000) + 1000;
        System.out.println("\n[SISTEMA] Trabajador registrado con éxito.");
        System.out.println("ID Asignado: " + idTrabajadorAleatorio + " | Rol: " + rolSeleccionado);

        if (rolSeleccionado.equals("JEFE")) {
            menuExclusivoJefe();
        }
    }

    private static void menuExclusivoJefe() {
        int opJefe;
        do {
            System.out.println("\n==================================================");
            System.out.println("          PANEL DE CONTROL - ROL: JEFE            ");
            System.out.println("==================================================");
            System.out.println("1. Añadir Ingredientes al Inventario");
            System.out.println("2. Eliminar Ingredientes del Inventario");
            System.out.println("3. Añadir Plato (Definir ingredientes necesarios)");
            System.out.println("4. Eliminar Plato del Sistema");
            System.out.println("0. Salir del Panel de Jefe");
            System.out.print("Seleccione una opción: ");
            try {
                opJefe = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) { opJefe = -1; }

            switch (opJefe) {
                case 1: System.out.println("[Inventario] Incrementando stock de GestionInventario.java..."); break;
                case 2: System.out.println("[Inventario] Reduciendo existencias del almacén..."); break;
                case 3:
                    System.out.print("Nombre del nuevo plato: ");
                    String nuevoPlato = teclado.nextLine();
                    System.out.print("Cantidad de ingredientes base requeridos: ");
                    int cant = Integer.parseInt(teclado.nextLine());
                    System.out.println("¡Plato [" + nuevoPlato + "] indexado con " + cant + " insumos en la aplicación!");
                    break;
                case 4: System.out.println("[Ficheros] Modificando inventarioGuardado.txt para remover plato..."); break;
                case 0: System.out.println("Saliendo del Panel de Administración."); break;
                default: System.out.println("Opción no válida."); break;
            }
        } while (opJefe != 0);
    }

    // =========================================================================
    // GUARDAR EN FICHERO
    // =========================================================================
    private static void guardarClienteEnFichero(EO1_Cliente cliente) {
        File fichero = new File(RUTA_FICHERO_CLIENTES);
        
        if (!fichero.exists()) {
            try {
                fichero.getParentFile().mkdirs();
                fichero.createNewFile();
            } catch (IOException e) {
                System.out.println("[ERROR FICH] No se pudo inicializar el archivo físico: " + e.getMessage());
                return;
            }
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero, true))) {
            String lineaCliente = cliente.getIdCliente() + ";" + 
                                 cliente.getNombreCliente() + ";" + 
                                 cliente.getTelefono() + ";" + 
                                 cliente.getDireccion();
            
            escritor.write(lineaCliente);
            escritor.newLine();
            System.out.println("[FICHEROS] -> Cliente volcado con éxito en 'clienteGuardado.txt'.");
            
        } catch (IOException e) {
            System.out.println("[ERROR CRÍTICO] Imposible escribir en el archivo de base de datos: " + e.getMessage());
        }
    }
}