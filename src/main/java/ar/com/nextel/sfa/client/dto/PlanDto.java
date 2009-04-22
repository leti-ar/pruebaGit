package ar.com.nextel.sfa.client.dto;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PlanDto implements IsSerializable{
    private String descripcion;
    private Long ordenAparicion;
    private Boolean frecuente;
    private TipoTelefoniaDto tipoTelefonia;
    private PlanBaseDto planBase;
    private Boolean anexaPorScoring = Boolean.TRUE;
    private Set<ServicioAdicionalIncluidoDto> serviciosAdicionalesIncluidos = new HashSet<ServicioAdicionalIncluidoDto>();
    private ServicioAdicionalIncluidoDto servicioAdicionalPrecio;
    private Set<ModalidadCobroDto> modalidadesCobro = new HashSet<ModalidadCobroDto>();
    private static String namedQueryServiciosAdicionalesInlcuidos = "SERVICIOS_ADICIONALES_INCLUIDOS";
    private static String namedQueryServiciosAdicionalesInlcuidosNoVisibles = "SERVICIOS_ADICIONALES_INCLUIDOS_NO_VISIBLES";
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getOrdenAparicion() {
		return ordenAparicion;
	}
	public void setOrdenAparicion(Long ordenAparicion) {
		this.ordenAparicion = ordenAparicion;
	}
	public Boolean getFrecuente() {
		return frecuente;
	}
	public void setFrecuente(Boolean frecuente) {
		this.frecuente = frecuente;
	}
	public TipoTelefoniaDto getTipoTelefonia() {
		return tipoTelefonia;
	}
	public void setTipoTelefonia(TipoTelefoniaDto tipoTelefonia) {
		this.tipoTelefonia = tipoTelefonia;
	}
	public PlanBaseDto getPlanBase() {
		return planBase;
	}
	public void setPlanBase(PlanBaseDto planBase) {
		this.planBase = planBase;
	}
	public Boolean getAnexaPorScoring() {
		return anexaPorScoring;
	}
	public void setAnexaPorScoring(Boolean anexaPorScoring) {
		this.anexaPorScoring = anexaPorScoring;
	}
	public Set<ServicioAdicionalIncluidoDto> getServiciosAdicionalesIncluidos() {
		return serviciosAdicionalesIncluidos;
	}
	public void setServiciosAdicionalesIncluidos(
			Set<ServicioAdicionalIncluidoDto> serviciosAdicionalesIncluidos) {
		this.serviciosAdicionalesIncluidos = serviciosAdicionalesIncluidos;
	}
	public ServicioAdicionalIncluidoDto getServicioAdicionalPrecio() {
		return servicioAdicionalPrecio;
	}
	public void setServicioAdicionalPrecio(
			ServicioAdicionalIncluidoDto servicioAdicionalPrecio) {
		this.servicioAdicionalPrecio = servicioAdicionalPrecio;
	}
	public Set<ModalidadCobroDto> getModalidadesCobro() {
		return modalidadesCobro;
	}
	public void setModalidadesCobro(Set<ModalidadCobroDto> modalidadesCobro) {
		this.modalidadesCobro = modalidadesCobro;
	}
	public static String getNamedQueryServiciosAdicionalesInlcuidos() {
		return namedQueryServiciosAdicionalesInlcuidos;
	}
	public static void setNamedQueryServiciosAdicionalesInlcuidos(
			String namedQueryServiciosAdicionalesInlcuidos) {
		PlanDto.namedQueryServiciosAdicionalesInlcuidos = namedQueryServiciosAdicionalesInlcuidos;
	}
	public static String getNamedQueryServiciosAdicionalesInlcuidosNoVisibles() {
		return namedQueryServiciosAdicionalesInlcuidosNoVisibles;
	}
	public static void setNamedQueryServiciosAdicionalesInlcuidosNoVisibles(
			String namedQueryServiciosAdicionalesInlcuidosNoVisibles) {
		PlanDto.namedQueryServiciosAdicionalesInlcuidosNoVisibles = namedQueryServiciosAdicionalesInlcuidosNoVisibles;
	}
}
