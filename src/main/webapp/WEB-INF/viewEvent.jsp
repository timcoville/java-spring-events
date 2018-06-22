<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/styles.css">
<title> <c:out value="${event.name}"/> </title>
</head>
<body>
<div id="wrapper">
	<h1><c:out value="${event.name}"/></h1>
	<div id="register">
	<p>Host: <c:out value="${event.user.firstName} ${event.user.lastName }" /></p>
	<p>Date: <fmt:formatDate pattern = "MMMM d, yyyy"  value="${event.eventDate }" />
	<p>Location: <c:out value="${event.city}, ${event.state }" /></p>
	<p>Number of attendees: <c:out value="${event.attendees.size() }" /></p>
	
	<table id="events">
		<tr>
			<th>Name</th>
			<th>Location</th>
		</tr>
		<c:forEach items="${event.attendees}" var="attendee">
		<tr>
			<td> <c:out value="${attendee.firstName} ${attendee.lastName }" /></td>
			<td> <c:out value="${attendee.city}" /></td>
		</c:forEach>
	</table>
	<br>
	<a href="/events"><button>Go Back</button></a>
	
	</div>
	
	<div id="login">
	<h2>Message Wall</h2>
	
	<div id="wall">
	
	<c:set var="msgNum" value="${event.messages.size() }" />
	<c:set var="msgList" value="${event.messages}" />

	<c:forEach var="i" begin="1" end="${msgNum}" step="1">
		<c:set var="message" value="${msgList[msgNum-i]}" />
		<p><c:out value="${message.user.firstName} ${message.user.lastName } says: ${message.message}"/></p>
	</c:forEach>
	</div>
	
	<h3>Add Comment:</h3>

	
	<form action="/messages" method="post">
		<span class="row"><textarea name="message" rows="10" cols="60"></textarea></span>
		<input type="hidden" name="eventID" value="${event.id }">
		<input type="hidden" name="userID" value="${user.id }">
		<input type="submit" value="Submit">
	</form>
	

	
	</div>
	
	


</div>
</body>
</html>