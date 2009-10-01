package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioGeneracionDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CerrarSSUI extends NextelDialog implements ClickListener {

	private CerrarSSUIData generarSSData;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
	private Command aceptarCommand;
	private static final String generarTitle = "SS - Generar SS";
	private static final String cerrarTitle = "SS - Cerrar SS";
	private FlexTable layout;

	public CerrarSSUI() {
		super("SS - Generar SS", false, true);
		init();
	}

	private void init() {
		setWidth("450px");
		aceptar = new SimpleLink(Sfa.constant().aceptar());
		aceptar.addClickListener(this);
		cancelar = new SimpleLink(Sfa.constant().cancelar());
		cancelar.addClickListener(this);
		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFooterVisible(false);
		setFormButtonsVisible(true);
		generarSSData = new CerrarSSUIData();
		layout = new FlexTable();
		layout.setWidth("100%");
		// primeraTabla.getFlexCellFormatter().setColSpan(1, 1, 2);
		Label emailLabel = new Label(Sfa.constant().emailPanelTitle());
		emailLabel.addStyleName("req");
		add(emailLabel);
		layout.addStyleName("layout");
		layout.setWidget(0, 0, generarSSData.getLaboral());
		layout.setHTML(0, 1, Sfa.constant().laboral());
		layout.setWidget(0, 3, generarSSData.getEmailLaboral());
		layout.setWidget(1, 0, generarSSData.getPersonal());
		layout.setHTML(1, 1, Sfa.constant().personal());
		layout.setWidget(1, 3, generarSSData.getEmailPersonal());
		layout.setWidget(2, 0, generarSSData.getNuevo());
		layout.setHTML(2, 1, Sfa.constant().nuevo());
		layout.setHTML(2, 2, Sfa.constant().emailReq());
		layout.setWidget(2, 3, generarSSData.getEmail());
		layout.setHTML(3, 1, Sfa.constant().scoringTitle());
		layout.setWidget(3, 0, generarSSData.getScoring());
		add(layout);
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			if (true && aceptarCommand != null) {
				aceptarCommand.execute();
				hide();
			}
		} else if (sender == cancelar) {
			hide();
		}
	}

	public void setAceptarCommand(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}

	public void show(PersonaDto persona, SolicitudServicioGeneracionDto solicitudServicioGeneracion,
			boolean cdw) {
		generarSSData.setEmails(persona.getEmails(), solicitudServicioGeneracion);
		boolean permisoCierreScoring = ClientContext.getInstance().checkPermiso(
				PermisosEnum.SCORING_CHECKED.getValue());
		if (cdw || !permisoCierreScoring) {
			layout.setHTML(3, 1, "");
			layout.setHTML(3, 0, "");
		} else {
			layout.setHTML(3, 1, Sfa.constant().scoringTitle());
			layout.setWidget(3, 0, generarSSData.getScoring());
		}
		showAndCenter();
	}

	public CerrarSSUIData getGenerarSSUIData() {
		return generarSSData;
	}

	public void setTitleCerrar(boolean cerrando) {
		if (cerrando) {
			setDialogTitle(cerrarTitle);
		} else {
			setDialogTitle(generarTitle);
		}
	}
}
