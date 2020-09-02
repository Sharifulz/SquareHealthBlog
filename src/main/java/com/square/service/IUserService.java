package com.square.service;

import java.util.Map;

import com.square.model.UsersModel;
import com.square.payload.CommonRequestViewModel;

public interface IUserService {

	public Map<String, Object> getCurrentUserDetails(String userName);
	
	public Map<String, Object> saveUser(UsersModel user, String roleType);
	
	public Map<String, Object> getAllDataForAdmin(); 
	
	public Map<String, Object> changeActivationStatus(Boolean activeStatus, int id); 
	
	public Map<String, Object> deleteUser(int id); 
	
	//---------- For API
	public Map<String, Object> saveUserFromApi(CommonRequestViewModel viewModel);
	
	
}
