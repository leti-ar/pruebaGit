package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EstadoOportunidadJustificadoDto implements IsSerializable {
    private OportunidadNegocioDto oportunidad;
    private EstadoOportunidadDto estado;
    private MotivoNoCierreDto motivo;
    private String observacionesMotivo;
	public OportunidadNegocioDto getOportunidad() {
		return oportunidad;
	}
	public void setOportunidad(OportunidadNegocioDto oportunidad) {
		this.oportunidad = oportunidad;
	}
	public EstadoOportunidadDto getEstado() {
		return estado;
	}
	public void setEstado(EstadoOportunidadDto estado) {
		this.estado = estado;
	}
	public MotivoNoCierreDto getMotivo() {
		return motivo;
	}
	public void setMotivo(MotivoNoCierreDto motivo) {
		this.motivo = motivo;
	}
	public String getObservacionesMotivo() {
		return observacionesMotivo;
	}
	public void setObservacionesMotivo(String observacionesMotivo) {
		this.observacionesMotivo = observacionesMotivo;
	}

    
}
