package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CuentaSSDto implements IsSerializable {

	private Long id;
	private Long idVantive;
	private String codigoVantive;
	private PersonaDto persona;
	private boolean empresa;
	private boolean transferido;
	
	private VendedorDto vendedor;
	
//	MGR - Mejoras Perfil Telemarketing. REQ#1. Cambia la definicion de prospect para Telemarketing
//	Traigo esta propiedades desde la cuenta mapeada a travez del dozer mapping
	private boolean prospect;
	private boolean cliente;
	private boolean prospectEnCarga;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdVantive() {
		return idVantive;
	}

	public void setIdVantive(Long idVantive) {
		this.idVantive = idVantive;
	}

	public String getCodigoVantive() {
		return codigoVantive;
	}

	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}

	public boolean isCliente() {
//		MGR - Mejoras Perfil Telemarketing. REQ#1. Cambia la definicion de prospect para Telemarketing
//		return getIdVantive() != null;
		return this.cliente;
	}

	public boolean isEmpresa() {
		return empresa;
	}

	public void setEmpresa(boolean empresa) {
		this.empresa = empresa;
	}

	public VendedorDto getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorDto vendedor) {
		this.vendedor = vendedor;
	}

	public boolean isTransferido() {
		return transferido;
	}

	public void setTransferido(boolean transferido) {
		this.transferido = transferido;
	}
	
	public boolean isProspect() {
		return this.prospect;
	}
	
	public void setProspect(boolean prospect) {
		this.prospect = prospect;
	}

	public boolean isProspectEnCarga() {
		return prospectEnCarga;
	}

	public void setProspectEnCarga(boolean prospectEnCarga) {
		this.prospectEnCarga = prospectEnCarga;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}
	
}
