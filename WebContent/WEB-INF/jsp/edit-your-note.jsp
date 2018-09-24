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
	<div id="change-data-profile-box">
		<form id="edit-form"
			action="${pageContext.request.contextPath}/notes/EditNoteServlet"
			method="POST">
			<fieldset>
				<c:forEach items="${errors}" var="error">
					<h2>${error}</h2>
				</c:forEach>
				<legend>Note Information</legend>
				<input type="hidden" name="idn" value="${note.idn}"> <label
					for="title-note"></label> <input class="input-modify" type="text"
					id="title-note" name="title-note" placeholder="Title for your Note"
					value="${note.title}" required> <label for="description">Description</label>
				<textarea class="input-modify" type="textarea" rows="8"
					id="description" name="description" required>${note.content}</textarea>

				<input class="input-modify" type="textarea" rows="8" id="labels"
					name="labels"
					placeholder="Please, write your labels with a colon and without spaces. I.E. example,for,list"
					value="<c:forEach items="${labels}" var="label">${label.content},</c:forEach>">

				<label for="note-img">Image</label> 
				<input class="input-modify" type="text" id="note-img" name="url-image" placeholder="Url of your image" value="${note.urlimage}"> 
				
				<label for="color">Select a color for your note</label> 
				<select name="color" id="color">
					<option value=0 <c:if test="${color == 0}">selected</c:if>>No
						Color</option>
					<option value=4 <c:if test="${color == 4}">selected</c:if>>Black</option>
					<option value=2 <c:if test="${color == 2}">selected</c:if>>Blue</option>
					<option value=1 <c:if test="${color == 1}">selected</c:if>>Green</option>
					<option value=3 <c:if test="${color == 3}">selected</c:if>>Pink</option>
				</select>
			</fieldset>
			<c:choose>
				<c:when test="${(empty note) || (newnote == 'true')}">
					<input type="hidden" name="newnote" value="true">
					<input type="submit" value="Save Note">
				</c:when>
				<c:otherwise>
					<input type="hidden" name="newnote" value="false">
					<input type="submit" value="Update Note">
				</c:otherwise>
			</c:choose>
		</form>
	</div>
</body>
</html>