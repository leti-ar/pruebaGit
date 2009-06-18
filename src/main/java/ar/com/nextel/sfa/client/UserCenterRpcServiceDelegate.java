package ar.com.nextel.sfa.client;

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

}
