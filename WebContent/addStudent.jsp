<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Import classes -->
<%@ page import="student.Student" %>

<html>
	<body>
		<%@include file="home.html" %>
		<% Student student = (Student) session.getAttribute("student"); %>
		<h2> ${success}</h2>
		<hr> Student Details are ,
		<table border="1">
			<tr>
				<th>Roll Number</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Department</th>
				<th>Mobile Number</th>
			</tr>
			<tr>
				<td><%= student.getRollnum() %></td>
				<td><%= student.getFname() %></td>
				<td><%= student.getLname() %></td>
				<td><%= student.getDept() %></td>
				<td><%= student.getMobileNum() %></td> 
			</tr>
		</table>
	</body>
</html>
