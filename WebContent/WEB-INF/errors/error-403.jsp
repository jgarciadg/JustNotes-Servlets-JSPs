<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheet/errors.css">
<title>Error 403</title>
</head>
<body>
	<h1 class="error-h1">Sorry. This is forbidden</h1>
	<img class="error-image" alt="Error 403"
		src="${pageContext.request.contextPath}/images/error-images/403.jpg">
	<a class="error-a"
		href="${pageContext.request.contextPath}/LoginServlet">Return to
		Login Page</a>
</body>
</html>