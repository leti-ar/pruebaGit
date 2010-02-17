package ar.com.nextel.sfa.client.infocom;

import java.util.List;

import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.cuenta.CuentaClientService;
import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class InfocomUI extends ApplicationUI {

	private InfocomUIData infocomUIData;
	private FidelizacionInfocomUI fidelizacionInfocomUI;
	private CCInfocomUI ccInfocomUI;
	private FlexTable layout;
	private FlexTable estadoFlexTable;
	private FlexTable responsableFlexTable;
	private String idCuenta;
	private String responsablePago;
	private String razonSocial;
	private String idCliente;
	private String codigoVantive;
	private static InfocomUI instance;
	
	
	public static InfocomUI getInstance() {
		if (instance == null) {
			instance = new InfocomUI();
		}
		return instance;
	}
	

	private InfocomUI() {
		super();
		infocomUIData = new InfocomUIData();
	}
	
	public void firstLoad() {
		mainPanel.add(getPanelSuperior());
		fidelizacionInfocomUI = new FidelizacionInfocomUI(infocomUIData);
		mainPanel.add(fidelizacionInfocomUI.getFidelizacionTitledPanel());
		ccInfocomUI = new CCInfocomUI(infocomUIData);
		mainPanel.add(ccInfocomUI.getCCTitledPanel());	
	}
			
	public void reload(String idCuenta, String responsablePago, String codigoVantive) {
		this.idCuenta = idCuenta;
		this.responsablePago = responsablePago;
		if ("Todos".equals(responsablePago)) {
			invocarServiciosTodos(idCuenta, responsablePago, codigoVantive);
		} else {
			invocarServicios(idCuenta, responsablePago, codigoVantive);
		}
	}
	
	public boolean load() {
		String codigoVantive = null;
		String cuentaID = HistoryUtils.getParam("cuenta_id");
		if ("0".equals(cuentaID)) {
			codigoVantive = HistoryUtils.getParam("cod_vantive");
		}
		return loadInfocom(cuentaID, codigoVantive);
	}
	
	private boolean loadInfocom(String cuentaID, String codigoVantive) {
		razonSocial = null;
		idCliente = null;
		infocomUIData.setIdCuenta(cuentaID);
		infocomUIData.setCodigoVantive(codigoVantive);
		idCuenta = cuentaID;
		this.codigoVantive = codigoVantive;
		infocomUIData.getResponsablePago().clear();
		this.getInfocomData(idCuenta, idCuenta, codigoVantive);
		invocarServicios(idCuenta, idCuenta, codigoVantive);
		return true;
	}
	

	public void invocarServicios (String idCuenta, String responsablePago, String codigoVantive) {
		this.getDetalleCreditoFidelizacion(idCuenta, codigoVantive, true);	
		fidelizacionInfocomUI.getFidelizacionTitledPanel().setVisible(true);
		this.getCuentaCorriente(idCuenta, responsablePago, codigoVantive);
	}
	
	public void invocarServiciosTodos (String idCuenta, String responsablePago, String codigoVantive) {
		fidelizacionInfocomUI.getFidelizacionTitledPanel().setVisible(false);
		infocomUIData.getLimCredito().setText("");
	}
	
	public Widget getPanelSuperior() {
		layout = new FlexTable();
		layout.setWidth("98%");
		layout.getFlexCellFormatter().setColSpan(0, 3, 5);
		estadoFlexTable = new FlexTable();
		responsableFlexTable = new FlexTable();
		
		estadoFlexTable.setWidget(0, 0, infocomUIData.getEstadoEncabezadoLabel());
		estadoFlexTable.setWidget(0, 1, infocomUIData.getEstadoTerminales());
		estadoFlexTable.setWidget(0, 2, infocomUIData.getCicloPanel());
		estadoFlexTable.setWidget(0, 3, infocomUIData.getFlotaPanel());
		estadoFlexTable.setWidget(0, 6, IconFactory.scoring());
		estadoFlexTable.setWidget(0, 7, infocomUIData.getScoring());
		estadoFlexTable.setWidget(0, 8, infocomUIData.getLimCreditoPanel());
		estadoFlexTable.setWidth("60%");
		
		responsableFlexTable.setWidget(0, 0, infocomUIData.getResponsablePanel());
		responsableFlexTable.setWidget(0, 1, infocomUIData.getResumenPorEquipo());
		responsableFlexTable.setWidth("80%");
		
		layout.setWidget(0, 0, estadoFlexTable);
		layout.setWidget(1, 0, responsableFlexTable);
		
		return layout;
	}
	
	public boolean unload(String token) {
		return true;
	}
	
	private void getDetalleCreditoFidelizacion(String idCuenta, String codigoVantive, boolean firstTime) {
		InfocomRpcService.Util.getInstance().getDetalleCreditoFidelizacion(idCuenta, codigoVantive, new DefaultWaitCallback<CreditoFidelizacionDto>() {
			public void success(CreditoFidelizacionDto result) {
				if (result != null) {
					infocomUIData.setCreditoFidelizacion(result);
				}
			}
		});
	}	
	
	private void getInfocomData(String idCuenta, String responsablePago, String codigoVantive) {
		InfocomRpcService.Util.getInstance().getInfocomInitializer(idCuenta, codigoVantive, responsablePago, new DefaultWaitCallback<InfocomInitializer>() {
			public void success(InfocomInitializer result) {
				if (result != null) {
					infocomUIData.setInfocom(result);
					razonSocial = result.getRazonSocial();
					idCliente = result.getNumeroCuenta();
				}
			}
		});
	}
	
	private void getCuentaCorriente(String idCuenta, String responsablePago, String codigoVantive) {
		InfocomRpcService.Util.getInstance().getCuentaCorriente(idCuenta, codigoVantive, responsablePago, new DefaultWaitCallback<List<TransaccionCCDto>>() {
			public void success(List<TransaccionCCDto> result) {
				if (result != null) {
					infocomUIData.setCuentaCorriente(result);
				}
			}
		});
	}

	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}


	public String getRazonSocial() {
		return razonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}


	public String getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	
}
