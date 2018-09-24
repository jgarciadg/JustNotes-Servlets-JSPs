<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>JustNotes</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/images/Logo.png">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheet/stylesheet5.css">
</head>
<body>
	<c:import url="/WEB-INF/jsp/logged-header.jsp"></c:import>
	<div class="h2-full">
		<h1>Are you sure that you want to delete this account?</h1>
	</div>
	<div class="content-friend">
		<div class="img-friend-content">
			<img class="img-friend" src="${profileimage.url}" alt="Friend Profile Img">
		</div>
		<div class="info-friend-content">
			<div class="name-friend">
				<h2>Username: ${user.username}</h2>
			</div>
			<label>E-mail: </label>${user.email}<br>
			<label>Notes Created: </label>${howmanycreated}<br> 
			<label>Notes Shared: </label>${howmanyshared}<br>
			<label>Notes Archived: </label>${howmanyarchived}<br>
			<div class="options-friend"></div>
		</div>
	</div>
	<form action="?" method="POST">
		<input type="submit" value="Delete Account">
	</form>

</body>
</html>