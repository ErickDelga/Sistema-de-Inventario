import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import src.persistencia.InventarioArchivo;
import src.servicio.InventarioService;
import src.vista.Menu;


public class SistemaInventario {

    public static void main(String[] args) {
        // Línea de código para configurar la consola en UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        
       // Ruta del archivo de persistencia
        String rutaArchivo = "datos/inventario.txt";

        // Inyección de dependencias (manual)
        InventarioArchivo archivo = new InventarioArchivo(rutaArchivo);
        InventarioService servicio = new InventarioService(archivo);

        // Cargar inventario inicial si existe
        try {
            servicio.cargarInventario();
        } catch (Exception e) {
            System.err.println("⚠️ No se pudo cargar el inventario inicial: " + e.getMessage());
        }

        // Iniciar menú
        Menu menu = new Menu(servicio);
        menu.iniciar();
    }
    
}
