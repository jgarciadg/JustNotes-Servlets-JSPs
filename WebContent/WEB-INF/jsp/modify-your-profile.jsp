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
	<div id="modify-background">
		<a
			href="${pageContext.request.contextPath}/notes/ModifyProfileImgServlet">
			<img id="img-profile" src="${profileimage.url}" alt="Profile Image">
		</a>
	</div>

	<div id="change-data-profile-box">
		<form
			action="${pageContext.request.contextPath}/notes/ModifyProfileServlet"
			method="POST">
			<c:forEach items="${errors}" var="error">
				<h2>${error}</h2>
			</c:forEach>
			<fieldset>
				<legend>Login Information</legend>
				<input type="hidden" name="idi" value="${profileimage.idi}">
				<label for="username">Username</label> <input class="input-modify"
					type="text" id="username" name="username" placeholder="Username"
					value="${user.username}" required> <label for="email">Email</label>
				<input class="input-modify" type="email" id="email" name="email"
					placeholder="Email" value="${user.email}" required> <label
					for="password">Password</label> <input class="input-modify"
					type="password" id="password" name="password"
					placeholder="Password" value="${user.password}" required> <label
					for="verifiedpassword">Confirm your password</label> <input
					class="input-modify" type="password" id="verifiedpassword"
					name="verifiedpassword" placeholder="Corfirm Password"
					value="${user.password}" required>
			</fieldset>
			<input type="submit" value="Save Changes"><br>
			<fieldset>
				<legend>Do you really want to delete your account?</legend>
				<a
					href="${pageContext.request.contextPath}/notes/DeleteAccountServlet"
					id="delete-account-button" value="">Delete Account</a>
			</fieldset>

		</form>
	</div>

</body>
</html>