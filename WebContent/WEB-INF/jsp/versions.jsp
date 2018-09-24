<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>JustNotes</title>
<link rel="icon" href="${pageContext.request.contextPath}/images/Logo.png">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheet/stylesheet5.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheet/color-classes.css">
</head>
<body>
	<c:import url="/WEB-INF/jsp/logged-header.jsp"></c:import>
	
	<div id="content-notes">
		<c:forEach var="notetriplet" items="${notes}">
			<div id="note${notetriplet.second.idn}" class="note class-${notetriplet.first.color}">
				<div class="note-up">
					<div class="img-note">
						<img class="img-options" src="${notetriplet.second.urlimage}">
					</div>
					<div class="note-title">
						<h2>${notetriplet.second.title}</h2>
					</div>
					<div class="box-options">
						<img class="img-options" src="${pageContext.request.contextPath}/images/box-options.png">
						<div class="dropdown-content">
							<form action="${pageContext.request.contextPath}/notes/VersionsServlet"
								method="POST">
								<input type="hidden" name="action" value="recover"> 
								<input type="hidden" name="idn" value="${notetriplet.second.idn}">
								<input type="hidden" name="timestamp" value="${notetriplet.third[1]}">
								<input class="options-input-submit" type="submit" value="Recover">
							</form>
						</div>
					</div>
				</div>
				<div class="note-description">
					<p>${notetriplet.second.content}</p>
				</div>
				<div class="labels">
				<c:forEach items="${notetriplet.third}" var="data">
					${data}
				</c:forEach>
				</div>
				<div class="icons">
					<c:choose>
						<c:when test="${notetriplet.first.owner == 1}">
							<img alt="Owner Icon" src="${pageContext.request.contextPath}/images/owner.png">
						</c:when>
						<c:otherwise>
							<img alt="Empty Icon" src="${pageContext.request.contextPath}/images/empty.png">
						</c:otherwise>
					</c:choose>
					<c:if test="${notetriplet.first.pinned == 1}">
						<img alt="Pinned Icon" src="${pageContext.request.contextPath}/images/pinned.png">
					</c:if>
					<c:if test="${notetriplet.first.archived == 1}">
						<img alt="Archived Icon" src="${pageContext.request.contextPath}/images/archived.png">
					</c:if>
					<c:if test="${notetriplet.first.intrash == 1}">
						<img alt="Trash Icon" src="${pageContext.request.contextPath}/images/trash.png">
					</c:if>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>