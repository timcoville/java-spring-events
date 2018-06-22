<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../styles.css">
<title>Edit Event</title>
</head>
<body>

	<div id="wrapper">
	<h1><c:out value="${event.name}"/></h1>
	<div id="inner">
	<a href="/events"><button>Go Back</button></a>
	<br>
	<br>
	
	
		<form:form action="/update/${event.id}" method="post" modelAttribute="event">
				
				<span class="row">
					<form:label path="name">Name:</form:label>
					<form:input path="name"/>
				</span>
				<span class="row">
					<label>Date:</label>
					<input type="date" name="newEventDate" min="${date}" value="${currDate }"> 
	
				</span>
				<span class="row">
					<form:label path="city">Location:</form:label>
					<form:input path="city"/>
					<form:select path="state">
						<form:option value="AZ">AZ</form:option>
						<form:option value="CA">CA</form:option>
						<form:option value="NV">NV</form:option>
						<form:option value="OR">OR</form:option>
						<form:option value="WA">WA</form:option>
					</form:select>
				</span>
				<span class="row">
					<input type="submit" class="right"  value="Update"/>
				</span>
				<p><form:errors path="event.*"/></p>	
			</form:form>
	</div>
	</div>
</body>
</html>