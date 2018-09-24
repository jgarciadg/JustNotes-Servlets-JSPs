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

	<div id="content-friends">

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
						<form
							action="${pageContext.request.contextPath}/notes/SendNotificationServlet"
							method="POST">
							<input type="hidden" name="idfriend"
								value="${friendtriplet.first.idu}"> <input
								class="icon-img" type="image"
								src="${pageContext.request.contextPath}/images/add_friend.png"
								alt="Submit Form" />
						</form>

						<form
							action="${pageContext.request.contextPath}/notes/DeleteFriendServlet"
							method="POST">
							<input type="hidden" name="idfriend"
								value="${friendtriplet.first.idu}"> <input
								class="icon-img" type="image"
								src="${pageContext.request.contextPath}/images/delete_friend.png"
								alt="Submit Form" />
						</form>
					</div>
					<div class="notes-shared-with">
						<h3>Notes Shared</h3>
						<c:forEach items="${friendtriplet.third}" var="note">
							<a
								href="${pageContext.request.contextPath}/notes/MainServlet?filter=onlyone&idn=${note.idn}">
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
	</div>

</body>
</html>