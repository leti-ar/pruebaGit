package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LineaSolicitudServicioDto implements IsSerializable {

	private Long id;
	private SolicitudServicioDto cabecera;

	private TipoSolicitudDto tipoSolicitud;
	private ItemSolicitudDto item;
	private PlanDto plan;

	private List<ServicioAdicionalLineaSolicitudServicioDto> serviciosAdicionales;

	private ListaPreciosDto listaPrecios;
	private TerminoPagoDto terminoPago;
	// private List<DescuentoLinea> descuentos;

	private Long numeradorLinea;
	private Double precioLista;
	private Double precioVenta;
	private Double precioListaPlan;
	private Double precioListaAjustado;
	private Double precioVentaPlan;
	private ModalidadCobroDto modalidadCobro;

	private String numeroReserva;
	private String numeroReservaArea;
	private String numeroIMEI;
	private String numeroSimcard;
	private String numeroSerie;
	private List<NumeroANIDto> numerosANI;
	private String alias;
	private Integer cantidad;

//	private Localidad localidad;

	private EstadoVerificacionNegativeFilesDto estadoVerificacionNegativeFilesIMEI;
	private EstadoVerificacionNegativeFilesDto estadoVerificacionNegativeFilesSIM;

	private Boolean ddn = Boolean.FALSE;
	private Boolean ddi = Boolean.FALSE;
	private Boolean roaming = Boolean.FALSE;

//	private Modelo modelo;
}
