<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Import classes -->
<%@ page import="student.Student,java.util.List" %>

<html>
	<body>
		<%
			int rollnum = Integer.parseInt(request.getParameter("rollnum"));
		%>
		<form action="StudentControllerServlet" method="get">
			<input type="hidden" name="action" value="remove"/>
			<input type="hidden" name="rollnum" value=<%=rollnum %>>
			<input type="submit" value="Remove">
		</form>
		
		<form action="StudentControllerServlet" method="get">
			<input type="hidden" name="action" value="update"/>
			<input type="hidden" name="rollnum" value=<%=rollnum %>>
			<input type="submit" value="Update">
		</form>
	</body>
</html>