package com.square.service;

import java.util.Map;

import com.square.payload.CommonRequestViewModel;

public interface ICommentsService {

	public Map<String, Object> saveComments(CommonRequestViewModel viewModel);
	
}
