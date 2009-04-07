package ar.com.nextel.sfa.client.constant;

import com.google.gwt.core.client.GWT;

public class Sfa {

	private static SfaStatic sfaStatic;
	static{
		sfaStatic = GWT.create(SfaStatic.class);
	}
		
	public static SfaStatic constant(){
		return sfaStatic;
	}
}
