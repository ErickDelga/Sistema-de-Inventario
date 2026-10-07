package src.persistencia;

import src.modelo.Producto;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Encargada de guardar y cargar el inventario en un archivo de texto plano.
 * Formato por línea: codigo;nombre;precio;cantidad
 */
public class InventarioArchivo {

    private final Path ruta;

    public InventarioArchivo(String rutaArchivo) {
        this.ruta = Paths.get(rutaArchivo);
    }

    public InventarioArchivo(Path ruta) {
        this.ruta = ruta;
    }

    public Path getRuta() {
        return ruta;
    }

    // ---------- GUARDAR ----------
    public void guardar(List<Producto> productos) throws IOException {
        // Crear directorio padre si no existe
        if (ruta.getParent() != null) {
            Files.createDirectories(ruta.getParent());
        }

        try (BufferedWriter bw = Files.newBufferedWriter(ruta,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {

            for (Producto p : productos) {
                bw.write(String.format("%s;%s;%.2f;%d",
                        p.getCodigo(),
                        p.getNombre(),
                        p.getPrecio(),
                        p.getCantidad()));
                bw.newLine();
            }
        }
    }

    // ---------- CARGAR ----------
    public List<Producto> cargar() throws IOException {
        List<Producto> productos = new ArrayList<>();

        if (!Files.exists(ruta)) {
            return productos; // archivo no existe → lista vacía
        }

        try (BufferedReader br = Files.newBufferedReader(ruta)) {
            String linea;
            int numeroLinea = 0;
            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(";");
                if (partes.length != 4) {
                    System.err.println("⚠️ Línea " + numeroLinea + " mal formada, se omite: " + linea);
                    continue;
                }

                try {
                    String codigo = partes[0];
                    String nombre = partes[1];
                    double precio = Double.parseDouble(partes[2]);
                    int cantidad = Integer.parseInt(partes[3]);
                    productos.add(new Producto(codigo, nombre, precio, cantidad));
                } catch (NumberFormatException e) {
                    System.err.println("⚠️ Error numérico en línea " + numeroLinea + ": " + linea);
                }
            }
        }
        return productos;
    }
}
