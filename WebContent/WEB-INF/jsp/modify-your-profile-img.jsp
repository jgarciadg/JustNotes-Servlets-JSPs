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
	<div id="change-content-profile-img">
		<div id="h2-intro">
			<h2 class="title">Select an image for your profile</h2>
		</div>
		<c:forEach var="image" items="${images}">
			<a
				href="${pageContext.request.contextPath}/notes/ModifyProfileServlet?idi=${image.idi}">
				<img class="profile-img" src="${image.url}"
				alt="Profile Image ${image.idi}">
			</a>
		</c:forEach>
	</div>

</body>
</html>