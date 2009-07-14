package ar.com.nextel.sfa.client.util;

import java.util.List;

import ar.com.nextel.sfa.client.dto.MessageDto;

public class MessageUtils {

	public static String getMessagesHTML(List<MessageDto> messages) {
		StringBuilder messagesString = new StringBuilder();
		for (MessageDto message : messages) {
			if (message.isError()) {
				messagesString.append("<span class=\"error\">Error: ");
			} else if (message.isWarn()) {
				messagesString.append("<span class=\"warn\">Atención: ");
			} else if (message.isInfo()) {
				messagesString.append("<span class=\"info\">");
			}
			messagesString.append(message.getDescription());
			messagesString.append("</span><br>");
		}
		return messagesString.toString();
	}
}
