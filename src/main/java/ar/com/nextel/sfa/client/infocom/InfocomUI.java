package ar.com.nextel.sfa.client.infocom;

import java.util.List;

import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.cuenta.CuentaClientService;
import ar.com.nextel.sfa.client.cuenta.CuentaEdicionTabPanel;
import ar.com.nextel.sfa.client.cuenta.CuentaInfocomForm;
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
	private static InfocomUI instance = new InfocomUI();
	
	
	public static InfocomUI getInstance() {
		if (instance == null) {
			instance = new InfocomUI();
		}
		return instance;
	}
	

	private InfocomUI() {
		super();
		infocomUIData = new InfocomUIData();
//		String cuentaID = HistoryUtils.getParam("cuenta_id");
//		infocomUIData.setIdCuenta(cuentaID);
//		idCuenta = cuentaID;
	}
	
	public void firstLoad() {
		mainPanel.add(getPanelSuperior());
		fidelizacionInfocomUI = new FidelizacionInfocomUI(infocomUIData);
		mainPanel.add(fidelizacionInfocomUI.getFidelizacionTitledPanel());
		ccInfocomUI = new CCInfocomUI(infocomUIData);
		mainPanel.add(ccInfocomUI.getCCTitledPanel());	
	}
			
	public void reload(String idCuenta, String responsablePago) {
		this.idCuenta = idCuenta;
		this.responsablePago = responsablePago;
		if ("Todos".equals(responsablePago)) {
			invocarServiciosTodos(idCuenta, responsablePago);
		} else {
			invocarServicios(idCuenta, responsablePago);
		}
	}
	
	public boolean load() {
		String cuentaID = HistoryUtils.getParam("cuenta_id");
		infocomUIData.setIdCuenta(cuentaID);
		idCuenta = cuentaID;
		infocomUIData.getResponsablePago().clear();
		this.getInfocomData(idCuenta, idCuenta);
		invocarServicios(idCuenta, idCuenta);
		return true;
	}
	
	public void invocarServicios (String idCuenta, String responsablePago) {
		//this.getInfocomData(idCuenta, responsablePago);
		this.getDetalleCreditoFidelizacion(idCuenta, true);	
		fidelizacionInfocomUI.getFidelizacionTitledPanel().setVisible(true);
		this.getCuentaCorriente(idCuenta, responsablePago);
	}
	
	public void invocarServiciosTodos (String idCuenta, String responsablePago) {
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
		estadoFlexTable.setWidget(0, 8, infocomUIData.getLimCreditoLabel());
		estadoFlexTable.setWidget(0, 9, infocomUIData.getLimCredito());
		//estadoFlexTable.setWidth("100%");
		
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
	
	private void getDetalleCreditoFidelizacion(String idCuenta, boolean firstTime) {
		InfocomRpcService.Util.getInstance().getDetalleCreditoFidelizacion(idCuenta, new DefaultWaitCallback<CreditoFidelizacionDto>() {
			public void success(CreditoFidelizacionDto result) {
				if (result != null) {
					infocomUIData.setCreditoFidelizacion(result);
				}
			}
		});
	}	
	
	private void getInfocomData(String idCuenta, String responsablePago) {
		InfocomRpcService.Util.getInstance().getInfocomInitializer(idCuenta, responsablePago, new DefaultWaitCallback<InfocomInitializer>() {
			public void success(InfocomInitializer result) {
				if (result != null) {
					infocomUIData.setInfocom(result);
				}
			}
		});
	}
	
	private void getCuentaCorriente(String idCuenta, String responsablePago) {
		InfocomRpcService.Util.getInstance().getCuentaCorriente(idCuenta, responsablePago, new DefaultWaitCallback<List<TransaccionCCDto>>() {
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

}
