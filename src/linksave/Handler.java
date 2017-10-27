package linksave;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Handler extends DefaultHandler{
	String currentTag;
	Map<String,Boolean> tagsMap;
	String fileLoc;
	StringBuilder CData;
	String skusAdded;
	
	StoreHandler storehandler;
	PromotionParse promotionhandler;
	
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	Element rootElement;
	boolean child;
	Element ele;
	String previousTag;
	
	public Handler(PromotionParse phandler, StoreHandler shandler) throws ParserConfigurationException {
		promotionhandler = phandler;
		storehandler = shandler;
		
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.newDocument();
		tagsMap = new HashMap<String,Boolean>();
		CData = new StringBuilder();
	}
	
	public void parse() {
		File inputFile = new File("/Users/m0a00pf/Documents/Docs/General.xml");				// This is generic xml
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			saxParser.parse(inputFile,this); 
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void transformDocToXML() throws TransformerException {
		String Output = "/Users/m0a00pf/Documents/Docs/"+promotionhandler.offerId+".xml";

		//Output = fileLoc + "Output\\"+handler.promotionhandler.offerId+".xml";
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
 		Transformer transformer = transformerFactory.newTransformer();
 		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
 		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
 		
 		DOMSource source = new DOMSource(doc);
 		StreamResult result = new StreamResult(new File(Output));
 		transformer.transform(source,result);
	}
	
	@Override
	   public void startElement(
	      String uri, String localName, String qName, Attributes attributes)
	      throws SAXException {
		currentTag = qName;
		if(!tagsMap.containsKey(currentTag))
			tagsMap.put(currentTag, false);		// set currentTag to false so to maintain one to one mapping
		System.out.println(currentTag + child);
		if(currentTag.equalsIgnoreCase("linksave")) {		// Root Element creation
			rootElement = doc.createElement("linksave");
			doc.appendChild(rootElement);
			storehandler.xml.append("<linksave>\n");
			System.out.println("Root element:"+rootElement);
		}else if(currentTag.equalsIgnoreCase("store_rule")) {		// Handling stores in xml
			child = false;
		}else if(currentTag.equalsIgnoreCase("promotions")) {		// Handling promotion in xml
			child = true;
		}
		if(child) {
			if(!tagsMap.get(currentTag)) {
				ele = promotionhandler.handle(currentTag,doc,rootElement);
				tagsMap.put(currentTag, true);
			}
		}else {
			if(!currentTag.equalsIgnoreCase("linksave")) {
				storehandler.handle(currentTag,doc,rootElement);
			}
		}
	}
	
	@Override
	   public void characters(char ch[], int start, int length) throws SAXException {
		String tag = currentTag;
		// Reads all tags except the inner tags given below
		if(child && tagsMap.get(tag)) {
			if(!tag.equalsIgnoreCase("forPriceOfQty") && !tag.equalsIgnoreCase("offerPurchaseQty") 
					&& !tag.equalsIgnoreCase("offerGroupId") && !tag.equalsIgnoreCase("promotionId") 
					&& !tag.equalsIgnoreCase("offerGroupInfo") && !tag.equalsIgnoreCase("include") 
					&& !tag.equalsIgnoreCase("mdsFamId") && !tag.equalsIgnoreCase("offerGroupItem") 
					&& !tag.equalsIgnoreCase("storeNbr") && !tag.equalsIgnoreCase("storeRuleId") 
					&& !tag.equalsIgnoreCase("store")) {
				if(ele != null) {
					String str = new String(ch,start,length);
					if(tag.equalsIgnoreCase("pmdlRule")) {
						CData.append(str);
					}
					else {
						if(str != null && !str.equals(null)) {
							System.out.println("After reading characters");
							promotionhandler.setValues(currentTag,doc,str,ele);		// sets value for that tag
						}
					}
				}
			}
			// StoreRuleId is also specified as outer tag. Create tag only if previous tag is OfferStatusCode
			else if(tag.equalsIgnoreCase("storeRuleId") && previousTag.equalsIgnoreCase("offerStatusCode")) {
				if(ele != null) {
					String str = new String(ch,start,length);
					if(str != null && !str.equals(null)) {
						System.out.println("After reading characters");
						promotionhandler.setValues(currentTag,doc,str,ele);		// sets value for that tag
					}
				}
			}
		}
	}
	
	@Override
	   public void endElement(
	      String uri, String localName, String qName)
	      throws SAXException {
		previousTag = qName;
		String tag = qName;
		System.out.println(tag);
		if(tag.equalsIgnoreCase("pmdlRule")) {
			promotionhandler.setValues(currentTag,doc,CData.toString(),ele);
		}
		if(!tag.equalsIgnoreCase("forPriceOfQty") && !tag.equalsIgnoreCase("offerPurchaseQty") 
				&& !tag.equalsIgnoreCase("offerGroupId") && !tag.equalsIgnoreCase("promotionId") 
				&& !tag.equalsIgnoreCase("offerGroupInfo") && !tag.equalsIgnoreCase("include") 
				&& !tag.equalsIgnoreCase("mdsFamId") && !tag.equalsIgnoreCase("offerGroupItem") 
				&& !tag.equalsIgnoreCase("storeNbr") && !tag.equalsIgnoreCase("storeRuleId") 
				&& !tag.equalsIgnoreCase("store") && child) {
			promotionhandler.endValue(tag);
			tagsMap.put(tag, false);
		}
		if(tag.equalsIgnoreCase("rule") || tag.equalsIgnoreCase("store_rule")) {
			storehandler.end(tag);
		}
	}
}

