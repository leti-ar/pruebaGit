package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
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
	private ScoringInfocomUI scoringInfocomUI;
	private Long idCuenta; 
	

	public InfocomUI() {
		super();
	}
	
	public Widget getPanelSuperior() {
		infocomUIData = new InfocomUIData();
		layout = new FlexTable();
//		layout.addStyleName("layout");
		layout.setWidth("98%");
		layout.getFlexCellFormatter().setColSpan(0, 3, 5);
		estadoFlexTable = new FlexTable();
		responsableFlexTable = new FlexTable();
		
		estadoFlexTable.setWidget(0, 0, infocomUIData.getEstado());
		estadoFlexTable.setWidget(0, 1, infocomUIData.getEstadoTerminales());
		estadoFlexTable.setWidget(0, 2, infocomUIData.getCicloLabel());
		estadoFlexTable.setWidget(0, 3, infocomUIData.getCiclo());
		estadoFlexTable.setWidget(0, 4, infocomUIData.getFlotaLabel());
		estadoFlexTable.setWidget(0, 5, infocomUIData.getFlota());
		estadoFlexTable.setWidget(0, 6, IconFactory.scoring());
		estadoFlexTable.setWidget(0, 7, infocomUIData.getScoring());
		estadoFlexTable.setWidget(0, 8, infocomUIData.getLimCreditoLabel());
		estadoFlexTable.setWidget(0, 9, infocomUIData.getLimCredito());
		
		responsableFlexTable.setWidget(0, 0, infocomUIData.getNumResponsable());
		responsableFlexTable.setWidget(0, 1, infocomUIData.getResponsablePago());
		responsableFlexTable.setWidget(0, 2, infocomUIData.getResumenPorEquipo());
		
		layout.setWidget(0, 0, estadoFlexTable);
		layout.setWidget(1, 0, responsableFlexTable);
		
		return layout;
	}

	public void firstLoad() {
		mainPanel.add(getPanelSuperior());
		fidelizacionInfocomUI = new FidelizacionInfocomUI(infocomUIData);
		mainPanel.add(fidelizacionInfocomUI.getFidelizacionTitledPanel());
		CCInfocomUI ccInfocomUI = new CCInfocomUI(infocomUIData);
		mainPanel.add(ccInfocomUI.getCCTitledPanel());
		//Borrar el hardcode
		String idCuenta="6.356172";
		this.getInfocomData(idCuenta);
		this.getDetalleCreditoFidelizacion(idCuenta, true);
		//this.getCuentaCorriente()
	}
	
	public boolean load() {
		return true;
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
	
	
	private void getInfocomData(String numeroCuenta) {
		InfocomRpcService.Util.getInstance().getInfocomInitializer(numeroCuenta, new DefaultWaitCallback<InfocomInitializer>() {
			public void success(InfocomInitializer result) {
				if (result != null) {
					infocomUIData.setInfocom(result);
				}
			}
		});
	}

}
