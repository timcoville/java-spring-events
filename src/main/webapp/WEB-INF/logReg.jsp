<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles.css">
<title>Welcome</title>
</head>
<body>
	<div id="wrapper">
		<h1>Welcome</h1>
		<div id="register">
		<h2>Register</h2>
		<form:form action="/register" method="post" modelAttribute="user">
			<span class="row">
				<form:label path="firstName">First Name:</form:label>
				<form:input path="firstName"/>
			</span>
			<span class="row">
				<form:label path="lastName">Last Name:</form:label>
				<form:input path="lastName"/>
			</span>
			<span class="row">
				<form:label path="email">Email:</form:label>
				<form:input path="email"/>
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
				<form:label path="password">Password:</form:label>
				<form:password path="password"/>
			</span>
			<span class="row">
				<form:label path="passwordConfirmation">PW Confirm:</form:label>
				<form:password path="passwordConfirmation"/>
			</span>
			<span class="row">
				<input type="submit" class="right"  value="Register"/>
			</span>				
		<p><form:errors path="user.*"/></p>	
		</form:form>
		
		</div>
		<div id="login">
		<h2>Login</h2>
		<form:form action="/login" method="post" modelAttribute="user">
			<span class="row">
				<form:label path="email">Email:</form:label>
				<form:input path="email"/>
			</span>
			<span class="row">
				<form:label path="password">Password:</form:label>
				<form:password path="password"/>
			</span>

			<span class="row">
				<input type="submit" class="right"  value="Login"/>
			</span>				
		<p><c:out value="${loginError}"></c:out></p>
		</form:form>
		
	
		</div>
	</div>
</body>
</html>