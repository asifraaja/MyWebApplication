<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Import classes -->
<%@ page import="student.Student,java.util.List" %>

<html>
	<body>
	<script>
		function pass_rollnum(rollno){
			var rollnum = rollno;
			document.getElementById("rollnum").value=rollnum;
			document.getElementById("searchForm").submit();
		}
	</script>
		<%@include file="search.html" %>
		
		<hr> Student Details are ,
		<table border="1">
			<tr>
				<th>Roll Number</th>
				<th>Name</th>
			</tr>
			<form action="StudentControllerServlet" method="get" id="searchForm">
				<input type="hidden" name="action" value="details">
				<c:forEach items="${students}" var="student">
				    <tr>
				        	<td onclick="pass_rollnum(this.innerText)"><c:out value="${student.rollnum}"/></td>
				        <td><c:out value="${student.fname}"/><c:out value="${student.lname}"/></td>  
				    </tr>
				</c:forEach>
				<input type="hidden" name="rollnum" value="" id="rollnum">
			</form> 
		</table>
	</body>
</html>