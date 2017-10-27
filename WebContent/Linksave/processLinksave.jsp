<%@ page import="file.FileGenerator,linksave.*,java.util.Random" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
	StringBuilder input = new StringBuilder();
	FileGenerator fgen = new FileGenerator();
	String lsType = request.getParameter("lsType");
	String stores = request.getParameter("stores");
	String skus = request.getParameter("skus");
	String price,qty;
	
	out.println(lsType);
	Type type = null;
	String lstype = lsType.toLowerCase();
	System.out.println("Type is "+lstype);
	switch(lstype){
		case "type2":
			price = request.getParameter("price");
			qty = request.getParameter("qty");
			type = new Type2(lsType,price,skus,qty);
		break;
		case "type8":
			price = request.getParameter("price");
			qty = "1";
			type = new Type8(lstype,price,skus,qty);
		break;	
		case "type11":
			price = request.getParameter("price");
			String priceA = request.getParameter("priceA");
			String priceB = request.getParameter("priceB");
			qty = request.getParameter("qty");
			String skus2 = request.getParameter("skus2");
			skus = skus + "/" + skus2;
			price = price + "," + priceA + "," + priceB;
			type = new Type11(lsType,price,skus,qty);
		break;
		case "type15":
			price = request.getParameter("price");
			qty = request.getParameter("qty");
			type = new Type15(lsType,price,skus,qty);
		break;
	}
	StoreHandler storehandler = new StoreHandler(stores);
	PromotionParse promotionhandler = new PromotionParse(type,storehandler);
	Handler handler = new Handler(promotionhandler,storehandler);
	handler.parse();
	handler.transformDocToXML();
%>
<html>
	<body>
		
	</body>
</html>