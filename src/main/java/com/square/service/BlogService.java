package com.square.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.square.common.ICommonService;
import com.square.dao.IBlogPostDao;
import com.square.dao.ICommentDao;
import com.square.dao.IUsersDao;
import com.square.model.BlogPostModel;
import com.square.model.UsersModel;

@Service
public class BlogService implements IBlogService {

	@Autowired
	IUsersDao userDao;
	
	@Autowired
	IBlogPostDao blogDao;
	
	@Autowired
	ICommentDao commentsDao;
	
	@Autowired
	ICommonService commonService;
	
	@Override
	public Map<String, Object> changeApprovalStatus(Boolean activeStatus, int id) {
		Map<String, Object> data = new HashMap<>();
		int updatedRow = blogDao.approveBlog(activeStatus, id);

		if (updatedRow>0) {
			data.put("result", "successful");
		}else {
			data.put("result", "failure");
		}
		return data;
	}

	@Override
	public Map<String, Object> deleteBlog(int id) {
		Map<String, Object> data = new HashMap<>();
		BlogPostModel blog = blogDao.findById(id);
		
		if (blog!=null) {
			int deleteCommentsRow = commentsDao.deleteCorrespondingCommentsByPostId(id);
			blogDao.delete(blog);
			//----------- Remove all corresponding comments from blog comments table
			
			data.put("result", "successful");
		}else {
			data.put("result", "failure");
		}

		return data;
	}

	@Override
	public Map<String, Object> saveBlog(BlogPostModel blog) {
		Map<String, Object> data = new HashMap<>();
		Optional<UsersModel> user = null;
		List<String> message = new ArrayList<>();
		String userName = commonService.getCurrentUser();
		
		try {
			user = userDao.findByUserName(userName);
			if (user.isPresent()) {
				blog.setApproved(true);
				blog.setPostDate(new Date(System.currentTimeMillis()));
				blog.setUserName(user.get().getUserName());
				BlogPostModel blogPost = blogDao.save(blog);
				
				message.add("New post saved by admin");
				data.put("result", "successful");
				data.put("data", blogPost);
				data.put("message", message);
				data.put("responseCode", "200");
			}
		} catch (Exception e) {
			message.add("Failed to post "+ e.getLocalizedMessage());
			data.put("result", "failure");
			data.put("data", "");
			data.put("message", message);
			data.put("responseCode", "200");
		}
		
		
		return data;
	}

	@Override
	public Map<String, Object> saveBlogByUser(BlogPostModel blog) {
		Map<String, Object> data = new HashMap<>();
		Optional<UsersModel> user = null;
		String userName = commonService.getCurrentUser();
		
		try {
			user = userDao.findByUserName(userName);
			if (user.isPresent()) {
				blog.setPostDate(new Date(System.currentTimeMillis()));
				blog.setUserName(user.get().getUserName());
				blogDao.save(blog);
				data.put("result", "successful");
			}
		} catch (Exception e) {data.put("result", "failure");}
		
		
		return data;
	}


	
}
