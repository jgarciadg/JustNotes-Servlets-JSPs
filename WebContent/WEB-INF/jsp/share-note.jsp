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
		<c:choose>
			<c:when test="${notifications}">
				<h1>Who do you want to be friend?</h1>
			</c:when>
			<c:otherwise>
				<h1>Who do you want to share the note with?</h1>
			</c:otherwise>
		</c:choose>
	</div>
	<div id="content-friends">

		<c:choose>
			<c:when test="${notifications}">
				<form
					action="${pageContext.request.contextPath}/notes/NotificationsServlet"
					method="POST">
			</c:when>
			<c:otherwise>
				<form
					action="${pageContext.request.contextPath}/notes/ShareNoteServlet"
					method="POST">
			</c:otherwise>
		</c:choose>
		<input type="hidden" name="idn" value="${idn}">
		<c:choose>
			<c:when test="${notifications}">
				<input type="submit" value="Accept">
			</c:when>
			<c:otherwise>
				<input type="submit" value="Share With">
			</c:otherwise>
		</c:choose>
		<c:forEach items="${friendsAndNotes}" var="friendtriplet">
			<div class="content-friend">
				<div class="img-friend-content">
					<img class="img-friend" src="${friendtriplet.second.url}"
						alt="Friend Profile Img ${friendtriplet.second.idi}">
				</div>
				<div class="info-friend-content">
					<div class="name-friend">
						<h2>${friendtriplet.first.username}</h2>
					</div>
					<div class="options-friend">
						<input class="icon-img" type="checkbox" name="idu"
							value="${friendtriplet.first.idu}">
					</div>
					<div class="notes-shared-with">
						<h3>Notes Shared</h3>
						<c:forEach items="${friendtriplet.third}" var="note">
							<a href="#">
								<div class="note-from-list">
									<div class="img-note-list">
										<img class="img-note-list" src="../images/Logo.png">
									</div>
									<div class="note-title-list">
										<h2>${note.title}</h2>
									</div>
								</div>
							</a>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:forEach>
		</form>
	</div>
</body>
</html>