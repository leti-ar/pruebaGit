package ar.com.nextel.sfa.client.command;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;

public class OpenPageCommand implements Command {

	public int page;
	private String params;

	public OpenPageCommand(int page) {
		this.page = page;
		this.params = null;
	}

	public OpenPageCommand(int page, String params) {
		this.page = page;
		this.params = params;
	}

	public void execute() {

		if (params != null) {
			History.newItem("" + page + "?" + params);
		} else {
			History.newItem("" + page);
		}

	}
}
