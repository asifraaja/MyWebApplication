<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Import classes -->
<%@ page import="student.Student,java.util.List,student.Address" %>

<html>
	<body>
		<%@include file="search.html" %>
		<%
			String a1s, a2s;
			Student s = (Student)session.getAttribute("student");
			Address a1 = s.getAddress("HOME");
			Address a2 = s.getAddress("OFFICE");
			if(a1 == null)
				a1s = "null";
			else
				a1s = a1.toString();
			if(a2 == null)
				a2s = "null";
			else
				a2s = a2.toString();
			System.out.println(a2s);
			
		%>
		
		<hr> Student Details are ,
		<table border = "1">
			<tr>
				<td>Name</td>
				<td>${student.fname } ${student.lname }</td>
			</tr>
			<tr>
				<td>Roll Number</td>
				<td>${student.rollnum }</td>
			</tr>
			<tr>
				<td>Gender</td>
				<td>${student.gender }</td>
			</tr>
			<tr>
				<td>Department</td>
				<td>${student.dept }</td>
			</tr>
			<tr>
				<td>MobileNumber</td>
				<td>${student.mobileNum }</td>
			</tr>
			<tr>
				<td>Address1</td>
				<td><%=a1s %></td>
			</tr>
			<tr>
				<td>Address2</td>
				<td><%=a2s %></td>
			</tr>
		</table>
		<%@include file="removeStudent.jsp" %>
	</body>
</html>