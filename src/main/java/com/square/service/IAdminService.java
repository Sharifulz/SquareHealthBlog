package com.square.service;

import java.util.Map;

import com.square.payload.CommonRequestViewModel;

public interface IAdminService {

	public Map<String, Object> createNewAdmin(CommonRequestViewModel viewModel); 
	public Map<String, Object> enableBlogger(CommonRequestViewModel viewModel);
	public Map<String, Object> disableBlogger(CommonRequestViewModel viewModel);
	public Map<String, Object> approvePost(CommonRequestViewModel viewModel);
	public Map<String, Object> removePost(CommonRequestViewModel viewModel);
	Map<String, Object> validateViewModelDataToCreateAdmin(CommonRequestViewModel viewModel);
	
	
}
