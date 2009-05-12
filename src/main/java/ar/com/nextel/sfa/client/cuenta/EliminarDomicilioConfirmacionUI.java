package ar.com.nextel.sfa.client.cuenta;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

/**
 * @author esalvador 
 **/
public class EliminarDomicilioConfirmacionUI extends NextelDialog{

	private Command comandoAceptar;
	private FormButtonsBar footerBar;
	private SimpleLink linkCerrar;
	private SimpleLink linkAceptar;
	private Label texto;
	
	public EliminarDomicilioConfirmacionUI() {
		super("Eliminar Domicilio");
		init();
	}

	private void init(){
		footerBar = new FormButtonsBar();
		linkCerrar = new SimpleLink("Cerrar");
		linkAceptar = new SimpleLink("Aceptar");
		texto = new Label("¿Está seguro que desea eliminar el domicilio?");
		setWidth("300px");
		setHeight("100px");
		addStyleName("layout");
		add(texto);
		linkCerrar.setStyleName("link");
		linkAceptar.setStyleName("link");
		footerBar.addLink(linkAceptar);
		footerBar.addLink(linkCerrar);
		footerBar.addStyleName("layout");
		mainPanel.add(footerBar);
		footer.setVisible(false);
		linkAceptar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				// TODO: Logica de Aceptar y guardar nuevo Domicilio!
				if (comandoAceptar != null){
					comandoAceptar.execute();
				}
			}
		});
		linkCerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				hide();
			}
		});
		this.showAndCenter();
	}

	public Command getComandoAceptar() {
		return comandoAceptar;
	}

	public void setComandoAceptar(Command comandoAceptar) {
		this.comandoAceptar = comandoAceptar;
	}
}
