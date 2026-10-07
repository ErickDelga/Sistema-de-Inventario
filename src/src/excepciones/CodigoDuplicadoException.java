
package src.excepciones;
/**
 * Se lanza cuando se intenta registrar un producto con un código ya existente.
 */
public class CodigoDuplicadoException extends Exception {
    public CodigoDuplicadoException(String mensaje) {
        super(mensaje);
    }

    public CodigoDuplicadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
