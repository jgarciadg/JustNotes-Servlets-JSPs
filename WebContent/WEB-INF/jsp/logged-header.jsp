<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
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
				<a href="${pageContext.request.contextPath}/notes/MainServlet">
					<h1>JustNotes</h1>
				</a>
			</div>
			<div id="center-header">
				<form
					action="${pageContext.request.contextPath}/notes/SimpleSearchServlet"
					method="POST">
					<input id="input-search" type="text" name="search-object"
						placeholder="Search Your Note"> <input id="search-submit"
						type="submit" value="Search">
				</form>
			</div>
			<div id="right-header">
				<a
					href="${pageContext.request.contextPath}/notes/NotificationsServlet">
					<div class="header-right-link">
						<c:choose>
							<c:when test="${notifications}">
								<img id="notification-icon"
									src="${pageContext.request.contextPath}/images/notification-icon-ocup.png"
									alt="Profile Image">
							</c:when>
							<c:otherwise>

								<img id="notification-icon"
									src="${pageContext.request.contextPath}/images/notification-icon.png"
									alt="Profile Image">

							</c:otherwise>
						</c:choose>
					</div>

				</a> <a
					href="${pageContext.request.contextPath}/notes/ModifyProfileServlet">
					<div class="header-right-link">
						<img id="profile-img-header" src="${profileimage.url}"
							alt="Profile Image"> ${user.username}
					</div>
				</a>
			</div>
			<div id="container-logout-icon">
				<a href="${pageContext.request.contextPath}/notes/LogoutServlet">
					<img id="logouticon"
					src="https://png.icons8.com/metro/50/000000/shutdown.png">
				</a>
			</div>
		</div>
	</header>
	<nav>
		<ul>
			<li>Notes
				<div class="dropdown-content">
					<a
						href="${pageContext.request.contextPath}/notes/MainServlet?filter=all">All
						Notes</a> <a
						href="${pageContext.request.contextPath}/notes/MainServlet?filter=mynotes">My
						Notes</a> <a
						href="${pageContext.request.contextPath}/notes/MainServlet?filter=pinned">Pinned</a>
					<a
						href="${pageContext.request.contextPath}/notes/MainServlet?filter=sharedwithme">Shared
						With Me</a> <a
						href="${pageContext.request.contextPath}/notes/MainServlet?filter=archived">Archived</a>
					<a
						href="${pageContext.request.contextPath}/notes/MainServlet?filter=intrash">In
						Trash</a>
				</div>
			</li>
			<li>Friends
				<div class="dropdown-content">
					<a href="${pageContext.request.contextPath}/notes/FriendsServlet">My
						Friends</a> <a
						href="${pageContext.request.contextPath}/notes/FriendsServlet">My
						Groups</a>
				</div>
			</li>
			<li>Advanced Search
				<div class="dropdown-content">
					<a
						href="${pageContext.request.contextPath}/notes/AdvancedSearchNoteServlet">Notes</a>
					<a
						href="${pageContext.request.contextPath}/notes/AdvancedSearchUserServlet">Users</a>
				</div>
			</li>
			<c:if test="${filter eq 'intrash'}">
				<li><a
					href="${pageContext.request.contextPath}/notes/TrashServlet?action=emptytrash">Empty
						Trash</a></li>
			</c:if>
		</ul>

		<a id="new-note-button"
			href="${pageContext.request.contextPath}/notes/EditNoteServlet">New
			Note</a>
	</nav>
</body>
</html>