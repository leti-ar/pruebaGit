package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OportunidadNegocioDto extends CuentaPotencialDto implements IsSerializable {
    
	private Integer cantidadEquiposCompetencia;
    private Short cantidadVisitas;
    private String clienteReferido;
    private Date fechaCreacion;
    private EstadoOportunidadJustificadoDto estadoJustificado;
    private String oferta;
    private PrioridadDto prioridad;
    private ProveedorDto proveedorCompetencia;
    private TipoContribuyenteDto tipoContribuyente;
    //private Set<Cuenta> cuentasCreadas = new HashSet<Cuenta>();

    public Integer getCantidadEquiposCompetencia() {
		return cantidadEquiposCompetencia;
	}
	public void setCantidadEquiposCompetencia(Integer cantidadEquiposCompetencia) {
		this.cantidadEquiposCompetencia = cantidadEquiposCompetencia;
	}
	public Short getCantidadVisitas() {
		return cantidadVisitas;
	}
	public void setCantidadVisitas(Short cantidadVisitas) {
		this.cantidadVisitas = cantidadVisitas;
	}
	public String getClienteReferido() {
		return clienteReferido;
	}
	public void setClienteReferido(String clienteReferido) {
		this.clienteReferido = clienteReferido;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public EstadoOportunidadJustificadoDto getEstadoJustificado() {
		return estadoJustificado;
	}
	public void setEstadoJustificado(EstadoOportunidadJustificadoDto estadoJustificado) {
		this.estadoJustificado = estadoJustificado;
	}
	public String getOferta() {
		return oferta;
	}
	public void setOferta(String oferta) {
		this.oferta = oferta;
	}
	public PrioridadDto getPrioridad() {
		return prioridad;
	}
	public void setPrioridadDto(PrioridadDto prioridad) {
		this.prioridad = prioridad;
	}
	public ProveedorDto getProveedorCompetencia() {
		return proveedorCompetencia;
	}
	public void setProveedorCompetencia(ProveedorDto proveedorCompetencia) {
		this.proveedorCompetencia = proveedorCompetencia;
	}
	public TipoContribuyenteDto getTipoContribuyente() {
		return tipoContribuyente;
	}
	public void setTipoContribuyente(TipoContribuyenteDto tipoContribuyente) {
		this.tipoContribuyente = tipoContribuyente;
	}
}
