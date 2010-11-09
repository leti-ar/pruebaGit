package ar.com.nextel.sfa.client;

import java.util.HashMap;

import ar.com.nextel.sfa.client.dto.UserCenterDto;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface UserCenterRpcServiceAsync {
	public void getUserCenter(AsyncCallback<UserCenterDto> callback);
	public void getDevUserData(AsyncCallback<UserCenterDto> callback);
	//MGR - #1050
	public void getKnownInstance(AsyncCallback<HashMap<String, Long>> callback);
}


