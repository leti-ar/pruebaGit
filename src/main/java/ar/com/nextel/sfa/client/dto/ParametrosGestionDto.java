package ar.com.nextel.sfa.client.dto;

public class ParametrosGestionDto {

	private long id;
	private String codGestion;
	private String categoria;
	private String subcategoria;
	private String descripcionResolucion;
	private boolean crearCerrada;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodGestion() {
		return codGestion;
	}

	public void setCodGestion(String codGestion) {
		this.codGestion = codGestion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

	public String getDescripcionResolucion() {
		return descripcionResolucion;
	}

	public void setDescripcionResolucion(String descripcionResolucion) {
		this.descripcionResolucion = descripcionResolucion;
	}

	public boolean isCrearCerrada() {
		return crearCerrada;
	}

	public void setCrearCerrada(boolean crearCerrada) {
		this.crearCerrada = crearCerrada;
	}

}
