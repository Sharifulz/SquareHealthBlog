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
			if (blog!=null) {
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
}
