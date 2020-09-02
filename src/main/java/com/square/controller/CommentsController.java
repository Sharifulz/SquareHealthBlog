package com.square.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.square.model.CommentsModel;
import com.square.model.UsersModel;
import com.square.service.ICommentsService;

@Controller
public class CommentsController {
///comment/comment_on_post/{postId}
//"/like/{postId}", "/dislike/{postId}"	
	
	
	@Autowired
	ICommentsService commentsService;
	
	@PostMapping("/comment/comment_on_post/{postId}")
	public ModelAndView commentsOnPost(@PathVariable("postId") int postId, @ModelAttribute("comments") String comments) {
		ModelAndView mv  = new ModelAndView();
		Map<String, Object> data = new HashMap<>();
		
		data = commentsService.saveComments(postId, comments);
		
		mv.setViewName("user");
		mv.addObject("currentUser",  data.get("currentUser"));
		mv.addObject("approvedPosts", data.get("approvedPosts"));
		mv.addObject("messages", data.get("message"));
		mv.addObject("errorMessage", data.get("errorMessage"));
		
		return mv;
	}
	
	@GetMapping("/like/{postId}")
	public ModelAndView likePost(@PathVariable("postId") int postId) {
		ModelAndView mv  = new ModelAndView();
		Map<String, Object> data = new HashMap<>();
		
		data = commentsService.likeDislikePost(postId, "LIKE");
		
		mv.setViewName("user");
		mv.addObject("currentUser",  data.get("currentUser"));
		mv.addObject("approvedPosts", data.get("approvedPosts"));
		mv.addObject("messages", data.get("message"));
		mv.addObject("errorMessage", data.get("errorMessage"));
		
		return mv;
	}
	
	@GetMapping("/dislike/{postId}")
	public ModelAndView dislikePost(@PathVariable("postId") int postId) {
		ModelAndView mv  = new ModelAndView();
		Map<String, Object> data = new HashMap<>();
		
		data = commentsService.likeDislikePost(postId, "DISLIKE");
		
		mv.setViewName("user");
		mv.addObject("currentUser",  data.get("currentUser"));
		mv.addObject("approvedPosts", data.get("approvedPosts"));
		mv.addObject("messages", data.get("message"));
		mv.addObject("errorMessage", data.get("errorMessage"));
		
		return mv;
	}
	
	
}
