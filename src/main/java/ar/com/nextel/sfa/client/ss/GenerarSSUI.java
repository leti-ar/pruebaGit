package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioGeneracionDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class GenerarSSUI extends NextelDialog implements ClickListener {

	private GenerarSSUIData generarSSData;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
	private Command aceptarCommand;
	private static final String generarTitle = "SS - Generar SS";
	private static final String cerrarTitle = "SS - Cerrar SS";

	public GenerarSSUI() {
		super("SS - Generar SS");
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
		generarSSData = new GenerarSSUIData();
		FlexTable primeraTabla = new FlexTable();
		primeraTabla.setWidth("100%");
		// primeraTabla.getFlexCellFormatter().setColSpan(1, 1, 2);
		Label emailLabel = new Label(Sfa.constant().emailPanelTitle());
		emailLabel.addStyleName("req");
		add(emailLabel);
		primeraTabla.addStyleName("layout");
		primeraTabla.setWidget(0, 0, generarSSData.getLaboral());
		primeraTabla.setHTML(0, 1, Sfa.constant().laboral());
		primeraTabla.setWidget(0, 3, generarSSData.getEmailLaboral());
		primeraTabla.setWidget(1, 0, generarSSData.getPersonal());
		primeraTabla.setHTML(1, 1, Sfa.constant().personal());
		primeraTabla.setWidget(1, 3, generarSSData.getEmailPersonal());
		primeraTabla.setWidget(2, 0, generarSSData.getNuevo());
		primeraTabla.setHTML(2, 1, Sfa.constant().nuevo());
		primeraTabla.setHTML(2, 2, Sfa.constant().emailReq());
		primeraTabla.setWidget(2, 3, generarSSData.getEmail());
		primeraTabla.setHTML(3, 1, Sfa.constant().scoringTitle());
		primeraTabla.setWidget(3, 0, generarSSData.getScoring());
		add(primeraTabla);
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

	public void show(PersonaDto persona, SolicitudServicioGeneracionDto solicitudServicioGeneracion) {
		generarSSData.setEmails(persona.getEmails(), solicitudServicioGeneracion);
		showAndCenter();
	}

	public GenerarSSUIData getGenerarSSUIData() {
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
