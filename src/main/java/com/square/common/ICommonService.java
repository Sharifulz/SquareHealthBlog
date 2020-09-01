package com.square.common;

public interface ICommonService {
	public String encodeString(String actualString,String id);
	public String decodeString(String encodedString, String id);
	
	public String hashingString(String str);
	
	public String getCurrentUser();
	
}
