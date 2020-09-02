<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>User Page</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<style>
body {
	font-family: 'Varela Round', sans-serif;
}
.modal-login {
	color: #636363;
	width: 350px;
}
.modal-login .modal-content {
	padding: 20px;
	border-radius: 5px;
	border: none;
}
.modal-login .modal-header {
	border-bottom: none;
	position: relative;
	justify-content: center;
}
.modal-login h4 {
	text-align: center;
	font-size: 26px;
}
.modal-login  .form-group {
	position: relative;
}
.modal-login i {
	position: absolute;
	left: 13px;
	top: 11px;
	font-size: 18px;
}
.modal-login .form-control {
	padding-left: 40px;
}
.modal-login .form-control:focus {
	border-color: #00ce81;
}
.modal-login .form-control, .modal-login .btn {
	min-height: 40px;
	border-radius: 3px; 
}
.modal-login .hint-text {
	text-align: center;
	padding-top: 10px;
}
.modal-login .close {
	position: absolute;
	top: -5px;
	right: -5px;
}
.modal-login .btn, .modal-login .btn:active {	
	border: none;
	background: #00ce81 !important;
	line-height: normal;
}
.modal-login .btn:hover, .modal-login .btn:focus {
	background: #00bf78 !important;
}
.modal-login .modal-footer {
	background: #ecf0f1;
	border-color: #dee4e7;
	text-align: center;
	margin: 0 -20px -20px;
	border-radius: 5px;
	font-size: 13px;
	justify-content: center;
}
.modal-login .modal-footer a {
	color: #999;
}
.trigger-btn {
	display: inline-block;
	margin: 100px auto;
}
</style>
</head>
<body>

<div class="container">
	<div class="row p-2">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<fieldset style="margin: 0 auto; text-align: center;">
			<a href="/" class=" badge badge-primary" style="padding: 15px;">HOME</a>
			<a href="/user" class=" badge badge-primary" style="padding: 15px;">USER</a>
			<a href="/admin" class="badge badge-primary" style="padding: 15px;">ADMIN</a>
			<a href="/logout" class="badge badge-primary" style="padding: 15px;">LOGOUT</a>
		</fieldset>
		</div>
		<div class="col-md-3"></div>
	</div>
</div>

<div class="container">
	<div class="row p-2">
		<div class="col-md-3"></div>
		<div class="col-md-6">
				<h2 style="text-align: right;"><i class="fa fa-user fa-1x mr-2"></i> ${currentUser.userName}</h2>
				<h2 style="text-align: right;"><i class="fa fa-superpowers fa-1x mr-2"></i> ${currentUser.roles}</h2>
		</div>
		<div class="col-md-3"></div>
	</div>
</div>



<c:forEach items="${messages}" var="message">
	<div class="alert alert-primary" role="alert">
  		Dear ${currentUser.userName} , ${message}
	</div>
</c:forEach>

<c:forEach items="${errorMessage}" var="error">
	<div class="alert alert-danger" role="alert">
  		 ${error}
	</div>
</c:forEach>

<c:forEach items="${warningMessage}" var="warning">
	<div class="alert alert-warning" role="alert">
  		 ${warning}
	</div>
</c:forEach>
<!-- ==================================================== -->
<div class="container">
	<div class="row mb-2">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<form action="/blog/create_by_user" method="post">
			  <div class="form-group">
			    <label for="exampleInputEmail1">Status</label>
			    <input type="text" class="form-control" placeholder="Type your post" name="description">
			  </div>
			  <button type="submit" class="btn btn-primary">Submit <i class="fa fa-paper-plane ml-2"></i></button>
			</form>
		</div>
		<div class="col-md-3"></div>
	</div>
</div>
	

<div class="container">
<c:forEach items="${approvedPosts}" var="approvedPost">
	<div class="row mb-2">
		<div class="col-md-3"></div>
		<div class="col-md-6">
		<div class="card" style="width: 34rem;">
		  <i class="fa fa-twitter" style="font-size: 5rem; text-align: center"></i>
		  <div class="card-body">
		    <span class="card-title"><i class="fa fa-user mr-2"></i> ${approvedPost.userName}</span>
		    <span class="card-title"><i class="fa fa-id-badge mr-2"></i>  ${approvedPost.id}</span>
		    <span class="card-title"><i class="fa fa-calendar mr-2"></i>  ${approvedPost.postDate}</span>
		    <h2>${approvedPost.description}</h2>
		   
		    <c:forEach items="${approvedPost.commentsList}" var="comments">
				<p><i class="fa fa-comments mr-2"></i> ${comments.comments}</p>
			</c:forEach>
		    <div style="text-align: right; margin-bottom: 10px;">
		    	 <a href="/like/${approvedPost.id}" class="btn btn-dark"><i class="fa fa-thumbs-up" style="text-align: center">${approvedPost.likes}</i></a>
			    <a href="/dislike/${approvedPost.id}" class="btn btn-dark"><i class="fa fa-thumbs-down" style="text-align: center">${approvedPost.dislikes}</i></a>
			    <a href="#" class="btn btn-dark"><i class="fa fa-comment" style="text-align: center">${approvedPost.comments}</i></a>
			     <c:if test="${approvedPost.userName eq currentUser.userName}">  
			  	 <span><a href="/blog/remove_by_user/${approvedPost.id}" class="btn btn-danger"><i class="fa fa-trash" style="text-align: center"></i></a></span>  
			</c:if>  
		    </div>
		    <form action="/comment/comment_on_post/${approvedPost.id}" method="post">
			  <div class="form-group">
			    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter your comment" name="comments">
			  </div>
				<button type="submit" class="btn btn-primary btn-block">Submit <i class="fa fa-paper-plane ml-2"></i></button>
			</form>
		  </div>
		</div>
		</div>
		<div class="col-md-3"></div>
	</div>
	</c:forEach>
</div> 

</body>
</html>