package com.square.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.square.model.BlogPostModel;
import com.square.payload.CommonRequestViewModel;
import com.square.service.IAdminService;
import com.square.service.IBlogService;
import com.square.service.ICommentsService;

@RestController
@RequestMapping("/admin_api")
public class RestAdminController {

	@Autowired
	IAdminService adminService;

	@Autowired
	IBlogService blogService;
	
	@Autowired
	ICommentsService commentService;
	
	/*-----------
	Top Features: 
	
	Login using username password [DONE]
	
	Create admin account [DONE]
	
	Approve / Deactivate bloggers account [DONE]
	
	Approve post [DONE]
	
	Delete blog post [DONE]
	
	Publish post [DONE]
	
	*/
	@PostMapping("/create_admin")
	public ResponseEntity<Map<String, Object>> createNewAdminAccount(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = adminService.createNewAdmin(viewModel);
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@PostMapping("/activate_blogger")
	public ResponseEntity<Map<String, Object>> enableBlogger(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = adminService.enableBlogger(viewModel);
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@PostMapping("/deactivate_blogger")
	public ResponseEntity<Map<String, Object>> disableBlogger(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = adminService.disableBlogger(viewModel);
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@PostMapping("/approve_post")
	public ResponseEntity<Map<String, Object>> approvePost(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = adminService.approvePost(viewModel);
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@PostMapping("/remove_post")
	public ResponseEntity<Map<String, Object>> removePost(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = adminService.removePost(viewModel);
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@PostMapping("/add_post")
	public ResponseEntity<Map<String, Object>> addNewPost(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		if (viewModel.getPostDescription()!=null && !viewModel.getPostDescription().equals("")) {
			BlogPostModel blog = new BlogPostModel();
			blog.setDescription(viewModel.getPostDescription());
			data = blogService.saveBlog(blog);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
			return responseEntity;
		}
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@PostMapping("/post_comments")
	public ResponseEntity<Map<String, Object>> saveComments(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = commentService.saveComments(viewModel);
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@PostMapping("/save_post")
	public ResponseEntity<Map<String, Object>> savePost(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = blogService.savePost(viewModel);
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
}
