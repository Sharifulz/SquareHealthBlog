package com.square.service;

import java.util.Map;

import com.square.payload.CommonRequestViewModel;

public interface ICommentsService {

	//----------- For WEB
	public Map<String, Object> saveComments(int postId, String comments);
	public Map<String, Object> likeDislikePost(int postId, String reactType);
	
	//----------- For API
	public Map<String, Object> saveComments(CommonRequestViewModel viewModel);
	
}
