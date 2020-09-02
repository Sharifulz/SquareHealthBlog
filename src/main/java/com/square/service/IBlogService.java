package com.square.service;

import java.util.Map;

import com.square.model.BlogPostModel;
import com.square.payload.CommonRequestViewModel;

public interface IBlogService {
	//-------- FOR WEB
	public Map<String, Object> changeApprovalStatus(Boolean activeStatus, int id); 
	public Map<String, Object> deleteBlog(int id);
	public Map<String, Object> saveBlog(BlogPostModel blog);
	public Map<String, Object> saveBlogByUser(BlogPostModel blog);
	public Map<String, Object> getByIsApprovedTrueOrderByPostDateDesc();
	
	//--------- FOR API
	public Map<String, Object> removePost(CommonRequestViewModel viewModel);
	public Map<String, Object> savePost(CommonRequestViewModel viewModel);
	public Map<String, Object> likesDislikes(CommonRequestViewModel viewModel, String reactionType);
	
	
}
