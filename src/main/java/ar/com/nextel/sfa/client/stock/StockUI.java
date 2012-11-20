package ar.com.nextel.sfa.client.stock;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.NextelDialog;

public class StockUI extends ApplicationUI {

	private static StockUI instance = new StockUI();
	NextelDialog dialog;

	public StockUI() {
		super();
		init();
	}

	public static StockUI getInstance() {
		return instance;
	}

	public void init() {
		// if (firstLoad) {
		dialog = new StockUIDlg(Sfa.constant().ValidarStock());
		// mainPanel.add(CuentaEdicionTabPanel.getInstance().getCuentaEdicionPanel());
		// }
	}

	public boolean load() {
		((StockUIDlg)dialog).fromMenu = true;
		((StockUIDlg)dialog).initCombos();
		dialog.showAndCenter();
		return true;
	}

	public void firstLoad() {
	}

	public boolean unload(String token) {
		//((StockUIDlg)dialog).clearCombos();
		return true;
	}

}
