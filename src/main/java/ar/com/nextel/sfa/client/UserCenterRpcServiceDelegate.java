package ar.com.nextel.sfa.client;

import java.util.HashMap;

import ar.com.nextel.sfa.client.dto.UserCenterDto;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

public class UserCenterRpcServiceDelegate {

	private UserCenterRpcServiceAsync userCenterRpcService;

	public UserCenterRpcServiceDelegate() {
	}

	public UserCenterRpcServiceDelegate(UserCenterRpcServiceAsync userCenterRpcService) {
		this.userCenterRpcService = userCenterRpcService;
	}

	public void getUserCenter(DefaultWaitCallback<UserCenterDto> callback) {
		WaitWindow.show();
		this.userCenterRpcService.getUserCenter(callback);
	}

	public void getDevUserData(DefaultWaitCallback<UserCenterDto> callback) {
		WaitWindow.show();
		this.userCenterRpcService.getDevUserData(callback);
	}

	//MGR - #1050
	public void getKnownInstance(DefaultWaitCallback<HashMap<String, Long>> callback) {
		this.userCenterRpcService.getKnownInstance(callback);
	}

}
