package linksave;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CDataParser extends DefaultHandler{
	Element rootElement;
	Element ele;
	Attr attr;
	String currentTag;
	static String quantity;
	static String promotionId;
	static StringBuilder xml;
	static Map<String,Boolean> tags;
	static String currType;
	
	@Override
	public void startElement(
			String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
		currentTag = qName;
		if(!tags.containsKey(currentTag))
			tags.put(currentTag, false);
		
		if(!tags.get(currentTag)) {
			tags.put(currentTag, true);
			xml.append("<");
			xml.append(currentTag);

			if(currentTag.equalsIgnoreCase("next") || currentTag.equalsIgnoreCase("up-to-and-including")) {
				for(int i=0;i<attributes.getLength();i++) {
					if(attributes.getQName(i).equalsIgnoreCase("number")) {
						xml.append(" number=\"");
						xml.append(quantity);
						xml.append("\"");
					}else {
						xml.append(" ");xml.append(attributes.getQName(i));
						xml.append(" =\""); xml.append(attributes.getValue(i));
						xml.append("\"");
					}
				}
			}
			xml.append(">");
		}
	}
	
	@Override
	   public void characters(char ch[], int start, int length) throws SAXException {
		if(tags.get(currentTag)) {
			if(currentTag.equalsIgnoreCase("string-value")) {		// setting promotionId to <string-value>
				xml.append(promotionId);
			}else if(currentTag.equalsIgnoreCase("value")) {		// setting data for <value>
				if(!currType.equalsIgnoreCase("11")) {			// if type 2, 8 or 15 -- set it to Group1
					String str = new String(ch,start,length);
					if(str.equalsIgnoreCase("item.auxiliaryData.catalogRef.offerGroup2")) {
						str = "item.auxiliaryData.catalogRef.offerGroup1";
						xml.append(str);
						
					}else {
						xml.append(new String(ch,start,length));
					}
				}else {			// else if type 11 -- set it to Group2
					xml.append(new String(ch,start,length));
				}
				
			}
			else {
				xml.append(new String(ch,start,length));
			}
		}
	}
	
	@Override
	   public void endElement(
	      String uri, String localName, String qName)
	      throws SAXException {
		xml.append("</");
		/*if(qName.equalsIgnoreCase("next")) {
			if(currType.equalsIgnoreCase("1"))
				xml.append("at-least");
			else
				xml.append(qName);
		}else if(qName.equalsIgnoreCase("up-to-and-including")) {
			if(currType.equalsIgnoreCase("1"))
				xml.append("every");
			else
				xml.append(qName);
		}else {
			xml.append(qName);
		}*/
		xml.append(qName);
		xml.append(">\n");
		tags.put(qName,false);
	}

	// Set filelocation path for mac or windows
	public static String setFileLoc(String fileLoc) {
		StringBuilder currPath = new StringBuilder();
		fileLoc = System.getProperty("user.dir");
		
		if(fileLoc.contains("/")) {
			String paths[] = fileLoc.split("/");
			currPath.append("/");
			for(int i=0;i<paths.length-2;i++) {
				currPath.append(paths[i]);
				currPath.append("/");
			}
			currPath.append("ASDACDFeatureTest/asda-estore-regression/src/main/resources/com/asda/qa/data/Linksave-files/");
		}else {
			/*String paths[] = fileLoc.split("\\\\");
			for(String path : paths) {
				currPath.append(path);
				currPath.append("\\");
			}*/
			currPath.append(fileLoc);
			currPath.append("\\src\\main\\resources\\com\\asda\\qa\\data\\Linksave-files\\");
		}
		fileLoc = currPath.toString();
		return fileLoc;
	}
	
	// Returns a string containing modified pricing-model-rule based on type
	public static String parse(String data,String quant,String pId,String type) {
		quantity = quant;
		promotionId = pId;
		currType = type;
		
		//String fileLoc =setFileLoc("System.getProperty(\"user.dir\")");
		
		File cdatafile = new File("/Users/m0a00pf/Documents/Docs/ cdatafile.xml");
		xml = new StringBuilder();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(cdatafile));
			bw.write(data);
			bw.flush();
			bw.close();
			
			File inputFile = new File("/Users/m0a00pf/Documents/Docs/ cdatafile.xml");
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser = factory.newSAXParser();
	         
	        tags = new HashMap<String,Boolean>();
	        
	        CDataParser cdataparser = new CDataParser();
	        
	        saxParser.parse(inputFile, cdataparser); 
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return xml.toString();
	}
}

