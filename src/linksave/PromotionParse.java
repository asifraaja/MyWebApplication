package linksave;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PromotionParse{
	int id;
	// Promotion details
	String offerId;
	String oId;
	String linkSaveId;
	String startDate;
	String endDate;
	String adjuster;
	
	// Promotion type and store details
	Type type;
	StoreHandler storehandler;

	// elements for building doc
	String currentTag;
	Element rootElement;
	Element root;
	Element cdata;
	Attr attribute;
	Element elem;
	String previousTag;
	
	// Cdata reader 
	StringBuilder xmls;
	StringBuilder CData;
	
	/*@params(input) -- INPUT about promotion
	 * @params(storehandler) -- to access store details
	 * */
	
	public PromotionParse(Type type, StoreHandler storehandler) {
		currentTag = null;
		id = new Random().nextInt(99999);
		offerId = "ls" + Integer.toString(id);
		linkSaveId = Integer.toString(id);
		oId = Integer.toString(id);
		CData = new StringBuilder();
		
		this.storehandler = storehandler;
		this.type = type;
	}
	
	public PromotionParse(String input,StoreHandler storehandler) throws IOException, ParserConfigurationException {
		currentTag = null;
		processInput(input);
		id = new Random().nextInt(99999);
		offerId = "ls" + Integer.toString(id);
		linkSaveId = Integer.toString(id);
		oId = Integer.toString(id);
		
		this.storehandler = storehandler;
		
		CData = new StringBuilder();
	}
	
	// Creating a xml with specific linksave id
	public void setOfferId(String offerId) {
		if(offerId!=null && !offerId.isEmpty() && !offerId.equalsIgnoreCase("")) {
			this.offerId = offerId;
			String temp = offerId.substring(2, offerId.length());
			id = Integer.parseInt(temp);
			linkSaveId = Integer.toString(id);
			oId = Integer.toString(id);
		}
	}

	// Updating the xml with specific linksave id
	public void setIds(String pId, String oId, String lsId) {
		offerId = pId;		// offerId refers to promotion Id
		linkSaveId = lsId;
		this.oId = oId;
	}
	
	/*
	 * INPUT recieved as TypeNum;BuyItems;Prices;Offer1(MdsIds)/OfferN(MdsIds);Quantity
	 * */
	public void processInput(String input) {
		String[] inputs = input.split(";");
		System.out.println("Length"+inputs.length+"input:"+input);
		if(inputs[0].equalsIgnoreCase("TYPE8")) {
			type = new Type8(inputs);
		}else if(inputs[0].equalsIgnoreCase("TYPE15")) {
			type = new Type15(inputs);
		}else if(inputs[0].equalsIgnoreCase("TYPE11")) {
			type = new Type11(inputs);
		}else if(inputs[0].equalsIgnoreCase("TYPE2")) {
			type = new Type2(inputs);
		}
	}
	
	private String getOfferId() {
		return offerId;
	}
	// StartDate Details
	public void setStartDate(String startDate) {
		if(offerId!=null && !offerId.isEmpty() && !offerId.equalsIgnoreCase("")) 
			this.startDate = startDate;
	}
	public String getStartDate() {
		return startDate;
	}
	// EndDate details
	public void setEndDate(String endDate) {
		if(offerId!=null && !offerId.isEmpty() && !offerId.equalsIgnoreCase("")) 
			this.endDate = endDate;
	}
	public String getEndDate() {
		return endDate;
	}
	// Creates elements
	private Element create(String tag,Document doc) {
		Element child = doc.createElement(tag);
		return child;
	}

	public void addStores(Element parent, Document doc) {
		List<String> storeList = new ArrayList<String>();
		storeList = storehandler.getStores();
		// Multiple stores
		for(String store : storeList) {			
			Element str = create("store",doc);
			parent.appendChild(str);
			
			Element child = create("promotionId",doc);
			child.appendChild(doc.createTextNode(offerId));
			str.appendChild(child);
			
			child = create("storeRuleId",doc);
			child.appendChild(doc.createTextNode(storehandler.getStoreId()));
			str.appendChild(child);
			
			child = create("storeNbr",doc);
			child.appendChild(doc.createTextNode(store));
			str.appendChild(child);
		}
	}
	
	public void addOfferGroupItems(Element parent, Document doc) {
		// Multiple offers
		type.reset(-1);
		for(int i=0;i<type.getOfferCount();i++) {
			List<String> skuList = type.getOfferSkuList();
			for(String sku : skuList) {
				Element group = create("offerGroupItem",doc);
				parent.appendChild(group);

				Element child = create("promotionId",doc);
				child.appendChild(doc.createTextNode(getOfferId()));
				group.appendChild(child);

				child = create("offerGroupId",doc);
				System.out.println("OfferId"+type.getOfferId());
				child.appendChild(doc.createTextNode(type.getOfferId()));
				group.appendChild(child);

				child = create("mdsFamId",doc);
				child.appendChild(doc.createTextNode(sku));
				group.appendChild(child);
				
				child = create("include",doc);
				child.appendChild(doc.createTextNode("true"));
				group.appendChild(child);
			}
		}
		type.reset(-1);
	}
	
	public void addOfferGroupInfos(Element parent, Document doc) {
		// multiple offers
		for(int i=0;i<type.getOfferCount();i++) {
			type.reset(i);
			Element group = create("offerGroupInfo",doc);
			parent.appendChild(group);

			Element child = create("promotionId",doc);
			child.appendChild(doc.createTextNode(getOfferId()));
			group.appendChild(child);

			child = create("offerGroupId",doc);
			child.appendChild(doc.createTextNode(type.getOfferId()));
			group.appendChild(child);

			child = create("offerPurchaseQty",doc);
			child.appendChild(doc.createTextNode(type.getQuantity()));
			group.appendChild(child);

			child = create("forPriceOfQty",doc);
			child.appendChild(doc.createTextNode("-1"));
			group.appendChild(child);
		}
		type.reset(-1);
	}
	// Creates tags for elements 
	public Element handle(String tag,Document doc,Element parent) {
		Element child = null;
		if(tag.equalsIgnoreCase("promotions")) {
			child = doc.createElement(tag);
			parent.appendChild(child);
			root = child;
		}else if(tag.equalsIgnoreCase("promotion")) {
			child = doc.createElement(tag);
			root.appendChild(child);
			root = child;
		}else if(tag.equalsIgnoreCase("stores")) {
			child = doc.createElement(tag);
			root.appendChild(child);
			addStores(child,doc);
		}else if(tag.equalsIgnoreCase("offerGroupItems")) {
			child = doc.createElement(tag);
			root.appendChild(child);
			addOfferGroupItems(child,doc);
		}else if(tag.equalsIgnoreCase("offerGroupInfos")) {
			child = doc.createElement(tag);
			root.appendChild(child);
			addOfferGroupInfos(child,doc);
		}else if(tag.equalsIgnoreCase("pmdlRule")){
			child = doc.createElement(tag);
			root.appendChild(child);
			cdata = child;
		}else if(tag.equalsIgnoreCase("pricingCalculatorService")) {
			child = doc.createElement(tag);
			root.appendChild(child);
			cdata = child;
		}else if(tag.equalsIgnoreCase("storeRuleId") && previousTag.equalsIgnoreCase("offerStatusCode")) {
			child = doc.createElement(tag);
			root.appendChild(child);
		}else {
			// inner Tags not created
			if(!tag.equalsIgnoreCase("forPriceOfQty") && !tag.equalsIgnoreCase("offerPurchaseQty") 
					&& !tag.equalsIgnoreCase("offerGroupId") && !tag.equalsIgnoreCase("promotionId") 
					&& !tag.equalsIgnoreCase("offerGroupInfo") && !tag.equalsIgnoreCase("include") 
					&& !tag.equalsIgnoreCase("mdsFamId") && !tag.equalsIgnoreCase("offerGroupItem") 
					&& !tag.equalsIgnoreCase("storeNbr") && !tag.equalsIgnoreCase("storeRuleId") 
					&& !tag.equalsIgnoreCase("store")) {
				child = doc.createElement(tag);
				root.appendChild(child);
			}else {
				child = null;
			}
		}
		previousTag = tag;
		return child;
	}
	
	// Setting data to each tag
	public void setValues(String tag,Document doc,String str,Element ele) {
		if(tag.equalsIgnoreCase("id")) {
			ele.appendChild(doc.createTextNode(getOfferId()));	// linksave Id along with 'ls'
			
		}else if(tag.equalsIgnoreCase("displayName")) {
			ele.appendChild(doc.createTextNode(type.getOfferName()));
			
		}else if(tag.equalsIgnoreCase("description")) {
			ele.appendChild(doc.createTextNode(type.getTypeName()));
			
		}else if(tag.equalsIgnoreCase("type")) {
			ele.appendChild(doc.createTextNode(type.getTypeNumber()));
			
		}else if(tag.equalsIgnoreCase("offerId")) {
			ele.appendChild(doc.createTextNode(oId));	// linksave Number only
			
		}else if(tag.equalsIgnoreCase("linkSaveId")) {
			ele.appendChild(doc.createTextNode(linkSaveId));	// linksave Id along with 'ls'
			
		}else if(tag.equalsIgnoreCase("offerTypeCode")) {
			ele.appendChild(doc.createTextNode(type.getOfferType()));
			
		}else if(tag.equalsIgnoreCase("storeRuleId") || tag.equalsIgnoreCase("storeRule")) {
			str = storehandler.getStoreId();
			ele.appendChild(doc.createTextNode(str));
			
		}else if(tag.equalsIgnoreCase("pmdlRule")){
			String curr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"+
					"<!DOCTYPE pricing-model  SYSTEM \"dynamosystemresource:/atg/dtds/pmdl/pmdl_1.0.dtd\">"+
					CDataParser.parse(str,type.getQuantity(),getOfferId(),type.getOfferType())
					;
				
			Node data = doc.createCDATASection(curr);
			cdata.appendChild(data);
			
		}else if(tag.equalsIgnoreCase("pricingCalculatorService")) {
			String curr = str;
			Node data = doc.createCDATASection(curr);
			cdata.appendChild(data);
			
		}else if(tag.equalsIgnoreCase("beginUsable")){
			if(getStartDate() == null)
				ele.appendChild(doc.createTextNode(str));
			else 
				ele.appendChild(doc.createTextNode(getStartDate()));
			
		}else if(tag.equalsIgnoreCase("endUsable")){
			if(getEndDate() == null)
				ele.appendChild(doc.createTextNode(str));
			else 
				ele.appendChild(doc.createTextNode(getEndDate()));
			
		}else if(tag.equalsIgnoreCase("creationDate")){
			str= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			ele.appendChild(doc.createTextNode(str));
			
		}else if(tag.equalsIgnoreCase("offerDiscPct")) {	// Only for Type 8
			if(type.getOfferPercentage() != null)
				str = type.getOfferPercentage();
			ele.appendChild(doc.createTextNode(str));
			
		}else if(tag.equalsIgnoreCase("discountType")) {
			ele.appendChild(doc.createTextNode(type.getDiscountType()));
			
		}else if(tag.equalsIgnoreCase("offerPriceAmt")) {
			if(type.getOfferType().equalsIgnoreCase("11")) {
				str = type.getAdjuster();
			}
			else {
				if(type.getPrice() != null)
					str = type.getPrice();
				else
					str = "0";
			}
			ele.appendChild(doc.createTextNode(str));
			
		}else if(tag.equalsIgnoreCase("adjuster")) {
			System.out.println("Adjuster"+type.getAdjuster());
			if(type.getAdjuster()!=null)
				str = type.getAdjuster();
			ele.appendChild(doc.createTextNode(str));
			
		}else if(tag.equalsIgnoreCase("customerDiscAmt")) {	// same as OfferPriceAmt for type 11
			if(type.getOfferType().equalsIgnoreCase("11")) {
				str = type.getAdjuster();
			}
			ele.appendChild(doc.createTextNode(str));
			
		}else {
			ele.appendChild(doc.createTextNode(str));
			
		}
	}
	
	public void endValue(String tag) {
		if(tag.equalsIgnoreCase("pmdlRule")) {
			System.out.println(CData.toString());
		}
	}
}