<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheet/color-classes.css">
</head>
<body>
	<c:import url="/WEB-INF/jsp/logged-header.jsp"></c:import>
	<!-- 	Extension sin completar -->
	<div id="filter-data">
		<form
			action="${pageContext.request.contextPath}/notes/FilterDataServlet"
			method="GET">
			<fieldset>
				<legend>Colors</legend>
				<c:forEach items="${mostfrequentcolors}" var="color">
					<div class="checkbox-filter-data label-content">
						<label for="${color.first}"> <input class="input-checkbox"
							type="checkbox" name="color" value="${color.first}"
							<c:forEach items="${colorsselected}" var="colorselected">
								<c:if test="${colorselected eq color.second}">checked</c:if>
							</c:forEach>>
							${color.second}
						</label>
					</div>
				</c:forEach>
			</fieldset>

			<fieldset>
				<legend>Labels</legend>
				<c:forEach items="${mostfrequentlabels}" var="label">
					<div class="checkbox-filter-data label-content">
						<input class="input-checkbox" type="checkbox" name="labels"
							value="${label}"> <label for="label1">${label}</label>
					</div>
				</c:forEach>
			</fieldset>
			<input type="submit" value="Filter">
		</form>
	</div>

	<div id="content-notes-list">
		<c:forEach var="notetriplet" items="${notes}">
			<div class="note-from-list class-${notetriplet.first.color}">
				<a href="#note${notetriplet.second.idn}">

					<div class="img-note-list">
						<img class="img-note-list"
							src="${notetriplet.second.urlimage}">
					</div>
					<div class="note-title-list">
						<h2>${notetriplet.second.title}</h2>
					</div>

				</a>
			</div>
		</c:forEach>
	</div>

	<div id="content-notes">
		<c:forEach var="notetriplet" items="${notes}">
			<div id="note${notetriplet.second.idn}"
				class="note class-${notetriplet.first.color}">
				<div class="note-up">
					<div class="img-note">
						<img class="img-options"
							src="${notetriplet.second.urlimage}">
					</div>
					<div class="note-title">
						<h2>${notetriplet.second.title}</h2>
					</div>
					<div class="box-options">
						<img class="img-options"
							src="${pageContext.request.contextPath}/images/box-options.png">
						<div class="dropdown-content">
							<a
								href="${pageContext.request.contextPath}/notes/EditNoteServlet?idn=${notetriplet.second.idn}">Edit</a>
							<a
								href="${pageContext.request.contextPath}/notes/ShareNoteServlet?idn=${notetriplet.second.idn}">Share</a>
							<c:choose>
								<c:when test="${notetriplet.first.pinned == 0}">
									<form
										action="${pageContext.request.contextPath}/notes/PinNoteServlet"
										method="POST">
										<input type="hidden" name="pin" value="true"> <input
											type="hidden" name="idn" value="${notetriplet.second.idn}">
										<input class="options-input-submit" type="submit" value="Pin">
									</form>
								</c:when>
								<c:otherwise>
									<form
										action="${pageContext.request.contextPath}/notes/PinNoteServlet"
										method="POST">
										<input type="hidden" name="pin" value="false"> <input
											type="hidden" name="idn" value="${notetriplet.second.idn}">
										<input class="options-input-submit" type="submit"
											value="UnPin">
									</form>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${notetriplet.first.intrash == 0}">
									<form
										action="${pageContext.request.contextPath}/notes/TrashServlet"
										method="POST">
										<input type="hidden" name="action" value="totrash"> <input
											type="hidden" name="idn" value="${notetriplet.second.idn}">
										<input class="options-input-submit" type="submit"
											value="Delete">
									</form>
								</c:when>
								<c:otherwise>
									<form
										action="${pageContext.request.contextPath}/notes/TrashServlet"
										method="POST">
										<input type="hidden" name="action" value="delete"> <input
											type="hidden" name="idn" value="${notetriplet.second.idn}">
										<input class="options-input-submit" type="submit"
											value="Delete">
									</form>
									<form
										action="${pageContext.request.contextPath}/notes/TrashServlet"
										method="POST">
										<input type="hidden" name="action" value="recover"> <input
											type="hidden" name="idn" value="${notetriplet.second.idn}">
										<input class="options-input-submit" type="submit"
											value="Recover">
									</form>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${notetriplet.first.archived == 0}">
									<a
										href="${pageContext.request.contextPath}/notes/ArchiveNoteServlet?idn=${notetriplet.second.idn}&archive=true">Archive</a>
								</c:when>
								<c:otherwise>
									<a
										href="${pageContext.request.contextPath}/notes/ArchiveNoteServlet?idn=${notetriplet.second.idn}&archive=false">DisArchive</a>
								</c:otherwise>
							</c:choose>

							<a href="${pageContext.request.contextPath}/notes/VersionsServlet?idn=${notetriplet.second.idn}">Versions</a>
						</div>
					</div>
				</div>
				<div class="note-description">
					<p>${notetriplet.second.content}</p>
				</div>
				<div class="labels">
					<c:forEach items="${notetriplet.third}" var="label">
						${label.content},
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