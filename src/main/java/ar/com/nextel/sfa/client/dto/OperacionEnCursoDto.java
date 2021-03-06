package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OperacionEnCursoDto implements IsSerializable {

	private String id;
	private Long idCuenta;
	private Long idGrupoSolicitud;
	private String razonSocial;
	private String numeroCliente;
	private String descripcionGrupo;
	private Long idSolicitudServicio;
	
//	MGR - #4231 - Necesito el id de la factura
//	JM - #4520 - Necesito el numero de la factura	
	private String numeroFactura;

	public Long getIdSolicitudServicio() {
		return idSolicitudServicio;
	}

	public void setIdSolicitudServicio(Long idSolicitudServicio) {
		this.idSolicitudServicio = idSolicitudServicio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Long getIdGrupoSolicitud() {
		return idGrupoSolicitud;
	}

	public void setIdGrupoSolicitud(Long idGrupoSolicitud) {
		this.idGrupoSolicitud = idGrupoSolicitud;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNumeroCliente() {
		return numeroCliente;
	}

	public void setNumeroCliente(String numeroCliente) {
		this.numeroCliente = numeroCliente;
	}

	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}

	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}

//	public SolicitudServicioDto getSolicitudServicio() {
//		return solicitudServicio;
//	}
//
//	public void setSolicitudServicio(SolicitudServicioDto solicitudServicio) {
//		this.solicitudServicio = solicitudServicio;
//	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
}
