package ar.com.nextel.sfa.client.ss;

/**
 * Clase para que el usuario no pueda aplicarle el mismo tipo de descuento a una linea
 *
 */
public class TipoDescuentoSeleccionado {

	Long idLinea;
	String descripcion;

	public Long getIdLineaSeleccionada() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
