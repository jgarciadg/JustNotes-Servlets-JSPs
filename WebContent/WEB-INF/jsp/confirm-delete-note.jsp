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
		<h1>Are you sure that you want to delete this note?</h1>
	</div>
	<div id="content-notes">
		<c:forEach items="${notes}" var="note">
			<div class="note">
				<div class="note-up">
					<div class="img-note">
						<img class="img-options"
							src="${pageContext.request.contextPath}/images/Logo.png">
					</div>
					<div class="note-title">
						<h2>${note.title}</h2>
					</div>
				</div>
				<div class="note-description">
					<p>${note.content}</p>
				</div>
				<div class="labels">No Tags</div>
			</div>
		</c:forEach>
	</div>
	<form
		action="${pageContext.request.contextPath}/notes/DeleteNoteServlet"
		method="POST">
		<c:forEach items="${notes}" var="note">
			<input type="hidden" name="idn" value="${note.idn}">
		</c:forEach>
		<input type="submit" value="Delete">
	</form>

</body>
</html>