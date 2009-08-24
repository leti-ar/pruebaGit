package ar.com.nextel.sfa.client.infocom;

import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DetalleFidelizacionDto;
import ar.com.nextel.sfa.client.dto.EquiposServiciosDto;
import ar.com.nextel.sfa.client.dto.ResumenEquipoDto;
import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class InfocomUIData extends UIData {

	private EstadoTerminales estadoTerminales;
	private InlineHTML ciclo;
	private Label cicloLabel;
	private InlineHTML flota;
	private Label flotaLabel;
	private InlineHTML limCredito;
	private Label limCreditoLabel;
	private ListBox responsablePago;
	private Label montoLabel;
	private Label vencimientoLabel;
	
	private Label numResponsable;
	
	private HorizontalPanel cicloPanel;
	private HorizontalPanel flotaPanel;
	private HorizontalPanel limCreditoPanel;
	private HorizontalPanel responsablePanel;
	
	private InlineHTML monto;
	private Label estadoLabel;
	private InlineHTML vencimiento;
	private InlineHTML estado;
	private Label estadoEncabezadoLabel;
	
	private FlexTable creditoFidelizacionTable;
	private FlexTable cuentaCorrienteTable;
	private FlexTable descripcionTable;
	
	private SimpleLink scoring; 
	private SimpleLink resumenPorEquipo;
	
	private HorizontalPanel montoPanel;
	private HorizontalPanel estadoPanel;
	private HorizontalPanel vencimientoPanel;
	
	private InfocomUI infocomUI;
	
	private final NumberFormat numberFormat = NumberFormat.getCurrencyFormat();
	private final DateTimeFormat dateFormat = DateTimeFormat.getMediumDateFormat();
	
	private String idCuenta;
	private InfocomInitializer infocom;
	
	
	public InfocomUIData() {
		montoPanel = new HorizontalPanel();
		estadoPanel = new HorizontalPanel();
		vencimientoPanel = new HorizontalPanel();
		responsablePanel = new HorizontalPanel();
		cicloPanel = new HorizontalPanel();
		cicloPanel.setWidth("200px");
		flotaPanel = new HorizontalPanel();
		flotaPanel.setWidth("70px");
		estadoTerminales = new EstadoTerminales();
		cicloLabel = new Label("Ciclo: ");
		ciclo = new InlineHTML();
		cicloPanel.add(cicloLabel);
		cicloPanel.add(ciclo);
		flotaLabel = new Label("Flota: ");
		flota = new InlineHTML();
		flotaPanel.add(flotaLabel);
		flotaPanel.add(flota);
		limCreditoPanel = new HorizontalPanel();
		limCreditoPanel.setWidth("150px");
		limCreditoLabel = new Label("Lím. Crédito: ");
		limCredito = new InlineHTML();
		limCreditoPanel.add(limCreditoLabel);
		limCreditoPanel.add(limCredito);
		responsablePago = new ListBox("Todos");
		responsablePago.setWidth("120px");
		
		responsablePago.addChangeListener(new ChangeListener() {
			public void onChange (Widget arg0) {
				if (responsablePago.getSelectedIndex()==0) {
					InfocomUI.getInstance().reload(idCuenta, "Todos");
				} else {
					limCredito.setText(numberFormat.format(infocom.getLimiteCredito()));
					InfocomUI.getInstance().reload(idCuenta, responsablePago.getSelectedItemText());
				}
			}
		});		
		
		montoLabel = new Label("Monto: ");
		monto = new InlineHTML();
		montoPanel.add(montoLabel);
		montoPanel.add(monto);
		estadoLabel = new Label("Estado: ");
		estado = new InlineHTML();
		estadoPanel.add(estadoLabel);
		estadoPanel.add(estado);
		
		vencimientoLabel = new Label("Vencimiento: ");
		vencimiento = new InlineHTML();
		vencimientoPanel.add(vencimientoLabel);
		vencimientoPanel.add(vencimiento);
		numResponsable = new Label("N° Responsable Pago:");
		responsablePanel.add(numResponsable);
		responsablePanel.add(responsablePago);
		scoring = new SimpleLink("Scoring");
		scoring.addStyleName("infocomSimpleLink");
		scoring.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				getScoring(Long.valueOf(idCuenta));
			}
		});
	
		estadoEncabezadoLabel = new Label("Estado");

		resumenPorEquipo = new SimpleLink("Resumen por equipo");
		resumenPorEquipo.addStyleName("infocomSimpleLink");
		resumenPorEquipo.addClickListener(new ClickListener() {
			public void onClick (Widget arg0) {
				getResumenPorEquipo(idCuenta, responsablePago.getSelectedItemText());
			}
		});
	}	
	
	public HorizontalPanel getEstadoPanel() {
		return estadoPanel;
	}

	public void setEstadoPanel(HorizontalPanel estadoPanel) {
		this.estadoPanel = estadoPanel;
	}

	public HorizontalPanel getVencimientoPanel() {
		return vencimientoPanel;
	}

	public void setVencimientoPanel(HorizontalPanel vencimientoPanel) {
		this.vencimientoPanel = vencimientoPanel;
	}

	/**Obtiene la información del scoring*/
	private void getScoring(Long numeroCuenta) {
		InfocomRpcService.Util.getInstance().consultarScoring(numeroCuenta, new DefaultWaitCallback<ScoringDto>() {
			public void success(ScoringDto result) {
				if (result != null) {
					ScoringPopUpUI scoringPopUpUI = new ScoringPopUpUI("Cuentas - Scoring", result);
					scoringPopUpUI.showAndCenter();					
				}
			}
		});
	}
	
	/**Obtiene la información de Resumen Por Equipo*/
	private void getResumenPorEquipo(String numeroCuenta, final String numeroResponsablePago) {
		InfocomRpcService.Util.getInstance().getResumenEquipos(numeroCuenta, numeroResponsablePago, new DefaultWaitCallback<ResumenEquipoDto>() {			
			public void success(ResumenEquipoDto result) {
				if (result != null) {
					ResumenEquipoPopUp resumenEquipoPopUp = new ResumenEquipoPopUp("Cuentas - Resumen Por Equipos", "750", numeroResponsablePago);
					resumenEquipoPopUp.setResumenPorEquipo(result);
					resumenEquipoPopUp.showAndCenter();
				}
			}
		});
	}	
	
	/**Inicializa la tabla de Fidelización*/
	public FlexTable initTableCreditoFidelizacion() {
		creditoFidelizacionTable = new FlexTable();
		String[] widths = { "149px", "489px", "149px", "149px", };
		String[] titles = { "Fecha", "Descripcion", "Monto", "Factura aplicada" };
		for (int col = 0; col < widths.length; col++) {
			creditoFidelizacionTable.setHTML(0, col, titles[col]);
			creditoFidelizacionTable.getColumnFormatter().setWidth(col, widths[col]);
		}
		creditoFidelizacionTable.getColumnFormatter().addStyleName(0, "alignCenter");
		creditoFidelizacionTable.getColumnFormatter().addStyleName(1, "alignCenter");
		creditoFidelizacionTable.getColumnFormatter().addStyleName(2, "alignCenter");
		creditoFidelizacionTable.setCellPadding(0);
		creditoFidelizacionTable.setCellSpacing(0);
		creditoFidelizacionTable.addStyleName("gwt-BuscarCuentaResultTable");
		creditoFidelizacionTable.getRowFormatter().addStyleName(0, "header");
		return creditoFidelizacionTable;
	}
	
	/**Inicializa la tabla de Cuenta Corriente*/
	public FlexTable initCCTable() {
		cuentaCorrienteTable = new FlexTable();
		String[] widths = { "150px", "80px", "110px", "150px","160px", "140px", "140px" };
		String[] titles = { Sfa.constant().numeroCuenta(), Sfa.constant().clase(), Sfa.constant().venc(), Sfa.constant().descripcion(), 
				Sfa.constant().numeroComprobante(), Sfa.constant().importe(), Sfa.constant().saldo() };
		for (int col = 0; col < widths.length; col++) {
			cuentaCorrienteTable.setHTML(0, col, titles[col]);
			cuentaCorrienteTable.getColumnFormatter().setWidth(col, widths[col]);
			cuentaCorrienteTable.getColumnFormatter().addStyleName(col, "alignCenter");
		}
		cuentaCorrienteTable.setCellPadding(0);
		cuentaCorrienteTable.setCellSpacing(0);
		cuentaCorrienteTable.addStyleName("gwt-BuscarCuentaResultTable");
		//cuentaCorrienteTable.addStyleName("dataTable");
		cuentaCorrienteTable.getRowFormatter().addStyleName(0, "header");
		return cuentaCorrienteTable;
	}

	/**Inicializa la tabla de Descripción*/
	public FlexTable initDescripcionTable() {
		descripcionTable = new FlexTable();
		this.descripcionTable = descripcionTable;
		String[] widths = { "640px", "149px", "149px" };
		String[] titles = { "Descripcion", "A vencer", "Vencida" };
		for (int col = 0; col < widths.length; col++) {
			descripcionTable.setHTML(0, col, titles[col]);
			descripcionTable.getColumnFormatter().setWidth(col, widths[col]);
			descripcionTable.getColumnFormatter().addStyleName(col, "alingLeft");
		}
		
		descripcionTable.setHTML(1, 0, "EQUIPOS");		
		descripcionTable.setHTML(2, 0, "SERVICIOS");
		descripcionTable.setHTML(3, 0, "Total");
		descripcionTable.setCellPadding(0);
		descripcionTable.setCellSpacing(0);
		//descripcionTable.addStyleName("infocomDescripcionTable");
		descripcionTable.addStyleName("dataTableInfocom");
		descripcionTable.getRowFormatter().addStyleName(0, "header");
		return descripcionTable;
	}
	
	/** Carga los datos de Header de infocom */
	public void setInfocom(InfocomInitializer infocom) {
		this.infocom = infocom;  
		estadoTerminales.setEstado(Integer.parseInt(infocom.getTerminalesActivas()), Integer.parseInt(infocom.getTerminalesSuspendidas()), Integer.parseInt(infocom.getTerminalesDesactivadas()));
		ciclo.setText(infocom.getCiclo());
		flota.setText(infocom.getFlota());
		limCredito.setText(numberFormat.format(infocom.getLimiteCredito()));
		setDescripcion(infocom.getEquiposServicios());
		
		for (Iterator iterator = (infocom.getResponsablePago()).iterator(); iterator.hasNext();) {
			CuentaDto cuentaDto = (CuentaDto) iterator.next();
			responsablePago.addItem(cuentaDto.getCodigoVantive());
		}
		
		responsablePago.setSelectedIndex(1);
	}

	/** Carga el panel de Credito de Fidelizacion de infocom */
	public void setCreditoFidelizacion(CreditoFidelizacionDto creditoFidelizacionDto) {
		monto.setText(numberFormat.format(creditoFidelizacionDto.getMonto()));
		estado.setText(creditoFidelizacionDto.getEstado());
		if (creditoFidelizacionDto.getVencimiento() != null) {
			vencimiento.setText(dateFormat.format(creditoFidelizacionDto.getVencimiento()));
		} else {
			vencimiento.setText("");
		}
		refreshCreditoFidelizacionTable(creditoFidelizacionDto.getDetalles());
	}

	/** Carga el panel de Cuenta Corriente de infocom */
	public void setCuentaCorriente(List<TransaccionCCDto> transacciones) {
		refreshCuentaCorrienteTable(transacciones);
	}
	
	/** Carga la tabla de Descripcion de infocom */
	public void setDescripcion(EquiposServiciosDto equipos) {
		refreshDescripcionTable(equipos);
	}

	private void refreshCreditoFidelizacionTable(List<DetalleFidelizacionDto> detallesFidelizacion) {
		int row = 1;
		DateTimeFormat dateFormatter = DateTimeFormat.getMediumDateFormat();
		for (Iterator iterator = detallesFidelizacion.iterator(); iterator.hasNext();) {
			DetalleFidelizacionDto detalle = (DetalleFidelizacionDto) iterator.next();
			creditoFidelizacionTable.setHTML(row, 0, dateFormatter.format(detalle.getFecha()));
			creditoFidelizacionTable.setHTML(row, 1, detalle.getDescripcion());
			creditoFidelizacionTable.setHTML(row, 2, detalle.getMonto());
			creditoFidelizacionTable.setHTML(row, 3, detalle.getFactura());
		}
	}

	private void refreshCuentaCorrienteTable(List<TransaccionCCDto> transacciones) {
		int row = 1;
		DateTimeFormat dateFormatter = DateTimeFormat.getMediumDateFormat();
		for (Iterator iterator = transacciones.iterator(); iterator.hasNext();) {
			TransaccionCCDto transaccion = (TransaccionCCDto) iterator.next();
			//cuentaCorriente.setHTML(row, 1, transaccion.getNumero());
			cuentaCorrienteTable.setHTML(row, 1, transaccion.getClase());
			//ver si no hay que formatear la fecha
			cuentaCorrienteTable.setHTML(row, 2, transaccion.getFechaVenc());
			cuentaCorrienteTable.setHTML(row, 3, transaccion.getDescripcion());
			cuentaCorrienteTable.setHTML(row, 4, transaccion.getNumero());
			cuentaCorrienteTable.setHTML(row, 5, transaccion.getImporte());
			cuentaCorrienteTable.setHTML(row, 6, transaccion.getSaldo());
			row++;
		}
	}
	
	private void refreshDescripcionTable(EquiposServiciosDto equipos) {
		descripcionTable.setHTML(1, 1, numberFormat.format(equipos.getDeudaEquiposAVencer()));
		descripcionTable.setHTML(1, 2, numberFormat.format(equipos.getDeudaEquiposVencida()));
		descripcionTable.setHTML(2, 1, numberFormat.format(equipos.getDeudaServiciosAVencer()));
		descripcionTable.setHTML(2, 2, numberFormat.format(equipos.getDeudaServiciosVencida()));
		descripcionTable.setHTML(3, 1, numberFormat.format(equipos.getDeudaEquiposAVencer() + equipos.getDeudaServiciosAVencer()));
		descripcionTable.setHTML(3, 2, numberFormat.format(equipos.getDeudaEquiposVencida() + equipos.getDeudaServiciosVencida()));
	}


	public EstadoTerminales getEstadoTerminales() {
		return estadoTerminales;
	}


	public void setEstadoTerminales(EstadoTerminales estadoTerminales) {
		this.estadoTerminales = estadoTerminales;
	}


	public InlineHTML getCiclo() {
		return ciclo;
	}


	public void setCiclo(InlineHTML ciclo) {
		this.ciclo = ciclo;
	}


	public Label getCicloLabel() {
		return cicloLabel;
	}


	public void setCicloLabel(Label cicloLabel) {
		this.cicloLabel = cicloLabel;
	}


	public InlineHTML getFlota() {
		return flota;
	}


	public void setFlota(InlineHTML flota) {
		this.flota = flota;
	}


	public Label getFlotaLabel() {
		return flotaLabel;
	}


	public void setFlotaLabel(Label flotaLabel) {
		this.flotaLabel = flotaLabel;
	}


	public InlineHTML getLimCredito() {
		return limCredito;
	}


	public void setLimCredito(InlineHTML limCredito) {
		this.limCredito = limCredito;
	}


	public Label getLimCreditoLabel() {
		return limCreditoLabel;
	}


	public void setLimCreditoLabel(Label limCreditoLabel) {
		this.limCreditoLabel = limCreditoLabel;
	}


	public ListBox getResponsablePago() {
		return responsablePago;
	}


	public void setResponsablePago(ListBox responsablePago) {
		this.responsablePago = responsablePago;
	}


	public Label getNumResponsable() {
		return numResponsable;
	}


	public void setNumResponsable(InlineHTML numResponsable) {
		this.numResponsable = numResponsable;
	}


	public InlineHTML getMonto() {
		return monto;
	}


	public void setMonto(InlineHTML monto) {
		this.monto = monto;
	}

	public Label getEstadoLabel() {
		return estadoLabel;
	}


	public void setEstadoLabel(Label estadoLabel) {
		this.estadoLabel = estadoLabel;
	}


	public InlineHTML getVencimiento() {
		return vencimiento;
	}


	public void setVencimiento(InlineHTML vencimiento) {
		this.vencimiento = vencimiento;
	}

	public SimpleLink getScoring() {
		return scoring;
	}


	public void setScoring(SimpleLink scoring) {
		this.scoring = scoring;
	}


	public SimpleLink getResumenPorEquipo() {
		return resumenPorEquipo;
	}


	public void setResumenPorEquipo(SimpleLink resumenPorEquipo) {
		this.resumenPorEquipo = resumenPorEquipo;
	}


	public Label getMontoLabel() {
		return montoLabel;
	}


	public void setMontoLabel(Label montoLabel) {
		this.montoLabel = montoLabel;
	}


	public Label getVencimientoLabel() {
		return vencimientoLabel;
	}


	public void setVencimientoLabel(Label vencimientoLabel) {
		this.vencimientoLabel = vencimientoLabel;
	}


	public InlineHTML getEstado() {
		return estado;
	}


	public void setEstado(InlineHTML estado) {
		this.estado = estado;
	}


	public Label getEstadoEncabezadoLabel() {
		return estadoEncabezadoLabel;
	}


	public void setEstadoEncabezadoLabel(Label estadoEncabezadoLabel) {
		this.estadoEncabezadoLabel = estadoEncabezadoLabel;
	}



	public void setMontoPanel(HorizontalPanel montoPanel) {
		this.montoPanel = montoPanel;
	}


	public HorizontalPanel getMontoPanel() {
		return montoPanel;
	}


	public HorizontalPanel getCicloPanel() {
		return cicloPanel;
	}


	public void setCicloPanel(HorizontalPanel cicloPanel) {
		this.cicloPanel = cicloPanel;
	}


	public HorizontalPanel getFlotaPanel() {
		return flotaPanel;
	}

	public void setFlotaPanel(HorizontalPanel flotaPanel) {
		this.flotaPanel = flotaPanel;
	}

	public HorizontalPanel getResponsablePanel() {
		return responsablePanel;
	}

	public void setResponsablePanel(HorizontalPanel responsablePanel) {
		this.responsablePanel = responsablePanel;
	}

	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}

	public FlexTable getCreditoFidelizacionTable() {
		return creditoFidelizacionTable;
	}

	public void setCreditoFidelizacionTable(FlexTable creditoFidelizacionTable) {
		this.creditoFidelizacionTable = creditoFidelizacionTable;
	}

	public HorizontalPanel getLimCreditoPanel() {
		return limCreditoPanel;
	}

	public void setLimCreditoPanel(HorizontalPanel limCreditoPanel) {
		this.limCreditoPanel = limCreditoPanel;
	}	
	
}
