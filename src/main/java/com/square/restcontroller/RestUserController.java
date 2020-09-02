package com.square.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.square.payload.CommonRequestViewModel;
import com.square.service.IBlogService;
import com.square.service.ICommentsService;
import com.square.service.IUserService;

@RestController
@RequestMapping("/blogger_api")
public class RestUserController {

	@Autowired
	IBlogService blogService;
	
	@Autowired
	ICommentsService commentService;
	
	@Autowired
	IUserService userService;
	/*-----------
	Top Features: 
	
	Login using username password [DONE]
	
	Show others blog post [DONE]
	
	Comment on others blog  [DONE]
	
	Delete own blog post [DONE]
	
	Publish post sending for approval [DONE]
	
	Like, dislike [DONE]
	
	*/
	
	//------ Required body  post id :: {"ids":[1]}
	@PostMapping("/register_user")
	public ResponseEntity<Map<String, Object>> registerUser(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = userService.saveUser(viewModel);
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@GetMapping("/get_approved_posts")
	public ResponseEntity<Map<String, Object>> getAllApprovedPost() {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = blogService.getByIsApprovedTrueOrderByPostDateDesc();
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	//------ Required body  post id :: {"ids":[1]}
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
	
	//------ Required body  post id :: {"ids":[1]}
	@PostMapping("/remove_post")
	public ResponseEntity<Map<String, Object>> removePost(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = blogService.removePost(viewModel);
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	//------ Required body  : {"postDescription":""}
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
	
	//------ Required body post id : {"ids":[]}
	@PostMapping("/like_post")
	public ResponseEntity<Map<String, Object>> likePost(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = blogService.likesDislikes(viewModel, "LIKE");
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	//------ Required body post id : {"ids":[]}
	@PostMapping("/dislike_post")
	public ResponseEntity<Map<String, Object>> dislikePost(@RequestBody CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		ResponseEntity<Map<String,Object>> responseEntity = null;
		
		data = blogService.likesDislikes(viewModel, "DISLIKE");
				
		if (data.get("responseCode").equals("412")) {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.PRECONDITION_FAILED);
		}else {
			responseEntity = new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}
		return responseEntity;
	}
}
