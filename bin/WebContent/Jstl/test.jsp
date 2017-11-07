<!-- Using JSTL library -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Import classes -->
<%@ page import ="java.util.ArrayList,java.util.List,test.Student" %>

<%
	List<Student> students = new ArrayList<Student>();
	students.add(new Student("a","b",true));
	students.add(new Student("b","c",false));
	pageContext.setAttribute("students",students);
%>

<html>
	<body>
	<table border="1">
		<th>FirstName</th>
		<th>LastName</th>
		<th>SpecialDiscount</th>
		<c:forEach var="student" items="${students}" >
				<tr>
					<td>${student.fName}</td>
					<td>${student.lName}</td>
					<c:if test="${student.goldCustomer}">
						<td>10% Discount</td>
					</c:if>
					<c:if test="${not student.goldCustomer}">
						<td>No Discount</td>
					</c:if>
				</tr>
		</c:forEach>
	</table>
	</body>
</html>