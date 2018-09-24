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
	<div id="change-data-profile-box">
		<form
			action="${pageContext.request.contextPath}/notes/AdvancedSearchUserServlet"
			method="POST">
			<fieldset>
				<legend>Search Information</legend>
				<c:forEach var="error" items="${errors}">
					<h3>${error}</h3>
				</c:forEach>
				<label for="username-search">Username</label> <input
					class="input-modify" id="username-search" name="username-search"
					placeholder="Name of User" type="text">
				<div id="area-labels">
					<label>Labels</label>
					<div id="content-info-typeusers">
						<div class="type-user">
							<input id="typeuser1" class="input-checkbox" name="typeuser"
								type="radio" value="friends"> <label for="typeuser1"
								class="user-type-checkbox">Friends</label>
						</div>
						<div class="type-user">
							<input id="typeuser2" class="input-checkbox" name="typeuser"
								type="radio" value="allusers" checked> <label
								for="typeuser2">All Users</label>
						</div>
					</div>
				</div>
			</fieldset>
			<input type="submit" value="Search">
		</form>
	</div>
</body>
</html>