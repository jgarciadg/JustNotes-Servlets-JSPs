<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE>
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
	<c:import url="/WEB-INF/jsp/unlogged-header.jsp"></c:import>
	<div id="half-left" class="half">
		<img
			src="http://www.diycollegerankings.com/wp-content/uploads/2016/03/yellow-posit.png"
			alt="Decorative Posit">
	</div>
	<div id="half-right" class="half">
		<div class="box box-center">
			<form action="${pageContext.request.contextPath}/LoginServlet"
				method="POST">
				<h2>Login</h2>
				<h3>${error}</h3>
				<input type="text" name="username" placeholder="Username" required>
				<input type="password" name="password" placeholder="Password"
					required> <input type="submit" value="Login">
			</form>

		</div>
	</div>
</body>
</html>