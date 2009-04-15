package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import ar.com.nextel.model.solicitudes.beans.TipoAnticipo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioDto implements IsSerializable {

	private Long id;
	private VendedorDto vendedor;
	private GrupoSolicitudDto grupoSolicitud;
	private CuentaDto cuenta;
	private String numero;
	private String numeroFlota;

	// private CuentaPotencial cuentaPotencial;
	// private OrigenSolicitud origen;
	// private Vendedor vendedorDAE;

	private DomicilioDto domicilioFacturacion;
	private DomicilioDto domicilioEnvio;
	private String aclaracionEntrega;
	private String email;
	private String emailLicencias;

	private Double montoCreditoFideliacion;
	private String fechaVencimientoCreditoFidelizacion;
	private String montoDisponible;
	private Long channelCode;
	private Double pataconex;

	private Boolean firmar = Boolean.FALSE;
	// private EstadoAprobacionSolicitud estadoActual;
	private String observaciones;

//	private TipoAnticipo tipoAnticipo;

	// private Set<LineaSolicitudServicio> lineas = new HashSet<LineaSolicitudServicio>();

	private Boolean pinValidationSuccess;
	private Boolean scoringValidationSuccess;
	private Long idVantive;

	private Boolean vencimientoProcesado;

	private Boolean generada;

	private Boolean enCarga;

	private Date fechaCreacion;

}
