package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CuentaPotencialDto implements IsSerializable {
	private Long id;
	private String codigoVantive;
	private String numero;
	private Date fechaVencimiento;
	private Date fechaAsignacion;
	private CuentaDto cuentaOrigen;
	private Short terminalesEstimadas;
	private String diaHorarioVisitas;
	private PersonaDto persona;
	private RubroDto rubro;
	private VendedorDto vendedor;
	private ArrayList<ComentarioDto> comentarios = new ArrayList<ComentarioDto>();
	// private List<ContactoCuentaPotencial> contactos = new HashSet<ContactoCuentaPotencial>();
	private Boolean vencimientoProcesado = Boolean.FALSE;
	private Boolean vencimientoLockOportunidad = Boolean.FALSE;
	private Boolean consultada = Boolean.FALSE;
	protected boolean esReserva;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoVantive() {
		return codigoVantive;
	}

	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public CuentaDto getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(CuentaDto cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public Short getTerminalesEstimadas() {
		return terminalesEstimadas;
	}

	public void setTerminalesEstimadas(Short terminalesEstimadas) {
		this.terminalesEstimadas = terminalesEstimadas;
	}

	public String getDiaHorarioVisitas() {
		return diaHorarioVisitas;
	}

	public void setDiaHorarioVisitas(String diaHorarioVisitas) {
		this.diaHorarioVisitas = diaHorarioVisitas;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}

	public RubroDto getRubro() {
		return rubro;
	}

	public void setRubro(RubroDto rubro) {
		this.rubro = rubro;
	}

	public VendedorDto getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorDto vendedor) {
		this.vendedor = vendedor;
	}

	public Boolean getVencimientoProcesado() {
		return vencimientoProcesado;
	}

	public void setVencimientoProcesado(Boolean vencimientoProcesado) {
		this.vencimientoProcesado = vencimientoProcesado;
	}

	public Boolean getVencimientoLockOportunidad() {
		return vencimientoLockOportunidad;
	}

	public void setVencimientoLockOportunidad(Boolean vencimientoLockOportunidad) {
		this.vencimientoLockOportunidad = vencimientoLockOportunidad;
	}

	public Boolean getConsultada() {
		return consultada;
	}

	public void setConsultada(Boolean consultada) {
		this.consultada = consultada;
	}

	public boolean isEsReserva() {
		return esReserva;
	}

	public void setEsReserva(boolean esReserva) {
		this.esReserva = esReserva;
	}

	public ArrayList<ComentarioDto> getComentarios() {
		return comentarios;
	}

	public void setComentarios(ArrayList<ComentarioDto> comentarios) {
		this.comentarios = comentarios;
	}

}
