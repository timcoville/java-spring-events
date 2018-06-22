<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles.css">
<title>Events</title>
</head>
<body>
<div id="wrapper">
		<h1>Welcome, <c:out value="${user.firstName }"></c:out></h1>
		
		<div id="inner">
		<a href="/logout"><button >Logout</button></a>
		<p>Here are some events in your state: </p>
		<table id="events">
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Location</th>
				<th>Host</th>
				<th>Action/Status</th>
			</tr>
			<c:forEach items="${userEvents }" var="item">
			
			<tr>
				<td><a href="/events/${item.id}"><c:out value="${item.name }"></c:out></a></td>
				<td><fmt:formatDate pattern = "MMMM d, yyyy"  value="${item.eventDate }" /></td>
				<td><c:out value="${item.city }"></c:out></td>
				<td><c:out value="${item.user.firstName }"></c:out></td>
				<td>
				
				
				<c:choose>
					
					<c:when test="${item.user.id == user.id }">
						<a href="/edit/${item.id}">Edit</a> | 
						<a href="/delete/${item.id}">Delete</a>
					</c:when>
					
					<c:when test="${item.user.id != user.id }">
						<c:set var="joined" value="null" />
						
						<c:forEach items="${item.attendees}" var="userAt">
							<c:if test="${userAt.id == user.id }" >
								<c:set var="joined" value="true" />
							</c:if>
						</c:forEach>
						
						<c:if test="${joined.contains('null') }" >
							<a href="/join/${user.id}/${item.id}">Join</a>
						</c:if>
				
				
						<c:if test="${joined == true}" >
							Joined | <a href="/cancel/${user.id}/${item.id}">Cancel</a>
						</c:if>
					</c:when>
					
				</c:choose>
				
				
				
				
				<c:if test="${item.user.id != user.id}">
					<c:forEach items="${item.attendees}" var="userAt">
						<c:if test="${userAt.id == user.id }" >
							<c:set var="joined" value="true" />
						</c:if> 
					</c:forEach>
				</c:if>
				
				
		 		
				
				
				</td>
			</c:forEach>
			
		</table>
		<hr>
		<p>Here are some events in other states: </p>
		<table id="events">
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Location</th>
				<th>Host</th>
				<th>Action/Status</th>
			</tr>
			<c:forEach items="${userEvents2 }" var="item">
			
			<tr>
				<td><a href="/events/${item.id}"><c:out value="${item.name }"></c:out></a></td>
				<td><fmt:formatDate pattern = "MMMM d, yyyy"  value="${item.eventDate }" /></td>
				<td><c:out value="${item.city }"></c:out></td>
				<td><c:out value="${item.user.firstName }"></c:out></td>
				<td>
				
				
				<c:choose>
					
					<c:when test="${item.user.id == user.id }">
						<a href="/edit/${item.id}">Edit</a> | 
						<a href="/delete/${item.id}">Delete</a>
					</c:when>
					
					<c:when test="${item.user.id != user.id }">
						<c:set var="joined" value="null" />
						
						<c:forEach items="${item.attendees}" var="userAt">
							<c:if test="${userAt.id == user.id }" >
								<c:set var="joined" value="true" />
							</c:if>
						</c:forEach>
						
						<c:if test="${joined.contains('null') }" >
							<a href="/join/${user.id}/${item.id}">Join</a>
						</c:if>
				
				
						<c:if test="${joined == true}" >
							Joined | <a href="/cancel/${user.id}/${item.id}">Cancel</a>
						</c:if>
					</c:when>
					
				</c:choose>
				
				
				
				
				<c:if test="${item.user.id != user.id}">
					<c:forEach items="${item.attendees}" var="userAt">
						<c:if test="${userAt.id == user.id }" >
							<c:set var="joined" value="true" />
						</c:if> 
					</c:forEach>
				</c:if>
				
				
		 		
				
				
				</td>
			</c:forEach>
			
		</table>
		<hr>
		<h2>Create an Event!</h2>
		
		<form:form action="/events" method="post" modelAttribute="event">
			<span class="row">
				<form:label path="name">Name:</form:label>
				<form:input path="name"/>
			</span>
			<span class="row">
				<label>Date:</label>
				<input type="date" name="newEventDate" min="${date}"> 
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
				<input type="submit" class="right"  value="Add Event"/>
			</span>
			<p><form:errors path="event.*"/></p>	
		</form:form>
	</div>
		
		
	

		
		
		

</div>
</body>
</html>