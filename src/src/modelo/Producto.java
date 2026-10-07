
package src.modelo;

import java.io.Serializable;

/**
 * Representa un producto del inventario.
 */
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private double precio;
    private int cantidad;

    // ---------- Constructores ----------
    public Producto() { }

    public Producto(String codigo, String nombre, double precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // ---------- Getters y Setters ----------
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    // ---------- toString ----------
    @Override
    public String toString() {
        return String.format("Producto[codigo=%s, nombre=%s, precio=%.2f, cantidad=%d]",
                codigo, nombre, precio, cantidad);
    }

    // ---------- equals / hashCode (por código) ----------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Producto)) return false;
        Producto otro = (Producto) obj;
        return codigo != null && codigo.equals(otro.codigo);
    }

    @Override
    public int hashCode() {
        return codigo == null ? 0 : codigo.hashCode();
    }
}
