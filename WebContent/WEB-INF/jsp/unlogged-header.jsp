<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<header>
		<div id="contain-header">
			<div id="left-header">
				<a href="${pageContext.request.contextPath}/LoginServlet">
					<h1>JustNotes</h1>
				</a>
			</div>
			<div id="center-header">
				<img id="center-header-img"
					src="${pageContext.request.contextPath}/images/Logo.png" alt="Logo">
			</div>
			<div id="right-header">
				<a href="${pageContext.request.contextPath}/LoginServlet">
					<div class="header-right-link">Sign In</div>
				</a> <a href="${pageContext.request.contextPath}/RegisterServlet">
					<div class="header-right-link active-link">Sign Up</div>
				</a>
			</div>
		</div>
	</header>
</body>
</html>