package ar.com.nextel.sfa.client.command;

import ar.com.nextel.sfa.client.widget.UILoader;

import com.google.gwt.user.client.Command;

public class OpenPageCommand implements Command {

	public int page;

	public OpenPageCommand(int page) {
		this.page = page;
	}

	public void execute() {
		UILoader.getInstance().setPage(page);
	}

}
