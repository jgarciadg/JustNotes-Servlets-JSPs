<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE>
<html lang="en">
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
	<c:import url="/WEB-INF/jsp/unlogged-header.jsp"></c:import>
	<div id="half-left" class="half">
		<div class="box box-center">
			<form action="${pageContext.request.contextPath}/RegisterServlet"
				method="POST">
				<h2>Register</h2>
				<c:forEach var="error" items="${errors}">
					<h3>${error}</h3>
				</c:forEach>
				<input type="text" name="username" placeholder="Username"
					value="${user.username}" required> <input type="email"
					name="email" placeholder="Email" value="${user.email}" required>
				<input type="password" name="password" placeholder="Password"
					value="${user.password}" required> <input type="password"
					name="verifiedpassword" placeholder="Corfirm Password"
					value="${verifiedpassword}" required> <input type="submit"
					value="Register">
			</form>

		</div>
	</div>
	<div id="half-right" class="half">
		<img
			src="http://www.diycollegerankings.com/wp-content/uploads/2016/03/yellow-posit.png"
			alt="Decorative Posit">
	</div>
</body>
</html>