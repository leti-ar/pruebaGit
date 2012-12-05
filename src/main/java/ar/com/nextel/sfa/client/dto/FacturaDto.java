package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Factura
 * @author mrotger
 *
 */
public class FacturaDto implements IsSerializable{

	private Long id;
	
	private String numero;
	
	private Long comprobante;
	
	private Boolean pagado;
	
	private Date fecha;
	
	private Long financialsId;
	
	private SolicitudServicioDto solicitudServicioDTO;
	
	private List<ItemFacturaDto> items = new ArrayList<ItemFacturaDto>();
	
	public FacturaDto(String numero, Boolean pagado) {
		this.numero = numero;
		this.pagado = pagado;
	}
	
	public FacturaDto() {
	}

	public String getNumero() {
		return numero;
	}
	
	public Boolean getPagado() {
		return pagado;
	}


	public List<ItemFacturaDto> getItems() {
		return items;
	}

	public void setItems(List<ItemFacturaDto> items) {
		this.items = items;
	}
	
	public void addItemFacturaDTO(ItemFacturaDto item){
		getItems().add(item);
	}

	public Long getId() {
		return id;
	}

	public Long getComprobante() {
		return comprobante;
	}

	public Date getFecha() {
		return fecha;
	}

	public Long getFinancialsId() {
		return financialsId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComprobante(Long comprobante) {
		this.comprobante = comprobante;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setFinancialsId(Long financialsId) {
		this.financialsId = financialsId;
	}

	public SolicitudServicioDto getSolicitudServicioDTO() {
		return solicitudServicioDTO;
	}

	public void setSolicitudServicioDTO(SolicitudServicioDto solicitudServicioDTO) {
		this.solicitudServicioDTO = solicitudServicioDTO;
	}
}
