
package src.vista;
import src.excepciones.CodigoDuplicadoException;
import src.excepciones.ProductoNoEncontradoException;
import src.modelo.Producto;
import src.servicio.InventarioService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Capa de presentación: interactúa con el usuario mediante consola.
 */
public class Menu {
     private final InventarioService servicio;
    private final Scanner scanner;

    public Menu(InventarioService servicio) {
        this.servicio = servicio;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("👉 Elige una opción: ");
            System.out.println();
            try {
                switch (opcion) {
                    case 1 -> registrar();
                    case 2 -> modificar();
                    case 3 -> eliminar();
                    case 4 -> buscar();
                    case 5 -> listar();
                    case 6 -> guardar();
                    case 7 -> cargar();
                    case 0 -> System.out.println("👋 ¡Hasta luego!");
                    default -> System.out.println("⚠️ Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
            System.out.println();
        } while (opcion != 0);
    }

    // ---------- MENÚ ----------
    private void mostrarMenu() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   📦 SISTEMA DE INVENTARIO       ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║  1. Registrar producto           ║");
        System.out.println("║  2. Modificar producto           ║");
        System.out.println("║  3. Eliminar producto            ║");
        System.out.println("║  4. Buscar producto              ║");
        System.out.println("║  5. Listar inventario            ║");
        System.out.println("║  6. Guardar inventario           ║");
        System.out.println("║  7. Cargar inventario            ║");
        System.out.println("║  0. Salir                        ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    // ---------- OPERACIONES ----------
    private void registrar() throws CodigoDuplicadoException {
        System.out.println("── Registrar producto ──");
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDouble("Precio: ");
        int cantidad = leerEntero("Cantidad: ");

        Producto p = new Producto(codigo, nombre, precio, cantidad);
        servicio.registrarProducto(p);
        System.out.println("✅ Producto registrado correctamente.");
    }

    private void modificar() throws ProductoNoEncontradoException {
        System.out.println("── Modificar producto ──");
        String codigo = leerTexto("Código del producto a modificar: ");
        Producto actual = servicio.buscarProducto(codigo);

        System.out.println("Datos actuales: " + actual);
        String nombre = leerTexto("Nuevo nombre (" + actual.getNombre() + "): ");
        if (nombre.isEmpty()) nombre = actual.getNombre();

        System.out.print("Nuevo precio (" + actual.getPrecio() + "): ");
        String precioStr = scanner.nextLine().trim();
        double precio = precioStr.isEmpty() ? actual.getPrecio() : Double.parseDouble(precioStr);

        System.out.print("Nueva cantidad (" + actual.getCantidad() + "): ");
        String cantStr = scanner.nextLine().trim();
        int cantidad = cantStr.isEmpty() ? actual.getCantidad() : Integer.parseInt(cantStr);

        servicio.modificarProducto(codigo, new Producto(codigo, nombre, precio, cantidad));
        System.out.println("✅ Producto modificado correctamente.");
    }

    private void eliminar() throws ProductoNoEncontradoException {
        System.out.println("── Eliminar producto ──");
        String codigo = leerTexto("Código del producto a eliminar: ");
        servicio.eliminarProducto(codigo);
        System.out.println("✅ Producto eliminado correctamente.");
    }

    private void buscar() throws ProductoNoEncontradoException {
        System.out.println("── Buscar producto ──");
        String codigo = leerTexto("Código: ");
        Producto p = servicio.buscarProducto(codigo);
        System.out.println("🔍 Encontrado: " + p);
    }

    private void listar() {
        System.out.println("── Inventario actual ──");
        List<Producto> lista = servicio.listarProductos();
        if (lista.isEmpty()) {
            System.out.println("(Inventario vacío)");
            return;
        }
        System.out.printf("%-10s %-20s %10s %10s%n", "CÓDIGO", "NOMBRE", "PRECIO", "CANTIDAD");
        System.out.println("─".repeat(55));
        for (Producto p : lista) {
            System.out.printf("%-10s %-20s %10.2f %10d%n",
                    p.getCodigo(), p.getNombre(), p.getPrecio(), p.getCantidad());
        }
        System.out.println("─".repeat(55));
        System.out.println("Total: " + lista.size() + " producto(s).");
    }

    private void guardar() {
        try {
            servicio.guardarInventario();
            System.out.println("💾 Inventario guardado correctamente.");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar: " + e.getMessage());
        }
    }

    private void cargar() {
        try {
            servicio.cargarInventario();
            System.out.println("📂 Inventario cargado correctamente. Total: "
                    + servicio.totalProductos() + " producto(s).");
        } catch (IOException e) {
            System.out.println("❌ Error al cargar: " + e.getMessage());
        }
    }

    // ---------- UTILIDADES DE LECTURA ----------
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Debes ingresar un número entero.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Debes ingresar un número decimal.");
            }
        }
    }
}
