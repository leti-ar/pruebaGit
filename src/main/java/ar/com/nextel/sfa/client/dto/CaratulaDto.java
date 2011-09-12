package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CaratulaDto implements IsSerializable, IdentifiableDto, Cloneable, Comparable {

	private Long id;
	private Long idCuenta;
	
	private String documento; /* Caratula o Anexo */
	private String nroSS;
	private VendedorDto usuarioCreacion;
	private Date fechaCreacion;
	private Date fechaInicio;
	private String actividad;
	private ValidacionDomicilioDto validDomicilio;
	private BancoDto banco;  
	private String refBancarias;
	private TipoCuentaBancariaDto tipoCtaBancaria;
	private Double mayorSaldoFavor;
	private Double ingPromedio;
	private TipoTarjetaDto tipoTarjCred;
	private Double limiteTarjCred;
	private boolean ingresoDemostrado;
	private String iva;
	private String ganancia;
	private String balance;
	private String ingBrutos;
	private String reciboHaberes;
	private CalifCrediticiaDto califCred;
	private String factCelular;
	private boolean okEECCAgente;
	private ComDto com;
	private Date antiguedad;
	private Double limiteCredito;
	private CalificacionDto calificacion;
	private RiskCodeDto riskCode;
	private CompPagoDto compPago;
	private CargoDto cargo;
	private Double consumoProm;
	private long equiposActivos;
	private VerazNosisDto veraz;
	private VerazNosisDto nosis;
	private EstadoCreditBancoCentralDto estadoCredBC;
	private String otras;
	private String validFirma;
	private String depositoGarantia;
	private String anticipo;
	private boolean soloHibridos;
	private boolean findIMEI;
	private String comentarioAnalista;
	private String scoring;
	private boolean confirmada;
	
	public CaratulaDto(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public String getNroSS() {
		return nroSS;
	}

	public void setNroSS(String nroSS) {
		this.nroSS = nroSS;
	}

	public VendedorDto getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(VendedorDto usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public ValidacionDomicilioDto getValidDomicilio() {
		return validDomicilio;
	}

	public void setValidDomicilio(ValidacionDomicilioDto validDomicilio) {
		this.validDomicilio = validDomicilio;
	}

	public BancoDto getBanco() {
		return banco;
	}

	public void setBanco(BancoDto banco) {
		this.banco = banco;
	}

	public String getRefBancarias() {
		return refBancarias;
	}

	public void setRefBancarias(String refBancarias) {
		this.refBancarias = refBancarias;
	}

	public TipoCuentaBancariaDto getTipoCtaBancaria() {
		return tipoCtaBancaria;
	}

	public void setTipoCtaBancaria(TipoCuentaBancariaDto tipoCtaBancaria) {
		this.tipoCtaBancaria = tipoCtaBancaria;
	}

	public Double getMayorSaldoFavor() {
		return mayorSaldoFavor;
	}

	public void setMayorSaldoFavor(Double mayorSaldoFavor) {
		this.mayorSaldoFavor = mayorSaldoFavor;
	}

	public Double getIngPromedio() {
		return ingPromedio;
	}

	public void setIngPromedio(Double ingPromedio) {
		this.ingPromedio = ingPromedio;
	}

	public TipoTarjetaDto getTipoTarjCred() {
		return tipoTarjCred;
	}

	public void setTipoTarjCred(TipoTarjetaDto tipoTarjCred) {
		this.tipoTarjCred = tipoTarjCred;
	}

	public Double getLimiteTarjCred() {
		return limiteTarjCred;
	}

	public void setLimiteTarjCred(Double limiteTarjCred) {
		this.limiteTarjCred = limiteTarjCred;
	}

	public boolean isIngresoDemostrado() {
		return ingresoDemostrado;
	}

	public void setIngresoDemostrado(boolean ingresoDemostrado) {
		this.ingresoDemostrado = ingresoDemostrado;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getGanancia() {
		return ganancia;
	}

	public void setGanancia(String ganancia) {
		this.ganancia = ganancia;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getIngBrutos() {
		return ingBrutos;
	}

	public void setIngBrutos(String ingBrutos) {
		this.ingBrutos = ingBrutos;
	}

	public String getReciboHaberes() {
		return reciboHaberes;
	}

	public void setReciboHaberes(String reciboHaberes) {
		this.reciboHaberes = reciboHaberes;
	}

	public CalifCrediticiaDto getCalifCred() {
		return califCred;
	}

	public void setCalifCred(CalifCrediticiaDto califCred) {
		this.califCred = califCred;
	}

	public String getFactCelular() {
		return factCelular;
	}

	public void setFactCelular(String factCelular) {
		this.factCelular = factCelular;
	}

	public boolean isOkEECCAgente() {
		return okEECCAgente;
	}

	public void setOkEECCAgente(boolean okEECCAgente) {
		this.okEECCAgente = okEECCAgente;
	}

	public ComDto getCom() {
		return com;
	}

	public void setCom(ComDto com) {
		this.com = com;
	}

	public Date getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(Date antiguedad) {
		this.antiguedad = antiguedad;
	}

	public Double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public CalificacionDto getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(CalificacionDto calificacion) {
		this.calificacion = calificacion;
	}

	public RiskCodeDto getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(RiskCodeDto riskCode) {
		this.riskCode = riskCode;
	}

	public CompPagoDto getCompPago() {
		return compPago;
	}

	public void setCompPago(CompPagoDto compPago) {
		this.compPago = compPago;
	}

	public CargoDto getCargo() {
		return cargo;
	}

	public void setCargo(CargoDto cargo) {
		this.cargo = cargo;
	}

	public Double getConsumoProm() {
		return consumoProm;
	}

	public void setConsumoProm(Double consumoProm) {
		this.consumoProm = consumoProm;
	}

	public long getEquiposActivos() {
		return equiposActivos;
	}

	public void setEquiposActivos(long equiposActivos) {
		this.equiposActivos = equiposActivos;
	}

	public VerazNosisDto getVeraz() {
		return veraz;
	}

	public void setVeraz(VerazNosisDto veraz) {
		this.veraz = veraz;
	}

	public VerazNosisDto getNosis() {
		return nosis;
	}

	public void setNosis(VerazNosisDto nosis) {
		this.nosis = nosis;
	}

	public EstadoCreditBancoCentralDto getEstadoCredBC() {
		return estadoCredBC;
	}

	public void setEstadoCredBC(EstadoCreditBancoCentralDto estadoCredBC) {
		this.estadoCredBC = estadoCredBC;
	}

	public String getOtras() {
		return otras;
	}

	public void setOtras(String otras) {
		this.otras = otras;
	}

	public String getValidFirma() {
		return validFirma;
	}

	public void setValidFirma(String validFirma) {
		this.validFirma = validFirma;
	}

	public String getDepositoGarantia() {
		return depositoGarantia;
	}

	public void setDepositoGarantia(String depositoGarantia) {
		this.depositoGarantia = depositoGarantia;
	}

	public String getAnticipo() {
		return anticipo;
	}

	public void setAnticipo(String anticipo) {
		this.anticipo = anticipo;
	}

	public boolean isSoloHibridos() {
		return soloHibridos;
	}

	public void setSoloHibridos(boolean soloHibridos) {
		this.soloHibridos = soloHibridos;
	}

	public boolean isFindIMEI() {
		return findIMEI;
	}

	public void setFindIMEI(boolean findIMEI) {
		this.findIMEI = findIMEI;
	}

	public String getComentarioAnalista() {
		return comentarioAnalista;
	}

	public void setComentarioAnalista(String comentarioAnalista) {
		this.comentarioAnalista = comentarioAnalista;
	}

	public String getScoring() {
		return scoring;
	}

	public void setScoring(String scoring) {
		this.scoring = scoring;
	}

	public boolean isConfirmada() {
		return confirmada;
	}

	public void setConfirmada(boolean confirmada) {
		this.confirmada = confirmada;
	}
	
	public int compareTo(Object o) {
		CaratulaDto caratula1 = (CaratulaDto)o;
		
		if(this.fechaCreacion == null && caratula1.fechaCreacion == null){
			return 0;
		}
		if(caratula1.fechaCreacion == null){
			return -1;
		}
		if(this.fechaCreacion == null){
			return 1;
		}
		
		return this.fechaCreacion.compareTo(caratula1.fechaCreacion);
	}
}
