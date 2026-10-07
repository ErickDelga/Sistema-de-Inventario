
package src.servicio;
import src.excepciones.CodigoDuplicadoException;
import src.excepciones.ProductoNoEncontradoException;
import src.modelo.Producto;
import src.persistencia.InventarioArchivo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Capa de lógica de negocio. Administra los productos y coordina la persistencia.
 */
public class InventarioService {
    private final List<Producto> productos;
    private final InventarioArchivo archivo;

    public InventarioService(InventarioArchivo archivo) {
        this.archivo = archivo;
        this.productos = new ArrayList<>();
    }

    // ---------- CARGAR INVENTARIO AL INICIAR ----------
    public void cargarInventario() throws IOException {
        productos.clear();
        productos.addAll(archivo.cargar());
    }

    // ---------- GUARDAR INVENTARIO ----------
    public void guardarInventario() throws IOException {
        archivo.guardar(productos);
    }

    // ---------- REGISTRAR ----------
    public void registrarProducto(Producto nuevo) throws CodigoDuplicadoException {
        if (nuevo == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (buscarInterno(nuevo.getCodigo()) != null) {
            throw new CodigoDuplicadoException(
                "Ya existe un producto con el código: " + nuevo.getCodigo());
        }
        productos.add(nuevo);
    }

    // ---------- MODIFICAR ----------
    public void modificarProducto(String codigo, Producto datosNuevos)
            throws ProductoNoEncontradoException {

        Producto existente = buscarInterno(codigo);
        if (existente == null) {
            throw new ProductoNoEncontradoException(
                "No se encontró producto con código: " + codigo);
        }
        existente.setNombre(datosNuevos.getNombre());
        existente.setPrecio(datosNuevos.getPrecio());
        existente.setCantidad(datosNuevos.getCantidad());
    }

    // ---------- ELIMINAR ----------
    public void eliminarProducto(String codigo) throws ProductoNoEncontradoException {
        Producto existente = buscarInterno(codigo);
        if (existente == null) {
            throw new ProductoNoEncontradoException(
                "No se encontró producto con código: " + codigo);
        }
        productos.remove(existente);
    }

    // ---------- BUSCAR ----------
    public Producto buscarProducto(String codigo) throws ProductoNoEncontradoException {
        Producto p = buscarInterno(codigo);
        if (p == null) {
            throw new ProductoNoEncontradoException(
                "No se encontró producto con código: " + codigo);
        }
        return p;
    }

    private Producto buscarInterno(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    // ---------- LISTAR ----------
    public List<Producto> listarProductos() {
        return new ArrayList<>(productos); // copia defensiva
    }

    public int totalProductos() {
        return productos.size();
    }
}
