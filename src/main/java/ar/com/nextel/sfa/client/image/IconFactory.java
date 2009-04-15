package ar.com.nextel.sfa.client.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

public class IconFactory {

	private static IconBundle iconBundle = null;

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
}
