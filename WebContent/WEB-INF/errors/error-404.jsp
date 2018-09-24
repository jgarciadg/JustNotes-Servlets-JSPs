<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheet/errors.css">
<title>Error 404</title>
</head>
<body>
	<h1 class="error-h1">I can not found this resource. What are you
		searching?</h1>
	<img class="error-image" alt="Error 404"
		src="${pageContext.request.contextPath}/images/error-images/404.jpg">
	<a class="error-a"
		href="${pageContext.request.contextPath}/LoginServlet">Return to
		Login Page</a>
</body>
</html>