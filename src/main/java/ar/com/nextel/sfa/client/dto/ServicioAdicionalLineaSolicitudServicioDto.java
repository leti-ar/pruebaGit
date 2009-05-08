package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServicioAdicionalLineaSolicitudServicioDto implements IsSerializable{

	private LineaSolicitudServicioDto linea;
	private Double precioLista;
	private Double precioVenta;
	private ServicioAdicionalDto servicioAdicional;

	private String descripcionServicioAdicional;
	private Boolean esGarantia;
	private Boolean esAlquiler;
	private Boolean esWap;
	private Boolean esTethered;
	private Long ordenAparicion;

	private Boolean checked;
	private Boolean obligatorio;
}
