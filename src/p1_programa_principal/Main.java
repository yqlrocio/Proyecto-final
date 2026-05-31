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
    
    // RUTAS DE LOS FICHEROS
    private static final String RUTA_FICHERO_CLIENTES = "src/p2_almacenamiento_de_datos/clienteGuardado.txt";
    private static final String RUTA_FICHERO_TRABAJADORES = "src/p2_almacenamiento_de_datos/trabajadorGuardado.txt";
    private static final String RUTA_FICHERO_PAGOS = "src/p2_almacenamiento_de_datos/pagoGuardado.txt";
    private static final String RUTA_FICHERO_INVENTARIO = "src/p2_almacenamiento_de_datos/inventarioGuardado.txt";

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

    private static void mostrarClientesDelFichero() {
        System.out.println("\n==================================================");
        System.out.println("           LISTADO DE CLIENTES REGISTRADOS        ");
        System.out.println("==================================================");

        File fichero = new File(RUTA_FICHERO_CLIENTES);
        if (!fichero.exists() || fichero.length() == 0) {
            System.out.println("[AVISO] El archivo de datos está vacío o no existe ningún cliente aún.");
            return;
        }

        List<EO1_Cliente> listaClientes = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 4) {
                    listaClientes.add(new EO1_Cliente(Integer.parseInt(datos[0]), datos[1], datos[2], datos[3]));
                }
            }
            // STREAMS & LAMBDAS
            listaClientes.stream().forEach(cliente -> {
                System.out.println("ID: " + cliente.getIdCliente() + 
                                   " | Nombre: " + cliente.getNombreCliente() + 
                                   " | Teléfono: " + cliente.getTelefono() + 
                                   " | Dirección: " + cliente.getDireccion());
            });
            System.out.println("--------------------------------------------------");
            System.out.println("Total de clientes registrados: " + listaClientes.size());
        } catch (IOException e) {
            System.out.println("[ERROR] Error al leer clientes: " + e.getMessage());
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
                    EO2_Trabajador camarero = new EO2_Trabajador(201, "Carlos Gómez", Rol.CAMARERO);
                    System.out.println("\n==================================================");
                    System.out.println(" -> ATENDIDO POR: " + camarero.getNombreTrabajador() + " (" + camarero.getRol() + ")");
                    System.out.println("==================================================");
                    camarero.gestionarPedido();
                    menuCartaProductos("Restaurante");
                    opTipo = 0; 
                    break;
                case 2: 
                    System.out.println("[SISTEMA] Pedido Para Llevar Inicializado.");
                    EO2_Trabajador cocinero = new EO2_Trabajador(305, "Xiao Long", Rol.COCINERO);
                    System.out.println("\n==================================================");
                    System.out.println(" -> CHEF EN COCINA: " + cocinero.getNombreTrabajador() + " (" + cocinero.getRol() + ")");
                    System.out.println("==================================================");
                    cocinero.prepararPedido();
                    menuCartaProductos("Llevar");
                    opTipo = 0; 
                    break;
                case 0: System.out.println("Pedido cancelado."); break;
                default: System.out.println("Opción no válida."); break;
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
                guardarPagoEnFichero(pago);
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
            System.out.println("2. Ver Lista de Trabajadores (Leer Fichero)"); // MODIFICADO
            System.out.println("3. Eliminar Trabajador");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opTrabajador = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) { opTrabajador = -1; }

            switch (opTrabajador) {
                case 1: datosNuevoTrabajador(); break;
                case 2: mostrarTrabajadoresDelFichero(); break; // MODIFICADO
                case 3: System.out.println("[INFO] Lógica para eliminar de trabajadorGuardado.txt"); break;
                case 0: break;
                default: System.out.println("Opción no válida."); break;
            }
        } while (opTrabajador != 0);
    }

    private static void datosNuevoTrabajador() {
        System.out.println("\n--- Formulario de Nuevo Trabajador ---");
        System.out.print("Introduzca Nombre: ");
        String nombreTrabajador = teclado.nextLine();
        
        System.out.println("Seleccione el Rol:");
        System.out.println("1. COCINERO");
        System.out.println("2. CAMARERO");
        System.out.println("3. REPARTIDOR");
        System.out.println("4. JEFE");
        System.out.print("Opción: ");
        
        Rol rolEnum = Rol.CAMARERO;
        String rolSeleccionado = "CAMARERO";
        int opRol = Integer.parseInt(teclado.nextLine());
        if (opRol == 1) { rolSeleccionado = "COCINERO"; rolEnum = Rol.COCINERO; }
        if (opRol == 3) { rolSeleccionado = "REPARTIDOR"; rolEnum = Rol.REPARTIDOR; }
        if (opRol == 4) { rolSeleccionado = "JEFE"; rolEnum = Rol.JEFE; }

        int idTrabajadorAleatorio = (int) (Math.random() * 9000) + 1000;
        System.out.println("\n[SISTEMA] Trabajador registrado con éxito.");
        System.out.println("ID Asignado: " + idTrabajadorAleatorio + " | Rol: " + rolSeleccionado);

        EO2_Trabajador nuevoEmp = new EO2_Trabajador(idTrabajadorAleatorio, nombreTrabajador, rolEnum);
        guardarTrabajadorEnFichero(nuevoEmp);

        if (rolSeleccionado.equals("JEFE")) {
            menuExclusivoJefe();
        }
    }

    // NUEVO MÉTODO: LEER Y MOSTRAR TRABAJADORES CON STREAMS
    private static void mostrarTrabajadoresDelFichero() {
        System.out.println("\n==================================================");
        System.out.println("         LISTADO DE PERSONAL DEL RESTAURANTE      ");
        System.out.println("==================================================");

        File fichero = new File(RUTA_FICHERO_TRABAJADORES);
        if (!fichero.exists() || fichero.length() == 0) {
            System.out.println("[AVISO] No hay ningún trabajador contratado registrado aún.");
            return;
        }

        List<EO2_Trabajador> listaEmp = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 3) {
                    listaEmp.add(new EO2_Trabajador(Integer.parseInt(datos[0]), datos[1], Rol.valueOf(datos[2])));
                }
            }
            // STREAMS & LAMBDAS
            listaEmp.stream().forEach(emp -> {
                System.out.println("ID Empleado: " + emp.getIdTrabajador() + 
                                   " | Nombre: " + emp.getNombreTrabajador() + 
                                   " | Rol del Puesto: " + emp.getRol());
            });
            System.out.println("--------------------------------------------------");
            System.out.println("Total plantilla activa: " + listaEmp.size());
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo leer el fichero de personal: " + e.getMessage());
        }
    }

    private static void menuExclusivoJefe() {
        int opJefe;
        do {
            System.out.println("\n==================================================");
            System.out.println("          PANEL DE CONTROL - ROL: JEFE            ");
            System.out.println("==================================================");
            System.out.println("1. Añadir Ingredientes al Inventario");
            System.out.println("2. Ver Inventario de Platos (Leer Fichero)"); // MODIFICADO
            System.out.println("3. Añadir Plato (Definir ingredientes necesarios)");
            System.out.println("4. Eliminar Plato del Sistema");
            System.out.println("0. Salir del Panel de Jefe");
            System.out.print("Seleccione una opción: ");
            try {
                opJefe = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) { opJefe = -1; }

            switch (opJefe) {
                case 1: System.out.println("[Inventario] Incrementando stock de GestionInventario.java..."); break;
                case 2: mostrarInventarioDelFichero(); break; // MODIFICADO
                case 3:
                    System.out.print("Nombre del nuevo plato: ");
                    String nuevoPlato = teclado.nextLine();
                    System.out.print("Cantidad de ingredientes base requeridos: ");
                    int cant = Integer.parseInt(teclado.nextLine());
                    System.out.println("¡Plato [" + nuevoPlato + "] indexado con " + cant + " insumos en la aplicación!");
                    guardarPlatoEnInventario(nuevoPlato, cant);
                    break;
                case 4: System.out.println("[Ficheros] Modificando inventarioGuardado.txt para remover plato..."); break;
                case 0: System.out.println("Saliendo del Panel de Administración."); break;
                default: System.out.println("Opción no válida."); break;
            }
        } while (opJefe != 0);
    }

    // NUEVO MÉTODO: LEER Y MOSTRAR INVENTARIO CON STREAMS
    private static void mostrarInventarioDelFichero() {
        System.out.println("\n==================================================");
        System.out.println("         INVENTARIO ACTUAL DE PLATOS / CARTAS     ");
        System.out.println("==================================================");

        File fichero = new File(RUTA_FICHERO_INVENTARIO);
        if (!fichero.exists() || fichero.length() == 0) {
            System.out.println("[AVISO] El almacén está vacío. No hay platos creados por Dirección.");
            return;
        }

        List<String> lineasPlatos = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                lineasPlatos.add(linea);
            }
            // STREAMS & LAMBDAS
            lineasPlatos.stream().forEach(lineaPlato -> {
                String[] datos = lineaPlato.split(";");
                if (datos.length == 2) {
                    System.out.println(" -> Plato: " + datos[0] + " | Recursos de Cocina Requeridos: " + datos[1] + " uds.");
                }
            });
            System.out.println("--------------------------------------------------");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo leer el inventario físico: " + e.getMessage());
        }
    }

    // =========================================================================
    // MÉTODOS DE ESCRITURA EN FICHEROS TXT
    // =========================================================================
    private static void guardarClienteEnFichero(EO1_Cliente cliente) {
        File fichero = new File(RUTA_FICHERO_CLIENTES);
        verificarYCrearFichero(fichero);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero, true))) {
            String lineaCliente = cliente.getIdCliente() + ";" + cliente.getNombreCliente() + ";" + cliente.getTelefono() + ";" + cliente.getDireccion();
            escritor.write(lineaCliente);
            escritor.newLine();
            System.out.println("[FICHEROS] -> Datos guardados en 'clienteGuardado.txt'.");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo escribir en el archivo de clientes: " + e.getMessage());
        }
    }

    private static void guardarTrabajadorEnFichero(EO2_Trabajador empleado) {
        File fichero = new File(RUTA_FICHERO_TRABAJADORES);
        verificarYCrearFichero(fichero);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero, true))) {
            String lineaTrabajador = empleado.getIdTrabajador() + ";" + empleado.getNombreTrabajador() + ";" + empleado.getRol();
            escritor.write(lineaTrabajador);
            escritor.newLine();
            System.out.println("[FICHEROS] -> Trabajador guardado en 'trabajadorGuardado.txt'.");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo registrar físicamente al trabajador: " + e.getMessage());
        }
    }

    private static void guardarPagoEnFichero(EO3_Pago pago) {
        File fichero = new File(RUTA_FICHERO_PAGOS);
        verificarYCrearFichero(fichero);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero, true))) {
            String lineaPago = "ID_PAGO: " + (int)(Math.random()*1000) + " | Detalle: " + pago.generarRecibo().replace("\n", " ");
            escritor.write(lineaPago);
            escritor.newLine();
            System.out.println("[FICHEROS] -> Comprobante fiscal volcado en 'pagoGuardado.txt'.");
        } catch (IOException e) {
            System.out.println("[ERROR] Fallo en el volcado de auditoría de pagos: " + e.getMessage());
        }
    }

    private static void guardarPlatoEnInventario(String nombrePlato, int ingredientesRequeridos) {
        File fichero = new File(RUTA_FICHERO_INVENTARIO);
        verificarYCrearFichero(fichero);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero, true))) {
            String lineaInventario = nombrePlato + ";" + ingredientesRequeridos;
            escritor.write(lineaInventario);
            escritor.newLine();
            System.out.println("[FICHEROS] -> Receta añadida con éxito en 'inventarioGuardado.txt'.");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo actualizar el almacén central de datos: " + e.getMessage());
        }
    }

    private static void verificarYCrearFichero(File fichero) {
        if (!fichero.exists()) {
            try {
                fichero.getParentFile().mkdirs();
                fichero.createNewFile();
            } catch (IOException e) {
                System.out.println("[ERROR CRÍTICO] Imposible inicializar ruta del sistema de datos: " + e.getMessage());
            }
        }
    }
}