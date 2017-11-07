<%@ page import="java.util.ArrayList" %>
<html>
	<body>

	<%! 
		ArrayList<String> list = new ArrayList<String>();
		String item; 
	%>
	
	<%
		item = (String) request.getParameter("item");
		out.println(item);
		list.add(item);
	%>
	
	<%
		for(String item : list){
			out.println(item + "<br");
		}
	%>
	
	
	</body>
</html>