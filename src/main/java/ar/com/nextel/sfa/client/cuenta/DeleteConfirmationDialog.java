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
public class DeleteConfirmationDialog extends NextelDialog{

	private Command comandoAceptar;
	private FormButtonsBar footerBar;
	private SimpleLink linkCerrar;
	private SimpleLink linkAceptar;
	private Label texto;
	private String textoAConsultar;
	private String titulo;
	
	public final static String ELIMINA_DOMICILIO = "¿Esta seguro que desea eliminar el domicilio seleccionado?";

	public DeleteConfirmationDialog(String titulo) {
		super(titulo);
		this.titulo = titulo;
		init();
	}
	
	private void init(){
		footerBar = new FormButtonsBar();
		linkCerrar = new SimpleLink("Cerrar");
		linkAceptar = new SimpleLink("Aceptar");
		texto = new Label(this.textoAConsultar);
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
	
	public String getTextoAConsultar() {
		return textoAConsultar;
	}

	public void setTextoAConsultar(String textoAConsultar) {
		this.textoAConsultar = textoAConsultar;
	}

	public Command getComandoAceptar() {
		return comandoAceptar;
	}

	public void setComandoAceptar(Command comandoAceptar) {
		this.comandoAceptar = comandoAceptar;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
