package linksave;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StoreHandler{
	Store store;
	Element root;
	Attr attribute;
	StringBuilder xml;
	
	/*
	 * Input = StoreRuleId;storenum1,storenum2,storenumN
	 * */
	public StoreHandler(String input) {
		store = new Store();
		processInput(input);				
		xml = new StringBuilder();
	}
		
	// processes and puts the data into stores
	public void processInput(String input) {
		store.setStoreId(Integer.toString(new Random().nextInt(99999)));
		store.setStoreNumber(input);
	}
	
	public List<String> getStores() {
		return store.getStores();
	}
	
	public String getStoreId() {
		return store.getStoreId();
	}
	
	public Element addStoreRule(Document doc) {
		Element storeRule = doc.createElement("store_rule");
		return storeRule;
	}
	
	// Gets the rule id from store and adds it
	public Element addRule(Document doc) {
		Element rule = doc.createElement("rule");
		Attr attr = doc.createAttribute("id");
		attr.setValue(getStoreId());
		rule.setAttributeNode(attr);
		return rule;
	}
	
	// Gets list of all stores and creates tags and enters data into it
	public void addStores(Element parent,Document doc) {
		List<String> stores = new ArrayList<String>();
		stores = getStores();
		for(String store : stores) {
			Element storeElem = doc.createElement("store_nbr");
			storeElem.appendChild(doc.createTextNode(store));
			parent.appendChild(storeElem);
		}
	}
	
	/*
	 * Linksave
	 * 	|________>Store_Rule
	 * 				|__________>Rule
	 * 							 |______> Store_Nbr
	 * save the parent and pass it to next upcoming tag			
	 * */
	public void handle(String tag, Document doc,Element parent) {
		if(tag.equalsIgnoreCase("store_rule")) {
			Element child = addStoreRule(doc);
			parent.appendChild(child);
			root = child; 								// storing parent tag
		}else if(tag.equalsIgnoreCase("rule")) {
			Element child = addRule(doc);
			root.appendChild(child);
			root = child;								// storing parent tag
		}else if(tag.equalsIgnoreCase("store_nbr")) {
			addStores(root,doc);							// appending 
		}	
	}
	
	public void end(String tag) {
	}
}
