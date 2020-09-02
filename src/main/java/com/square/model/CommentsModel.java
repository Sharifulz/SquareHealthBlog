package com.square.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class CommentsModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;	


	@Column(name = "username")
	private String userName;	

	@Column(name = "comments")
	private String comments;

	//-------- This id is mapped with id in blog_post table
	@Column(name = "post_id")
	private int postId;

	@Column(name = "comments_date")
	private Date commentsDate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public Date getCommentsDate() {
		return commentsDate;
	}

	public void setCommentsDate(Date commentsDate) {
		this.commentsDate = commentsDate;
	}

	public CommentsModel() {}
	
	

}
