package ar.com.nextel.sfa.client;

import java.util.HashMap;

import ar.com.nextel.sfa.client.dto.UserCenterDto;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("usercenter.rpc")
public interface UserCenterRpcService extends RemoteService {

	public static class Util {

		private static UserCenterRpcServiceAsync userCenterRpcService = null;
		private static UserCenterRpcServiceDelegate userCenterRpcServiceDelegate = null;

		public static UserCenterRpcServiceDelegate getInstance() {
			if (userCenterRpcServiceDelegate == null) {
				userCenterRpcService = GWT.create(UserCenterRpcService.class);
				userCenterRpcServiceDelegate = new UserCenterRpcServiceDelegate(userCenterRpcService);
			}
			return userCenterRpcServiceDelegate;
		}
	}

	public UserCenterDto getUserCenter() throws RpcExceptionMessages;
	public UserCenterDto getDevUserData() throws RpcExceptionMessages;
	//MGR - #1050
	public HashMap<String, Long> getKnownInstance();
	
}
	
