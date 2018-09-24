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
		<form
			action="${pageContext.request.contextPath}/notes/AdvancedSearchNoteServlet"
			method="POST">
			<fieldset>
				<legend>Search Information</legend>
				<label for="title-search">Title</label> <input class="input-modify"
					id="title-search" name="title-search" placeholder="Title of Note"
					type="text"> <label for="description-search">Description</label>
				<input class="input-modify" id="description-search"
					name="description-search" placeholder="Description of Note"
					type="text">
				<fieldset>
					<legend>Labels</legend>
					<input class="input-modify" type="textarea" rows="8" id="labels"
						name="labels"
						placeholder="Please, write your labels with a colon and without spaces. I.E. example,for,list">
				</fieldset>
				<fieldset>
					<legend>User Type</legend>
					<div class="type-user">
						<input id="typeuser1" class="input-checkbox" name="typeuser"
							type="radio" value="owner" checked> <label
							for="typeuser1" class="user-type-checkbox">Owner</label>
					</div>
					<div class="type-user">
						<input id="typeuser2" class="input-checkbox" name="typeuser"
							type="radio" value="friends"> <label for="typeuser2">Friends</label>
					</div>
				</fieldset>
				<fieldset>
					<legend>Colors</legend>
					<div class="type-color">
						<input id="colorblack" class="input-checkbox" type="checkbox"
							name="color-searched" value="0"> <label for="colorblack">No
							Color</label>
					</div>
					<div class="type-color">
						<input id="nocolor" class="input-checkbox" type="checkbox"
							name="color-searched" value="4"> <label for="nocolor">Black</label>
					</div>
					<div class="type-color">
						<input id="colorblue" class="input-checkbox" type="checkbox"
							name="color-searched" value="2"> <label for="colorblue">Blue</label>
					</div>
					<div class="type-color">
						<input id="colorgreen" class="input-checkbox" type="checkbox"
							name="color-searched" value="1"> <label for="colorgreen">Green</label>
					</div>
					<div class="type-color">
						<input id="colorpink" class="input-checkbox" type="checkbox"
							name="color-searched" value="3"> <label for="colorpink">Pink</label>
					</div>
				</fieldset>
				<fieldset>
					<legend>Note Type</legend>
					<div class="type-color">
						<input id="pinned" class="input-checkbox" type="checkbox"
							name="type-note" value="pinned"> <label for="pinned">Pinned</label>
					</div>
					<div class="type-color">
						<input id="archived" class="input-checkbox" type="checkbox"
							name="type-note" value="archived"> <label for="archived">Archived</label>
					</div>
					<div class="type-color">
						<input id="shared" class="input-checkbox" type="checkbox"
							name="type-note" value="shared"> <label for="shared">Shared</label>
					</div>
					<div class="type-color">
						<input id="intrash" class="input-checkbox" type="checkbox"
							name="type-note" value="intrash"> <label for="intrash">In
							Trash</label>
					</div>
				</fieldset>
			</fieldset>
			<input type="submit" value="Search">
		</form>
	</div>
</body>
</html>