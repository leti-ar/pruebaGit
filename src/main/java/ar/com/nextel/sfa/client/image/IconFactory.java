package ar.com.nextel.sfa.client.image;

import ar.com.nextel.sfa.client.widget.UILoader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class IconFactory {

	private static IconBundle iconBundle = null;
	private static Image imagenFinal;

	private static IconBundle getIconBundle() {
		if (iconBundle == null) {
			iconBundle = (IconBundle) GWT.create(IconBundle.class);
		}
		return iconBundle;
	}

	public static Image lupa() {
		return getIconBundle().lupa().createImage();
	}

	public static Image cancel() {
		return getIconBundle().cancel().createImage();
	}

	public static Image lapiz() {
		return getIconBundle().lapiz().createImage();
	}
	
	public static Image locked() {
		return getIconBundle().locked().createImage();
	}
	
	public static Image addImageWithListener(String imagen){
		if (imagen.toString() == "lapiz"){
			imagenFinal = lapiz();
			imagenFinal.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
				//TODO: Modificar esta implementacion. Deberia llamar al servicio especifico que luego abrira la pantalla correspondiente.
					UILoader.getInstance().setPage(UILoader.BUSCAR_CUENTA);
				}
			});			
		}
	return imagenFinal;
	}
}
