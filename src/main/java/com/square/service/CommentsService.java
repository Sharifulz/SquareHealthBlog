package com.square.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.square.common.ICommonService;
import com.square.dao.IBlogPostDao;
import com.square.dao.ICommentDao;
import com.square.dao.IUsersDao;
import com.square.model.BlogPostModel;
import com.square.model.CommentsModel;
import com.square.model.UsersModel;
import com.square.payload.CommonRequestViewModel;

@Service
public class CommentsService implements ICommentsService {

	@Autowired
	IUsersDao userDao;
	
	@Autowired
	IBlogPostDao blogDao;
	
	@Autowired
	ICommentDao commentsDao;
	
	@Autowired
	ICommonService commonService;
	
	
	@Override
	public Map<String, Object> saveComments(CommonRequestViewModel viewModel) {
		Map<String, Object> data = new HashMap<>();
		List<String> message = new ArrayList<>();
		BlogPostModel blog = null;
		String userName = commonService.getCurrentUser();
		
		UsersModel user = userDao.findByUserName(userName).get();
		if (user.getRoles().equals("ROLE_ADMIN") || user.getRoles().equals("ROLE_USER")) {
			//------- Check if the post exist
			blog = blogDao.findById(Integer.parseInt(viewModel.getIds()[0]));
			if (blog!=null && blog.isApproved()) {
				//-----------Post exist, now save the comment
				CommentsModel comments = new CommentsModel();
				comments.setComments(viewModel.getPostDescription());
				comments.setPostId(Integer.parseInt(viewModel.getIds()[0]));
				comments.setCommentsDate(new Date(System.currentTimeMillis()));
				comments.setUserName(userName);
				try {
					//--------- Update comment counting
					CommentsModel comm = commentsDao.save(comments);
					if (comm!=null) {
						//------- Update count comments ++
						blog.setComments(blog.getComments()+1);
						BlogPostModel updatedBlog = blogDao.save(blog);
						if (updatedBlog!=null) {
							message.add("Successfully commented on post");
							data.put("result", "successful");
							data.put("data", updatedBlog);
							data.put("message", message);
							data.put("responseCode", "200");
						}
					}
				} catch (Exception e) {
					message.add("Failed to get approved post "+ e.getLocalizedMessage());
					data.put("result", "failure");
					data.put("data", "");
					data.put("message", message);
					data.put("responseCode", "412");
				}
				
			}else {
				message.add("The post is not approved yet");
				data.put("result", "failure");
				data.put("data", "");
				data.put("message", message);
				data.put("responseCode", "412");
			}
		}else {
			message.add("You are not assigned to any role yet.");
			data.put("result", "failure");
			data.put("data", "");
			data.put("message", message);
			data.put("responseCode", "412");
		}
		return data;
	}


	@Override
	public Map<String, Object> saveComments(int postId, String comments) {
		Map<String, Object> data = new HashMap<>();
		List<String> messages = new ArrayList<>();
		List<String> errorMessage = new ArrayList<>();
		List<BlogPostModel> approvedPosts = null;
		UsersModel currentUser = null;
		String userName = commonService.getCurrentUser();
		
		//--------- Check if this post exists
		BlogPostModel blog = null;
		
		try {
			//--------- Check if this post approved
			blog = blogDao.findById(postId);
			if (blog!=null) {
				if (blog.isApproved()) {
					
					CommentsModel cmnt = new CommentsModel();
					cmnt.setCommentsDate(new Date(System.currentTimeMillis()));
					cmnt.setPostId(postId);
					cmnt.setUserName(userName);
					cmnt.setComments(comments);
					CommentsModel comm = commentsDao.save(cmnt);
					if (comm!=null) {
						//---------------- Update post comments count
						blog.setComments(blog.getComments()+1);
						try {
							blogDao.save(blog);
							messages.add("Successfully saved comment and updated comment count "+ postId);
						} catch (Exception e) {
							errorMessage.add("Unexpected error occures " + e.getLocalizedMessage());
						}
					}
				}else {
					errorMessage.add("Post is not approved yet "+ postId);
				}
			}else {
				errorMessage.add("No such post found "+ postId);
			}
		} catch (Exception e) {
			errorMessage.add("Unexpected error occures " + e.getLocalizedMessage());
		}
		
		currentUser = userDao.findByUserName(userName).get();
		approvedPosts = blogDao.findByIsApprovedTrueOrderByPostDateDesc();
		data.put("currentUser", currentUser);
		data.put("approvedPosts", approvedPosts);
		data.put("message", messages);
		data.put("errorMessage", errorMessage);
		
		return data;
	}


	@Override
	public Map<String, Object> likeDislikePost(int postId, String reactType) {
		Map<String, Object> data = new HashMap<>();
		List<String> messages = new ArrayList<>();
		List<String> errorMessage = new ArrayList<>();
		List<BlogPostModel> approvedPosts = null;
		UsersModel currentUser = null;
		String userName = commonService.getCurrentUser();
		
		//--------- Check if this post exists
		BlogPostModel blog = null;
		
		try {
			//--------- Check if this post approved
			blog = blogDao.findById(postId);
			if (blog!=null) {
				if (blog.isApproved()) {
					//--------------- add like or dislike count for this post
					if (reactType.equalsIgnoreCase("LIKE")) {
						blog.setLikes(blog.getLikes()+1);
					}else if(reactType.equalsIgnoreCase("DISLIKE")) {
						blog.setDislikes(blog.getDislikes()+1);
					}
					try {
						blogDao.save(blog);
						messages.add("Successfully saved comment and updated comment count "+ postId);
					} catch (Exception e) {
						errorMessage.add("Unexpected error occures " + e.getLocalizedMessage());
					}
					
					
				}else {
					errorMessage.add("Post is not approved yet "+ postId);
				}
			}else {
				errorMessage.add("No such post found "+ postId);
			}
		} catch (Exception e) {
			errorMessage.add("Unexpected error occures " + e.getLocalizedMessage());
		}
		
		currentUser = userDao.findByUserName(userName).get();
		approvedPosts = blogDao.findByIsApprovedTrueOrderByPostDateDesc();
		data.put("currentUser", currentUser);
		data.put("approvedPosts", approvedPosts);
		data.put("message", messages);
		data.put("errorMessage", errorMessage);
		
		return data;
	}
}
