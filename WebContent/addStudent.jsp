<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Import classes -->
<%@ page import="student.Student" %>

<html>
	<body>
		<%@include file="home.html" %>
		<h2> ${success}</h2>
		<hr> Student Details are ,
		<table>
			<tr>
				<th>Name</th>
				<th>Rollnum</th>
				<th>Dept</th>
			</tr>
			<tr>
				<td>${student.fname} ${student.lname}</td>
				<td>${student.rollnum}</td>
				<td>${student.dept}</td>
			</tr>
		</table>
	</body>
</html>
