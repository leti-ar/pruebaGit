package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.cuenta.CuentaClientService;
import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.ss.EditarSSUIController;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;

public class RazonSocialClienteBar extends Composite {

	private FlowPanel left;
	private InlineHTML razonSocial;
	private InlineLabel consultarScoring;
	private InlineHTML cliente;
	private HTML cuentaLink;
	private HTML scoringIcon;
	private Long idCuenta;
	private String codigoVantive;
	private SimpleLink aceptar;
	private MessageDialog dialog;
	private EditarSSUIController controllerSSUI;

	public RazonSocialClienteBar() {
		left = new FlowPanel();
		initWidget(left);
		Grid right = new Grid(1, 3);
		left.add(right);
		addStyleName("gwt-RazonSocialClienteBar");
		right.addStyleName("right");
		left.add(new InlineLabel(Sfa.constant().razonSocial() + ": "));
		left.add(razonSocial = new InlineHTML());
		cuentaLink = IconFactory.silvioSoldan();
		cuentaLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				Long idCuenta = null;
				if (HistoryUtils.getParam("idCuenta") != null) {
					idCuenta = Long.parseLong(HistoryUtils.getParam("idCuenta"));
				} else if (HistoryUtils.getParam("cuenta_id") != null) {
					idCuenta = Long.parseLong(HistoryUtils.getParam("cuenta_id"));
				}
				CuentaClientService.cargarDatosCuenta(idCuenta, codigoVantive);
			}
		});
		right.setWidget(0, 0, cuentaLink);
		right.setWidget(0, 1, new InlineLabel(Sfa.constant().cliente() + ": "));
		right.setWidget(0, 2, cliente = new InlineHTML());
		right.getCellFormatter().setWidth(0, 2, "180px");
	}
	
	//GB
	public RazonSocialClienteBar(EditarSSUIController controller) {
		controllerSSUI = controller;
		left = new FlowPanel();
		initWidget(left);
		Grid right = new Grid(1, 5);
		left.add(right);
		addStyleName("gwt-RazonSocialClienteBar");
		right.addStyleName("right");
		
		consultarScoring = new InlineLabel(Sfa.constant().scoringTitle() + ": ");
		consultarScoring.setStyleName("gwt-ConsultarScoringBar");
		
		scoringIcon = IconFactory.consultarScoring();
		scoringIcon.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				
				InfocomRpcService.Util.getInstance().consultarScoring(
						controllerSSUI.getEditarSSUIData().getCuentaId(), controllerSSUI.getEditarSSUIData().getCuenta().getCodigoVantive(), new DefaultWaitCallback<ScoringDto>() {
							public void success(ScoringDto result) {
								if (result != null) {
									dialog = new MessageDialog(Sfa.constant().scoringTitle().toUpperCase(),false,false);
									dialog.setWidth("350px");
									dialog.showAceptar(result.getMensajeAdicional(), new Command() {	public void execute() {	dialog.hide();}});//MessageDialog.getCloseCommand());
								}
							}
				});
			}
		});
		
		left.add(new InlineLabel(Sfa.constant().razonSocial() + ": "));
		left.add(razonSocial = new InlineHTML());
		cuentaLink = IconFactory.silvioSoldan();
		cuentaLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				Long idCuenta = null;
				if (HistoryUtils.getParam("idCuenta") != null) {
					idCuenta = Long.parseLong(HistoryUtils.getParam("idCuenta"));
				} else if (HistoryUtils.getParam("cuenta_id") != null) {
					idCuenta = Long.parseLong(HistoryUtils.getParam("cuenta_id"));
				}
				CuentaClientService.cargarDatosCuenta(idCuenta, codigoVantive);
			}
		});
		right.setWidget(0, 0, consultarScoring);
		right.setWidget(0, 1, scoringIcon);
		right.getCellFormatter().setWidth(0, 1, "60px");
		right.setWidget(0, 2, cuentaLink);
		right.setWidget(0, 3, new InlineLabel(Sfa.constant().cliente() + ": "));
		right.setWidget(0, 4, cliente = new InlineHTML());
		right.getCellFormatter().setWidth(0, 4, "180px");
	}

	public void setDisabledSilvioSoldan() {
		cuentaLink.setVisible(false);
	}

	public void setEnabledSilvioSoldan() {
		cuentaLink.setVisible(true);
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial.setText(razonSocial);
	}

	public void setCliente(String cliente) {
		this.cliente.setText(cliente);
	}

	public void setIdCuenta(Long idCuenta, String codigoVantive) {
		this.idCuenta = idCuenta;
		this.codigoVantive = codigoVantive;
	}

	public void showConsultarScoring(){
		consultarScoring.setVisible(true);
		scoringIcon.setVisible(true);
	}
	
	public void hideConsultarScoring(){
		consultarScoring.setVisible(false);
		scoringIcon.setVisible(false);
	}
}