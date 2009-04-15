package ar.com.nextel.sfa.client.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.CuentaPotencial;
import ar.com.nextel.model.personas.beans.Domicilio;
import ar.com.nextel.model.solicitudes.beans.EstadoAprobacionSolicitud;
import ar.com.nextel.model.solicitudes.beans.GrupoSolicitud;
import ar.com.nextel.model.solicitudes.beans.LineaSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.OrigenSolicitud;
import ar.com.nextel.model.solicitudes.beans.TipoAnticipo;

public class SolicitudServicioDtoB {

	private Long id;
    private VendedorDto vendedor;
    private GrupoSolicitud grupoSolicitud;
    private Cuenta cuenta;
    private String numero;
    private String numeroFlota;

    private CuentaPotencial cuentaPotencial;
    private OrigenSolicitud origen;
    private Vendedor vendedorDAE;

    private Domicilio domicilioFacturacion;
    private Domicilio domicilioEnvio;
    private String aclaracionEntrega;
    private String email;
    private String emailLicencias;

    private Double montoCreditoFideliacion;
    private String fechaVencimientoCreditoFidelizacion;
    private String montoDisponible;
    private Long channelCode;
    private Double pataconex;

    private Boolean firmar = Boolean.FALSE;
    private EstadoAprobacionSolicitud estadoActual;
    private String observaciones;

    private TipoAnticipo tipoAnticipo;

    private Set<LineaSolicitudServicio> lineas = new HashSet<LineaSolicitudServicio>();

    private Boolean pinValidationSuccess;
    private Boolean scoringValidationSuccess;
    private Long idVantive;

    private Boolean vencimientoProcesado;

    private Boolean generada;

    private Boolean enCarga;

    private Date fechaCreacion;
    
}
