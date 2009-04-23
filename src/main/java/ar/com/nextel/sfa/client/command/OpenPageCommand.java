package ar.com.nextel.sfa.client.command;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;

public class OpenPageCommand implements Command {

	public int page;

	public OpenPageCommand(int page) {
		this.page = page;
	}

	public void execute() {
		History.newItem("" + page);
	}

}
