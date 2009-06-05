package ar.com.nextel.sfa.client;

import ar.com.nextel.sfa.client.dto.UserCenterDto;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface UserCenterRpcServiceAsync {
	public void getUserCenter(AsyncCallback<UserCenterDto> callback);
	public void getDevUserData(AsyncCallback<UserCenterDto> callback);
}


