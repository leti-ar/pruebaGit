package ar.com.nextel.sfa.client.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

public class IconFactory {

	private static IconBundle iconBundle = null;

	static final String LUPA_URL = "images/lupa.gif";
	static final String LOCKED_URL = "images/locked.gif";
	static final String LAPIZ_URL = "images/lapiz.gif";

	static {
		// @TODO Analizar uso de prefetch. No estoy liberando estos URLs
		Image.prefetch(LUPA_URL);
		Image.prefetch(LAPIZ_URL);
		Image.prefetch(LOCKED_URL);		
	}
	
	private static IconBundle getIconBundle() {
		if (iconBundle == null) {
			iconBundle = (IconBundle) GWT.create(IconBundle.class);
		}
		return iconBundle;
	}

	public static Image lupa() {
		return new Image(LUPA_URL);
	}

	public static Image lupa(String title) {
		Image img = IconFactory.lupa();
		img.setTitle(title);
		return img;
	}

	public static Image cancel() {
		return getIconBundle().cancel().createImage();
	}

	public static Image lapiz() {
		return new Image(LAPIZ_URL);		
	}

	public static Image lapiz(String title) {
		Image img = IconFactory.lapiz();
		img.setTitle(title);
		return img;
	}

	public static Image locked() {
		return new Image(LAPIZ_URL);
	}

	public static Image locked(String title) {
		Image img = IconFactory.locked();
		img.setTitle(title);
		return img;
	}

	public static Image word() {
		return getIconBundle().word().createImage();
	}

	public static Image silvioSoldan() {
		return getIconBundle().silvioSoldan().createImage();
	}

	public static Image copiar() {
		return getIconBundle().copiar().createImage();
	}
	
	public static Image tildeVerde() {
		return getIconBundle().tildeVerde().createImage();
	}
	
	public static Image excel() {
		return getIconBundle().excel().createImage();
	}
	
	public static Image vistaPreliminar() {
		return getIconBundle().vistaPreliminar().createImage();
	}
	
}
