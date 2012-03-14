package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoVendedorDto implements IsSerializable {

	private Long id;

	private String codigo;
    private String codigoVantive;
    private String descripcion;
    private List<GrupoSolicitudDto> grupos;
    private boolean ejecutaNegFiles;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigoVantive() {
		return codigoVantive;
	}
	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<GrupoSolicitudDto> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<GrupoSolicitudDto> grupos) {
		this.grupos = grupos;
	}
	public boolean isEjecutaNegFiles() {
		return ejecutaNegFiles;
	}
	public void setEjecutaNegFiles(boolean ejecutaNegFiles) {
		this.ejecutaNegFiles = ejecutaNegFiles;
	}
}