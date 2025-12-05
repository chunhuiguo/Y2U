package Y2U.Parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class YakinduReader {

	private String yakinduFilePath;	
	private Document yakinduDoc;	
	
	public YakinduReader(String yakinduFilePath) {
		super();
		this.yakinduFilePath = yakinduFilePath;
		
		yakinduDoc = null;
	}
	
	
	public Document read()
    {   	     
		yakinduDoc = parse();

		return yakinduDoc;
    }
	
	
	
	
	//Load and parse XML file into DOM 
    private Document parse() { 
    	Document document = null; 
    	try { 
    		//DOM parser instance 
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder(); 
    		//parse an XML file into a DOM tree 
    		document = docBuilder.parse(new File(yakinduFilePath)); 
    	} catch (ParserConfigurationException e) { 
    		e.printStackTrace();  
    	} catch (SAXException e) { 
    		e.printStackTrace(); 
    	} catch (IOException e) { 
    		e.printStackTrace(); 
    	} 
    	return document; 
    }    
	
	


	public String getYakinduFilePath() {
		return yakinduFilePath;
	}
	public void setYakinduFilePath(String yakinduFilePath) {
		this.yakinduFilePath = yakinduFilePath;
	}
	public Document getYakinduDoc() {
		return yakinduDoc;
	}
	public void setYakinduDoc(Document yakinduDoc) {
		this.yakinduDoc = yakinduDoc;
	}	
}
